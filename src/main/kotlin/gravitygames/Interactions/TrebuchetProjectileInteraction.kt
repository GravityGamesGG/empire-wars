package gravitygames.Interactions

import com.hypixel.hytale.codec.KeyedCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent
import com.hypixel.hytale.server.core.modules.entity.component.Intangible
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction
import com.hypixel.hytale.server.core.modules.time.TimeResource
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk
import kotlin.math.PI

class TrebuchetProjectileInteraction : SimpleInstantInteraction()
{
    private var projectileId: String? = null

    companion object
    {
        val CODEC: BuilderCodec<TrebuchetProjectileInteraction> = BuilderCodec.builder(
            TrebuchetProjectileInteraction::class.java, ::TrebuchetProjectileInteraction, SimpleInstantInteraction.CODEC
        ).append(
            KeyedCodec("ProjectileId", BuilderCodec.STRING),
            { obj, value -> obj.projectileId = value },
            { obj -> obj.projectileId }).add().build()
    }

    override fun firstRun(type: InteractionType, context: InteractionContext, cooldownHandler: CooldownHandler)
    {
        if (projectileId == null) return
        val targetBlock = context.targetBlock ?: return
        val commandBuffer = context.commandBuffer ?: return
        val playerEntityRef = context.entity
        val playerRef = playerEntityRef.store.getComponent(playerEntityRef, PlayerRef.getComponentType()) ?: return

        // Get block rotation
        val world = commandBuffer.store.externalData.world
        val chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z)
        val chunkRef = world.chunkStore.getChunkReference(chunkIndex) ?: return
        val blockChunk = world.chunkStore.store.getComponent(chunkRef, BlockChunk.getComponentType()) ?: return
        val section = blockChunk.getSectionAtBlockY(targetBlock.y) ?: return
        val blockRotation = section.getRotation(targetBlock.x, targetBlock.y, targetBlock.z)

        // Convert block rotation to radians (each ordinal step = 90 degrees = PI/2 radians)
        val yawRadians = (blockRotation.yaw().ordinal * PI / 2).toFloat()
        val pitchRadians = (blockRotation.pitch().ordinal * PI / 2).toFloat()

        // Spawn position
        val spawnPosition = Vector3d(targetBlock.x.toDouble(), targetBlock.y.toDouble(), targetBlock.z.toDouble())
        val shootRotation = Vector3f(pitchRadians, yawRadians, 0f)

        // Create projectile
        val timeResource = commandBuffer.getResource(TimeResource.getResourceType())
        val holder = ProjectileComponent.assembleDefaultProjectile(
            timeResource, projectileId!!, spawnPosition, shootRotation
        )

        val projectileComponent = holder.getComponent(ProjectileComponent.getComponentType()) ?: return
        holder.ensureComponent(Intangible.getComponentType())

        if (projectileComponent.projectile == null)
        {
            projectileComponent.initialize()
            if (projectileComponent.projectile == null) return
        }

        projectileComponent.shoot(
            holder, playerRef.uuid, spawnPosition.x, spawnPosition.y, spawnPosition.z, yawRadians, pitchRadians
        )

        commandBuffer.addEntity(holder, AddReason.SPAWN)
    }
}
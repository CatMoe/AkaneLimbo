@file:Suppress("unused")

package catmoe.fallencrystal.akanelimbo.packet

import dev.simplix.protocolize.api.PacketDirection
import dev.simplix.protocolize.api.mapping.AbstractProtocolMapping
import dev.simplix.protocolize.api.mapping.ProtocolMapping
import dev.simplix.protocolize.api.packet.AbstractPacket
import dev.simplix.protocolize.api.util.ProtocolUtil
import dev.simplix.protocolize.api.util.ProtocolVersions
import io.netty.buffer.ByteBuf
import java.util.*

open class PositionLook : AbstractPacket() {
    var x = 0.0
    var y = 0.0
    var z = 0.0
    var yaw = 0f
    var pitch = 0f
    var flags: Byte = 0
    var teleportId = 0
    var dismountVehicle = false
    override fun read(buf: ByteBuf, packetDirection: PacketDirection, protocolVersion: Int) {
        x = buf.readDouble()
        y = buf.readDouble()
        z = buf.readDouble()
        yaw = buf.readFloat()
        pitch = buf.readFloat()
        flags = buf.readByte()
        if (protocolVersion >= ProtocolVersions.MINECRAFT_1_9) teleportId = ProtocolUtil.readVarInt(buf)
        if (protocolVersion >= ProtocolVersions.MINECRAFT_1_17) dismountVehicle = buf.readBoolean()
    }

    override fun write(buf: ByteBuf, packetDirection: PacketDirection, protocolVersion: Int) {
        buf.writeDouble(x)
        buf.writeDouble(y)
        buf.writeDouble(z)
        buf.writeFloat(yaw)
        buf.writeFloat(pitch)
        buf.writeByte(flags.toInt())
        if (protocolVersion >= ProtocolVersions.MINECRAFT_1_9) ProtocolUtil.writeVarInt(buf, teleportId)
        if (protocolVersion >= ProtocolVersions.MINECRAFT_1_17) buf.writeBoolean(dismountVehicle)
    }

    override fun toString(): String {
        return ("PositionLookPacket(x=" + x + ", y=" + y + ", z=" + z + ", yaw=" + yaw + ", pitch="
                + pitch + ", flags=" + flags + ", teleportId=" + teleportId + ", dismountVehicle="
                + dismountVehicle + ")")
    }

    fun positionLookPacket() {
        x = 0.0
        y = 0.0
        z = 0.0
        yaw = 0f
        pitch = 0f
        flags = 0.toByte()
        teleportId = 0
        dismountVehicle = false
    }

    fun positionLookPacket(
        x: Double, y: Double, z: Double, yaw: Float, pitch: Float, flags: Byte, teleportId: Int,
        dismountVehicle: Boolean
    ) {
        this.x = x
        this.y = y
        this.z = z
        this.yaw = yaw
        this.pitch = pitch
        this.flags = flags
        this.teleportId = teleportId
        this.dismountVehicle = dismountVehicle
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) {
            return true
        }
        if (other !is PositionLook) {
            return false
        }
        if (!other.canEqual(this as Any)) {
            return false
        }
        if (x != other.x) {
            return false
        }
        if (y != other.y) {
            return false
        }
        if (z != other.z) {
            return false
        }
        if (yaw != other.yaw) {
            return false
        }
        if (pitch != other.pitch) {
            return false
        }
        if (flags != other.flags) {
            return false
        }
        return if (teleportId != other.teleportId) {
            false
        } else dismountVehicle == other.dismountVehicle
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is PositionLook
    }

    override fun hashCode(): Int {
        return Objects.hash(
            x, y, z, yaw,
            pitch, flags, teleportId,
            dismountVehicle
        )
    }

    companion object {
        val MAPPINGS = mutableListOf<ProtocolMapping>(
            AbstractProtocolMapping.rangedIdMapping(
                ProtocolVersions.MINECRAFT_1_8,
                ProtocolVersions.MINECRAFT_1_8,
                0x08
            ),
            AbstractProtocolMapping.rangedIdMapping(
                ProtocolVersions.MINECRAFT_1_9,
                ProtocolVersions.MINECRAFT_1_12,
                0x2e
            ),
            AbstractProtocolMapping.rangedIdMapping(
                ProtocolVersions.MINECRAFT_1_12_1,
                ProtocolVersions.MINECRAFT_1_12_2,
                0x2f
            ),
            AbstractProtocolMapping.rangedIdMapping(
                ProtocolVersions.MINECRAFT_1_13,
                ProtocolVersions.MINECRAFT_1_13_2,
                0x32
            ),
            AbstractProtocolMapping.rangedIdMapping(
                ProtocolVersions.MINECRAFT_1_14,
                ProtocolVersions.MINECRAFT_1_14_4,
                0x35
            ),
            AbstractProtocolMapping.rangedIdMapping(
                ProtocolVersions.MINECRAFT_1_15,
                ProtocolVersions.MINECRAFT_1_15_2,
                0x36
            ),
            AbstractProtocolMapping.rangedIdMapping(
                ProtocolVersions.MINECRAFT_1_16,
                ProtocolVersions.MINECRAFT_1_16_1,
                0x35
            ),
            AbstractProtocolMapping.rangedIdMapping(
                ProtocolVersions.MINECRAFT_1_16_2,
                ProtocolVersions.MINECRAFT_1_16_5,
                0x34
            ),
            AbstractProtocolMapping.rangedIdMapping(
                ProtocolVersions.MINECRAFT_1_17,
                ProtocolVersions.MINECRAFT_1_18_2,
                0x38
            ),
            AbstractProtocolMapping.rangedIdMapping(
                ProtocolVersions.MINECRAFT_1_19,
                ProtocolVersions.MINECRAFT_1_19,
                0x36
            ),
            AbstractProtocolMapping.rangedIdMapping(
                ProtocolVersions.MINECRAFT_1_19_1,
                ProtocolVersions.MINECRAFT_LATEST,
                0x39
            )
        )
    }
}

package catmoe.fallencrystal.akanelimbo.afk

import dev.simplix.protocolize.api.Location
import dev.simplix.protocolize.api.player.ProtocolizePlayer

class PlayerPosition(val player: ProtocolizePlayer) {
    fun position(): Location { return player.location() }
}
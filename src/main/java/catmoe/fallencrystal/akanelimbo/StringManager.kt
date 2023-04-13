package catmoe.fallencrystal.akanelimbo

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo

object StringManager {
    private var LoginLimbo = "LoginLimbo"
    private var MainLimbo = "MainLimbo"
    private var limboPrefix = "AkaneLimbo"
    private var limboArrow = " -> "
    private var kickRedirectLimbo = "KickRedirect"
    private var commandLimbo = "CmdCreate"
    private var serverListPermission = "bungeecord.command.server"
    private var sendLimboPermission = "bungeecord.command.send"

    fun getLoginLimbo(): ServerInfo { return ProxyServer.getInstance().getServerInfo(LoginLimbo) }
    fun getMainLimbo(): ServerInfo { return ProxyServer.getInstance().getServerInfo(MainLimbo) }

    fun getLimboPrefix(): String { return limboPrefix }
    fun getLimboArrow(): String { return limboArrow }

    fun getKickRedirectLimbo(): String {return kickRedirectLimbo}
    fun getCommandLimbo(): String {return commandLimbo}

    fun getServerListPermission(): String {return serverListPermission}
    fun getSendLimboPermission(): String {return sendLimboPermission}



}

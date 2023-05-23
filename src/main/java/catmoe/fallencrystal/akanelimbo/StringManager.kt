@file:Suppress("DEPRECATION")

package catmoe.fallencrystal.akanelimbo

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo

object StringManager {
    private var LoginLimbo = "LoginLimbo"
    private var MainLimbo = "MainLimbo"
    private var Lobby = "Survival"
    private var limboPrefix = "AkaneLimbo"
    private var limboArrow = " -> "
    private var kickRedirectLimbo = "KickRedirect"
    private var commandLimbo = "CmdCreate"
    private var serverListPermission = "bungeecord.command.server"
    private var sendLimboPermission = "bungeecord.command.send"
    private var enableRule = true

    fun getLoginLimbo(): ServerInfo? { return ProxyServer.getInstance().getServerInfo(LoginLimbo) }
    fun getMainLimbo(): ServerInfo? { return ProxyServer.getInstance().getServerInfo(MainLimbo) }

    fun getLimboPrefix(): String { return limboPrefix }
    fun getLimboArrow(): String { return limboArrow }

    fun getKickRedirectLimbo(): String {return kickRedirectLimbo}
    fun getCommandLimbo(): String {return commandLimbo}

    fun getServerListPermission(): String {return serverListPermission}
    fun getSendLimboPermission(): String {return sendLimboPermission}

    fun getEnableRule(): Boolean {return enableRule}

    fun getLobby(): ServerInfo {return try {ProxyServer.getInstance().getServerInfo(Lobby)}
    catch (_: NullPointerException) {return ProxyServer.getInstance().getServerInfo(ProxyServer.getInstance().config.listeners.iterator().next().defaultServer)}}



}

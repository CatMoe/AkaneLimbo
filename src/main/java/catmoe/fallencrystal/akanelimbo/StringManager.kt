package catmoe.fallencrystal.akanelimbo;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class StringManager {

    static String LoginLimbo = "LoginLimbo";
    static String MainLimbo = "MainLimbo";
    static String LimboPrefix = "AkaneLimbo";
    static String LimboArrow = " -> ";
    static String KickRedirectLimbo = "KickRedirect";
    static String CommandLimbo = "CmdCreate";

    static String ForceReadPermission = "rule.read";
    static String ForceUnreadPermission = "rule.bypass";
    static String ServerListPermission = "bungeecord.command.server";
    static String SendLimboPermission = "bungeecord.command.send";

    public static ServerInfo getLoginLimbo() {return ProxyServer.getInstance().getServerInfo(LoginLimbo);}
    public static ServerInfo getMainLimbo() {return ProxyServer.getInstance().getServerInfo(MainLimbo);}
    public static String getForceReadPermission() {return ForceReadPermission;}
    public static String getForceUnreadPermission() {return ForceUnreadPermission;}
    public static String getServerListPermission() {return ServerListPermission;}
    public static String getSendLimboPermission() {return SendLimboPermission;}
    public static String getLimboPrefix() {return LimboPrefix;}
    public static String getLimboArrow() {return LimboArrow;}
    public static String getKickRedirectLimbo() {return KickRedirectLimbo;}
    public static String getCommandLimbo() {return CommandLimbo;}

}

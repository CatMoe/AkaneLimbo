package catmoe.fallencrystal.akanelimbo.util;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@SuppressWarnings("deprecation")
// from AkaneField
public class MessageUtil {

    static String prefix = "";

    public static void actionbar(ProxiedPlayer p, String message) {
        try {
            if (!(p instanceof ProxiedPlayer)) {
                logerror("Cannot send actionbar for console.");
                logerror("Message: " + message);
                throw new RuntimeException("Cannot send actionbar for console");
            }
            p.sendMessage(
                    ChatMessageType.ACTION_BAR, new TextComponent(ca(message)));
        } catch (Exception e) {
            logerror("MessageSendUtil occurred exception");
            logerror("Type: actionbar");
            logerror("Message: " + message);
            logerror("Target: " + p);
            throw new RuntimeException("Cannot handle actionbar.");
        }
    }

    public static void rawchat(ProxiedPlayer p, String message) {
        try {
            if (!(p instanceof ProxiedPlayer)) {
                loginfo(message);
                return;
            }
            List<ProxiedPlayer> chatp = new ArrayList<>();
            chatp.add(p);
            p.sendMessage(
                    ChatMessageType.CHAT, new TextComponent(ca(message)));
            chatp.remove(p);
        } catch (Exception e) {
            logerror("MessageSendUtil occurred exception");
            logerror("Type: Chat");
            logerror("Message: " + message);
            logerror("Target: " + p);
            throw new RuntimeException("Cannot handle chat.");
        }
    }

    public static void prefixchat(ProxiedPlayer p, String message) {
        rawchat(p, prefix + message);
    }

    // Sender是专门为命令发送而准备的 皆在解决prefixchat和rawchat控制台的冲突
    public static void prefixsender(CommandSender sender, String message) {
        rawsender(sender, prefix + message);
    }

    public static void rawsender(CommandSender sender, String message) {
        try {
            sender.sendMessage(ca(message));
        } catch (Exception e) {
            logerror("MessageSendUtil occurred exception");
            logerror("Type: Chat (CommandSender)");
            logerror("Message: " + message);
            logerror("Target: " + sender);
            throw new RuntimeException("Cannot handle PrefixSender");
        }
    }

    public static void fulltitle(ProxiedPlayer p, String title, String subtitle, int stay, int fadeIn, int fadeOut) {
        try {
            if (!(p instanceof ProxiedPlayer)) {
                logerror("Cannot send title for console.");
                logerror("Title: " + title);
                logerror("Subtitle" + subtitle);
                logerror("Stay" + stay + ", " + "FadeIn" + fadeIn + ", " + "FadeOut" + fadeOut);
                logerror("Target: " + p);
                throw new RuntimeException("Cannot send fulltitle for console");
            }
            Title t = ProxyServer.getInstance().createTitle();
            t.title(new TextComponent(ca(title)));
            t.subTitle(new TextComponent(ca(subtitle)));
            t.stay(stay);
            t.fadeIn(fadeIn);
            t.fadeOut(fadeOut);
            t.send(p);
        } catch (Exception e) {
            logerror("MessageSendUtil occurred exception");
            logerror("Type: title");
            logerror("Title: " + title);
            logerror("Subtitle" + subtitle);
            logerror("Stay" + stay + ", " + "FadeIn" + fadeIn + ", " + "FadeOut" + fadeOut);
            logerror("Target: " + p);
            throw new RuntimeException("Cannot send fulltitle.");
        }
    }

    public static void broadcastRawChatPerms(String message, String permission) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.hasPermission(permission)) {
                rawchat(player, message);
            }
        }
    }

    public static void broadcastRawChat(String message) {
        broadcastRawChatPerms(message, "");
    }

    public static void broadcastActionbarPerms(String message, String permission) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.hasPermission(permission)) {
                actionbar(player, message);
            }
        }
    }

    public static void broadcastActionbar(String message) {
        broadcastActionbarPerms(message, "");
    }

    public static void broadcastPrefixChatPerms(String message, String permission) {
        broadcastRawChatPerms(prefix + message, permission);
    }

    public static void broadcastPrefixChat(String message) {
        broadcastPrefixChatPerms(message, "");
    }

    public static void broadcastTitlePerms(String title, String subtitle, int fadeIn, int stay, int fadeOut,
            String permission) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.hasPermission(permission)) {
                fulltitle(player, title, subtitle, stay, fadeIn, fadeOut);
            }
        }
    }

    public static void broadcastTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        broadcastTitlePerms(title, subtitle, fadeIn, stay, fadeOut, "");
    }

    public static void loginfo(String message) {
        ProxyServer.getInstance().getLogger().info(ca(prefix + message));
    }

    public static void logwarn(String message) {
        ProxyServer.getInstance().getLogger().warning(ca(prefix + message));
    }

    public static void logerror(String message) {
        ProxyServer.getInstance().getLogger().severe(ca(prefix + message));
    }

    public static String ca(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
package catmoe.fallencrystal.akanelimbo.util;

import java.net.InetSocketAddress;
import java.net.Socket;

import net.md_5.bungee.api.config.ServerInfo;

public class ServerOnlineCheck {
    @SuppressWarnings("deprecation")
    public static boolean SocketPing(ServerInfo server) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(server.getAddress().getAddress(), server.getAddress().getPort()),
                    1000);
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean MOTDPing(ServerInfo server) {
        try {
            if (server.getMotd() != null) {
                return true;
            }
            if (!server.getMotd().equals("")) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static String getMotd(ServerInfo server) {
        return server.getMotd();
    }
}

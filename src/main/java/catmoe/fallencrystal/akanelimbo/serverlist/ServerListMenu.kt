package catmoe.fallencrystal.akanelimbo.serverlist;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import catmoe.fallencrystal.akanelimbo.util.ServerOnlineCheck;
import catmoe.fallencrystal.akanelimbo.util.menu.ForceFormatCode;
import catmoe.fallencrystal.akanelimbo.util.menu.GUIBuilder;
import catmoe.fallencrystal.akanelimbo.util.menu.GUIEnchantsList;
import catmoe.fallencrystal.akanelimbo.util.menu.ItemBuilder;
import dev.simplix.protocolize.api.inventory.InventoryClick;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerListMenu extends GUIBuilder {
    @Override
    public void open(ProxiedPlayer player) {
        clear();
        define(player);
        super.open(player);
    }

    public void update() {
        clear();
        define(getPlayer());
    }

    ProxyServer proxy = ProxyServer.getInstance();
    Map<String, ServerInfo> servers = proxy.getServers();
    Collection<ServerInfo> serverlist = servers.values();

    @Override
    public void define(ProxiedPlayer p) {
        super.define(p);
        this.type(InventoryType.GENERIC_9X6);
        this.setTitle(ca("&eServer List"));
        int slot = 0;
        String clicktoconnect = "&7 > &b点击来连接到服务器";
        Map<SocketAddress, ServerInfo> addresscache = new HashMap<>();
        for (ServerInfo s : serverlist) {
            String name = s.getName();
            String motd = s.getMotd();
            String conflictwarn = "";
            // Just another BungeeCord / Waterfall - Force Hosts
            // 深蓝色丑死 但又懒得改 直接进行一波暴力替换
            if (motd.contains("Just another")) {
                motd = "&7默认 &f- &b请前往config.yml设置";
            }
            SocketAddress address = s.getSocketAddress();
            if (addresscache.containsKey(address)) {
                conflictwarn = "&c(跟" + addresscache.get(address).getName() + "冲突)";
            } else {
                addresscache.put(address, s);
            }
            int playing = s.getPlayers().size();
            String isOnline;
            ItemType item;
            if (ServerOnlineCheck.SocketPing(s)) {
                isOnline = "&a(在线)";
                item = ItemType.EMERALD_BLOCK;
            } else {
                isOnline = "&c(离线)";
                item = ItemType.REDSTONE_BLOCK;
            }
            setItem(slot, new ItemBuilder(item)
                    .name(ca("&b" + name + " " + isOnline))
                    // 有人喜欢给菜单全部物品上附魔 我不说是谁
                    .enchantment(GUIEnchantsList.UNBREAKING, playing)
                    // 来玩找不同吧 ——看看哪个笨蛋填了俩地址一样的服务器(?)
                    .lore(ca("&b地址: " + s.getSocketAddress() + " " + conflictwarn))
                    .lore(ca("&e" + playing + " 在线玩家"))
                    .lore(ca("&dMOTD注释:"))
                    .lore(ca(motd))
                    .lore(ca(""))
                    .lore(ca(clicktoconnect))
                    .build());
            slot++;
        }
    }

    public void onClick(InventoryClick e) {
        if (e.clickedItem().itemType() == null) {
            update();
        } else {
            toServer(getPlayer(), e.slot());
            update();
        }
    }

    public String ca(String text) {
        return ForceFormatCode.replaceFormat(text);
    }

    public void toServer(ProxiedPlayer p, int slot) {
        int c = 0;
        for (ServerInfo s : serverlist) {
            if (slot == c) {
                p.connect(s);
                return;
            } else {
                c++;
            }
        }
    }
}

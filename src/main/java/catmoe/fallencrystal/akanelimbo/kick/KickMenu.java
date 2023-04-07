package catmoe.fallencrystal.akanelimbo.kick;

import java.util.Arrays;
import java.util.List;

import catmoe.fallencrystal.akanelimbo.util.MessageUtil;
import catmoe.fallencrystal.akanelimbo.util.ServerOnlineCheck;
import catmoe.fallencrystal.akanelimbo.util.menu.ForceFormatCode;
import catmoe.fallencrystal.akanelimbo.util.menu.GUIBuilder;
import catmoe.fallencrystal.akanelimbo.util.menu.ItemBuilder;
import dev.simplix.protocolize.api.inventory.InventoryClick;
import dev.simplix.protocolize.api.inventory.InventoryClose;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;

public class KickMenu extends GUIBuilder {

    ServerInfo disconnectfrom = null;
    ServerInfo limboserver = null;
    ServerInfo lobbyserver = null;

    boolean close = false;

    public void SetInfo(ServerInfo incomingserver, ServerInfo incominglimbo, ServerInfo incominglobby) {
        disconnectfrom = incomingserver;
        limboserver = incominglimbo;
        lobbyserver = incominglobby;
    }

    public void update() {
        clear();
        define(getPlayer());
    }

    @Override
    public void open(ProxiedPlayer p) {
        clear();
        define(p);
        super.open(p);
    }

    public void PlaceholderItem() {
        List<Integer> slots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 17, 18, 19, 20, 21, 22, 23,
                24, 25, 26);
        for (Integer slot : slots) {
            setItem(slot, new ItemBuilder(ItemType.GRAY_STAINED_GLASS_PANE).name("").build());
        }
    }

    @Override
    public void define(ProxiedPlayer p) {
        super.define(p);
        PlaceholderItem();
        this.type(InventoryType.GENERIC_9X3);
        this.setTitle(ca("&c连接已丢失"));
        setItem(13, new ItemBuilder(ItemType.REDSTONE_BLOCK)
                .name(ca("&c您与此服务器的连接已丢失!"))
                .lore(ca(""))
                .lore(ca("&7其实你现在没有连接到任何服务器w!"))
                .lore(ca(""))
                .lore(ca("&b您可以选择 &a重新连接 &b或 &c回到大厅"))
                .build());
        setItem(11, new ItemBuilder(ItemType.REPEATER)
                .name(ca("&a重新连接"))
                .lore(ca(""))
                .lore(ca("&7只是尝试重新连接 没什么特别的"))
                .lore(ca(""))
                .lore(ca("&c如果服务器长时间离线 请报告给管理员!"))
                .lore(ca(""))
                .lore(ca("&7离线时所处服务器: " + disconnectfrom.getName()))
                .build());
        setItem(15, new ItemBuilder(ItemType.BEACON)
                .name(ca("&c回到大厅"))
                .lore(ca(""))
                .lore(ca("&7只是回到大厅 没什么特别的"))
                .build());
    }

    public void onClick(InventoryClick e) {
        if (e.slot() == 11 && e.clickedItem().itemType() == ItemType.REPEATER) {
            if (!isOnline(disconnectfrom)) {
                MessageUtil.actionbar(getPlayer(), "&c目标服务器似乎已离线 请稍后再试");
                return;
            }
            getPlayer().connect(disconnectfrom);
            update();
            MessageUtil.actionbar(getPlayer(), "&a正在尝试重新连接 请稍后..");
        } else if (e.slot() == 15 && e.clickedItem().itemType() == ItemType.BEACON) {
            if (!isOnline(lobbyserver)) {
                MessageUtil.actionbar(getPlayer(), "&c目标服务器似乎已离线 请稍后再试");
                return;
            }
            getPlayer().connect(lobbyserver);
            update();
            MessageUtil.actionbar(getPlayer(), "&a正在将您传送到大厅..");
        } else {
            update();
        }
    }

    public void onClose(InventoryClose e) {
        try {
            if (!close) {
                open(getPlayer());
                MessageUtil.actionbar(getPlayer(), "&b别忘了这可是不存在的地方 关闭了就出不来了哦~");
            } else {
                close();
            }
            // 如果玩家离开造成的NullPointerException 则关闭菜单
        } catch (NullPointerException ex) {
            close = true;
        }
    }

    public String ca(String text) {
        return ForceFormatCode.replaceFormat(text);
    }

    public void isConnected(ServerConnectedEvent e) {
        if (e.getPlayer().equals(getPlayer())) {
            close = true;
            close();
        }
    }

    public boolean isOnline(ServerInfo server) {
        if (!ServerOnlineCheck.SocketPing(server)) {
            return false;
        }
        if (!ServerOnlineCheck.MOTDPing(server)) {
            return false;
        }
        return true;
    }

}

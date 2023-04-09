package catmoe.fallencrystal.akanelimbo.rule;

import java.util.Arrays;
import java.util.List;

import catmoe.fallencrystal.akanelimbo.util.menu.ForceFormatCode;
import catmoe.fallencrystal.akanelimbo.util.menu.GUIBuilder;
import catmoe.fallencrystal.akanelimbo.util.menu.GUIEnchantsList;
import catmoe.fallencrystal.akanelimbo.util.menu.ItemBuilder;

import dev.simplix.protocolize.api.inventory.InventoryClick;
import dev.simplix.protocolize.api.inventory.InventoryClose;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RuleMenu extends GUIBuilder {

    boolean RuleGeneral = false;
    boolean RulePrivacy = false;
    boolean RuleFinal = false;
    boolean RuleNotReaded = false;
    boolean RuleNotReadedItem = false;

    int AgreeItemItemSlot = 31;
    int RuleFinalItemSlot = 10;
    int RuleGeneralItemSlot = 12;
    int RulePrivacyItemSlot = 14;
    int RuleNotReadedItemSlot = 16;

    boolean close = false;

    String empty = "";

    String RuleAccept = "&a点击接受此条例!";
    String RuleDeny = "&c不想同意? 再次点击来取消操作.";

    @Override
    public void open(ProxiedPlayer p) {
        clear();
        define(p);
        super.open(p);
    }

    @Override
    public void define(ProxiedPlayer p) {
        super.define(p);
        this.type(InventoryType.GENERIC_9X5);
        this.setTitle(ca("&b用户协议签署 &7[最后更新 23-02-07]"));
        AgreeItem();
        RuleFinal();
        RuleGeneral();
        RulePrivacy();
        RuleNotReaded();
        PlaceholderItem();
    }

    public void RuleFinal() {
        String name = "&b条款适用的地方";
        String line1 = " &f一旦您接受任何条例";
        String line2 = " &f即视为在停止使用/有关猫萌团队的服务前";
        String line3 = " &f您都需要遵守该条例.";
        String line4 = " &f条款适用于任何接受此条款的人 包括其它玩家 管理员";
        String line5 = " &f我们有权在不通知您的情况下修改条例";
        String line6 = " &f猫萌团队拥有对任何有关其团队的任何事物做出最终解释权.";
        if (!RuleFinal) {
            setItem(RuleFinalItemSlot, new ItemBuilder(ItemType.PAPER)
                    .name(ca(name))
                    .lore(ca(empty))
                    .lore(ca(line1))
                    .lore(ca(line2))
                    .lore(ca(line3))
                    .lore(ca(empty))
                    .lore(ca(line4))
                    .lore(ca(line5))
                    .lore(ca(empty))
                    .lore(ca(line6))
                    .lore(ca(empty))
                    .lore(ca(RuleAccept))
                    .build());
        } else {
            setItem(RuleFinalItemSlot, new ItemBuilder(ItemType.PAPER)
                    .enchantment(GUIEnchantsList.UNBREAKING, 1)
                    .name(ca(name))
                    .lore(ca(empty))
                    .lore(ca(line1))
                    .lore(ca(line2))
                    .lore(ca(line3))
                    .lore(ca(empty))
                    .lore(ca(line4))
                    .lore(ca(line5))
                    .lore(ca(empty))
                    .lore(ca(line6))
                    .lore(ca(empty))
                    .lore(ca(RuleDeny))
                    .build());
        }
    }

    public void RuleGeneral() {
        String name = "&b通用条例";
        String line1 = " &c同意代表您同意并愿意无规则遵守这些条例";
        String line2 = " &71. 严禁作弊";
        String line3 = "  &c严禁通过作弊手段破坏游戏平衡/得到不平等的优势";
        String line4 = "  &c包括但并不限于使用作弊/被修改的客户端";
        String line5 = "  &c第三方辅助工具等等的非法越界行为.";
        String line6 = " &72. 严禁发表不合适的言论";
        String line7 = "  &7包括但并不限于发送&b垃圾邮件&7, &b使用公认的低俗/不文明词汇";
        String line8 = "  &7内容&b涉及Dox(人肉搜索) &7或 &b种族仇恨 &7的相关言论";
        String line9 = "  &7以及&b发送广告 &7(例如宣传其它服务器)";
        String line10 = " &73. 账户安全";
        String line11 = "  &7您应自己负责您的账户安全. &c永远不要将您的账户外借给别人";
        String line12 = "  &c请妥当保管您的密码 我们不负责提供密码重置服务&7(如果您使用了任何类似的服务)";
        String line13 = " &74. &7严禁进行私下交易 &c如果您因为私下交易遇到了麻烦 猫萌团队概不负责.";
        String line14 = " &75. &7未满18周岁的未成年人请在家长的陪同下游玩游戏";
        if (!RuleGeneral) {
            setItem(RuleGeneralItemSlot, new ItemBuilder(ItemType.PAPER)
                    .name(ca(name))
                    .lore(ca(line1))
                    .lore(ca(empty))
                    .lore(ca(line2))
                    .lore(ca(line3))
                    .lore(ca(line4))
                    .lore(ca(line5))
                    .lore(ca(empty))
                    .lore(ca(line6))
                    .lore(ca(line7))
                    .lore(ca(line8))
                    .lore(ca(line9))
                    .lore(ca(empty))
                    .lore(ca(line10))
                    .lore(ca(line11))
                    .lore(ca(line12))
                    .lore(ca(empty))
                    .lore(ca(line13))
                    .lore(ca(empty))
                    .lore(ca(line14))
                    .lore(ca(empty))
                    .lore(ca(RuleAccept))
                    .build());
        } else {
            setItem(RuleGeneralItemSlot, new ItemBuilder(ItemType.PAPER)
                    .enchantment(GUIEnchantsList.UNBREAKING, 1)
                    .name(ca(name))
                    .lore(ca(line1))
                    .lore(ca(empty))
                    .lore(ca(line2))
                    .lore(ca(line3))
                    .lore(ca(line4))
                    .lore(ca(line5))
                    .lore(ca(empty))
                    .lore(ca(line6))
                    .lore(ca(line7))
                    .lore(ca(line8))
                    .lore(ca(line9))
                    .lore(ca(empty))
                    .lore(ca(line10))
                    .lore(ca(line11))
                    .lore(ca(line12))
                    .lore(ca(empty))
                    .lore(ca(line13))
                    .lore(ca(empty))
                    .lore(ca(line14))
                    .lore(ca(empty))
                    .lore(ca(RuleDeny))
                    .build());
        }
    }

    public void RulePrivacy() {
        String name = "&b隐私协议";
        String line1 = " &e所有我们收集的数据都是由您";
        String line2 = " &e自己或您的客户端提供的.";
        String line3 = " &e为了条例透明 会在下面列出必要的信息收集类型:";
        String line4 = " &b必要的数据 &7(即我们必须使用这些信息)";
        String line5 = "  &7- &f您在服务器中使用的命令/发送的聊天信息";
        String line6 = "  &7- &f客户端标签 语言和视野距离 &7(由您的客户端自动提供)";
        String line7 = "  &7- &f登入服务器时被加密过的令牌或登录密码";
        String line8 = "  &7- &f您在游戏中的行为事件 &7(例如移动)";
        String line9 = "  &7- &f您的私信或公共频道聊天 &7(用于实施通用条例)";
        String line10 = " &d加入服务器代表您同意我们收集这些数据";
        String line11 = " &d如果不同意 请点击下方的物品离开服务器";
        if (!RulePrivacy) {
            setItem(RulePrivacyItemSlot, new ItemBuilder(ItemType.PAPER)
                    .name(ca(name))
                    .lore(ca(empty))
                    .lore(ca(line1))
                    .lore(ca(line2))
                    .lore(ca(line3))
                    .lore(ca(empty))
                    .lore(ca(line4))
                    .lore(ca(line5))
                    .lore(ca(line6))
                    .lore(ca(line7))
                    .lore(ca(line8))
                    .lore(ca(line9))
                    .lore(ca(empty))
                    .lore(ca(line10))
                    .lore(ca(line11))
                    .lore(ca(empty))
                    .lore(ca(RuleAccept))
                    .build());
        } else {
            setItem(RulePrivacyItemSlot, new ItemBuilder(ItemType.PAPER)
                    .enchantment(GUIEnchantsList.UNBREAKING, 1)
                    .name(ca(name))
                    .lore(ca(empty))
                    .lore(ca(line1))
                    .lore(ca(line2))
                    .lore(ca(line3))
                    .lore(ca(empty))
                    .lore(ca(line4))
                    .lore(ca(line5))
                    .lore(ca(line6))
                    .lore(ca(line7))
                    .lore(ca(line8))
                    .lore(ca(line9))
                    .lore(ca(empty))
                    .lore(ca(line10))
                    .lore(ca(line11))
                    .lore(ca(empty))
                    .lore(ca(RuleDeny))
                    .build());
        }
    }

    public void RuleNotReaded() {
        // 这是一个陷阱 呃 这不是搞不搞笑的问题 这确实是一个陷阱..
        if (!RuleNotReaded) {
            setItem(RuleNotReadedItemSlot, new ItemBuilder(ItemType.PAPER)
                    .name(ca("&b我不到写啥了"))
                    .lore(ca(empty))
                    .lore(ca(" &e我个人认为啊 这个意大利面"))
                    .lore(ca(" &e就应该拌42号混凝土"))
                    .lore(ca(empty))
                    .lore(ca(" &e因为这个螺丝的长度它"))
                    .lore(ca(" &e很容易会直接影响到"))
                    .lore(ca(" &e挖掘机的扭矩 你知道吧"))
                    .lore(ca(" &e你往里砸的时候 一瞬间就会"))
                    .lore(ca(" &e产生大量的高能蛋白 俗称UFO"))
                    .lore(ca(" &e会严重影响经济的发展"))
                    .lore(ca(empty))
                    .lore(ca(" &e甚至对整个太平洋以及充电器"))
                    .lore(ca(" &e都会造成一定的核污染"))
                    .lore(ca(empty))
                    .lore(ca(" &8这不是协议的一部分xd 看看那个信标吧"))
                    .lore(ca(empty))
                    .lore(ca(RuleAccept))
                    .build());
            RuleNotReadedItem = true;
        } else {
            // 当有憨批真的去点了这个物品
            if (!RuleNotReadedItem) {
                setItem(RuleNotReadedItemSlot, new ItemBuilder(ItemType.PAPER)
                        .name(ca("&b我不到写啥了"))
                        .lore(ca(empty))
                        .lore(ca(" &e我真不到写什么了 同意之后点那个按钮吧"))
                        .lore(ca(empty))
                        .lore(ca(RuleAccept))
                        .build());
            } else {
                setItem(RuleNotReadedItemSlot, new ItemBuilder(ItemType.PAPER)
                        .enchantment(GUIEnchantsList.UNBREAKING, 1)
                        .name(ca("&b我不到写啥了"))
                        .lore(ca(empty))
                        .lore(ca(" &e我真不到写什么了 同意之后点那个按钮吧"))
                        .lore(ca(empty))
                        .lore(ca(RuleDeny))
                        .build());
            }
        }
    }

    public void AgreeItem() {
        // 这是个陷阱!
        if (RuleGeneral && RulePrivacy && RuleFinal && RuleNotReaded && RuleNotReadedItem) {
            setItem(AgreeItemItemSlot, new ItemBuilder(ItemType.EMERALD_BLOCK)
                    .name(ca("&a同意用户协议"))
                    .lore(ca(""))
                    .lore(ca(" &a看起来您接受我们的条款"))
                    .lore(ca(" "))
                    .lore(ca("&b点击加入服务器!"))
                    .build());
            return;
        }
        // 未同意任何条例
        if (!RuleGeneral && !RulePrivacy && !RuleFinal && !RuleNotReadedItem) {
            setItem(AgreeItemItemSlot, new ItemBuilder(ItemType.REDSTONE_BLOCK)
                    .name(ca("&c拒绝用户协议"))
                    .lore(ca(""))
                    .lore(ca(" &b要同意用户协议 请将鼠标移动到上面的物品"))
                    .lore(ca(" &b来查看条款 并点击确认"))
                    .lore(ca(""))
                    .lore(ca("&c如果您确实不想同意任何条款 请直接点击此按钮"))
                    .build());
            return;
        }
        // 已同意所有条例
        if (RuleGeneral && RulePrivacy && RuleFinal && !RuleNotReaded) {
            setItem(AgreeItemItemSlot, new ItemBuilder(ItemType.BEACON)
                    .name(ca("&a同意用户协议"))
                    .lore(ca(""))
                    .lore(ca(" &a看起来您接受我们的条款"))
                    .lore(ca(" &8不会真的有人没阅读就点吧"))
                    .lore(ca(" "))
                    .lore(ca("&b点击加入服务器!"))
                    .build());
        } else { // 如果没有同意所有条例 展示的物品
            setItem(AgreeItemItemSlot, new ItemBuilder(ItemType.BEACON)
                    .name(ca("&e您还有条款未同意!"))
                    .lore(ca(""))
                    .lore(ca(" &c如果您确实不想接受我们的条款"))
                    .lore(ca(" &c请直接点击此按钮"))
                    .build());
        }
    }

    public void onClick(InventoryClick e) {
        if (e.slot() == AgreeItemItemSlot && RuleNotReaded && e.clickedItem().itemType() == ItemType.EMERALD_BLOCK) {disconnect(getPlayer(), "&c你真的认真阅读并同意用户协议了吗?");}
        else if (e.slot() == AgreeItemItemSlot && e.clickedItem().itemType() == ItemType.BEACON && RuleGeneral && RulePrivacy && RuleFinal && !RuleNotReaded) {
            getPlayer().connect(ProxyServer.getInstance().getServerInfo("Lobby-1")); close = true;}
        else if (e.slot() == AgreeItemItemSlot && e.clickedItem().itemType() == ItemType.REDSTONE_BLOCK) {disconnect(getPlayer(), "&c下次再见!");}
        else if (e.slot() == RuleFinalItemSlot && e.clickedItem().itemType() == ItemType.PAPER) {RuleFinal = !RuleFinal; open(getPlayer());}
        else if (e.slot() == RuleGeneralItemSlot && e.clickedItem().itemType() == ItemType.PAPER) {RuleGeneral = !RuleGeneral; open(getPlayer());}
        else if (e.slot() == RulePrivacyItemSlot && e.clickedItem().itemType() == ItemType.PAPER) {RulePrivacy = !RulePrivacy; open(getPlayer());}
        else if (e.slot() == RuleNotReadedItemSlot && e.clickedItem().itemType() == ItemType.PAPER) {if (!RuleNotReaded) {RuleNotReaded = true;} else {RuleNotReadedItem = !RuleNotReadedItem;}
            open(getPlayer());
        } else if (e.slot() == AgreeItemItemSlot && e.clickedItem().itemType() == ItemType.BEACON && !(RuleGeneral && RulePrivacy && RuleFinal && !RuleNotReaded)) {
            disconnect(getPlayer(), "&c下次再见!");
        } else {
            open(getPlayer());
        }
    }

    public void PlaceholderItem() {
        List<Integer> slots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 13, 15, 17, 18, 19, 20, 21, 22, 23, 24,
                25, 26, 27, 28, 29, 30, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44);
        for (Integer slot : slots) {
            setItem(slot, new ItemBuilder(ItemType.GRAY_STAINED_GLASS_PANE).name("").build());
        }
    }

    public void onClose(InventoryClose e) {
        try {
            if (!close) {
                open(getPlayer());
            } else {
                close();
            }
            // 用法详见KickMenu
        } catch (NullPointerException ex) {
            close = true;
        }
    }

    public String ca(String text) {
        return ForceFormatCode.replaceFormat(text);
    }

    public void disconnect(ProxiedPlayer p, String reason) {
        close = true;
        close();
        p.disconnect(new TextComponent(ca(reason)));
    }

}

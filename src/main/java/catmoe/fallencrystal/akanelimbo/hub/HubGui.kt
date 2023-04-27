package catmoe.fallencrystal.akanelimbo.hub

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.akanelimbo.util.MessageUtil
import catmoe.fallencrystal.akanelimbo.util.ServerOnlineCheck
import catmoe.fallencrystal.akanelimbo.util.menu.ForceFormatCode
import catmoe.fallencrystal.akanelimbo.util.menu.GUIBuilder
import catmoe.fallencrystal.akanelimbo.util.menu.GUIEnchantsList
import catmoe.fallencrystal.akanelimbo.util.menu.ItemBuilder
import dev.simplix.protocolize.api.inventory.InventoryClick
import dev.simplix.protocolize.data.ItemType
import dev.simplix.protocolize.data.inventory.InventoryType
import net.md_5.bungee.api.connection.ProxiedPlayer

class HubGui : GUIBuilder() {

    val inventoryType = InventoryType.GENERIC_9X3
    val inventoryName = ca("&7回到大厅")

    override fun open(player: ProxiedPlayer) {
        clear()
        define(player)
        super.open(player)
    }
    override fun define(p: ProxiedPlayer?) {
        super.define(p)
        this.type(inventoryType)
        setTitle(inventoryName)
        placeholderItem()
        setItem(13, ItemBuilder(ItemType.BEACON).name(ca("&b点击来回到大厅")).enchantment(GUIEnchantsList.UNBREAKING, 0).build())
        setItem(18, ItemBuilder(ItemType.ARROW).name(ca("&7我就是随便逛逛")).build())
    }

    override fun onClick(click: InventoryClick?) {
        if (click!!.slot() == 13 && click.clickedItem().itemType() == ItemType.BEACON) { sendToLobby(player!!) }
        else if (click.slot() == 18 && click.clickedItem().itemType() == ItemType.ARROW) { close() }
        else define(player)
    }

    private fun sendToLobby(p: ProxiedPlayer) {
        val lobby = StringManager.getLobby()
        if (ServerOnlineCheck.socketPing(lobby)) { p.connect(lobby) }
        else MessageUtil.actionbar(p, "&c目标服务器不在线 请稍后再试.")
    }

    private fun placeholderItem() {
        val slots: List<Int> = listOf(1,2,3,4,5,6,7,8,9,10,11,12,14,15,16,17,19,20,21,22,23,24,25,26)
        val item = ItemType.LIGHT_GRAY_STAINED_GLASS_PANE
        for (slot in slots) {setItem(slot, ItemBuilder(item).name(ca("")).build())}
    }

    private fun ca(text: String): String { return ForceFormatCode.replaceFormat(text) }


}
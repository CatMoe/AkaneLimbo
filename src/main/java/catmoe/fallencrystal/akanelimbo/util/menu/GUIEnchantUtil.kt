package catmoe.fallencrystal.akanelimbo.util.menu

class GUIEnchantUtil @JvmOverloads constructor(@JvmField val enchantment: GUIEnchantsList, @JvmField val level: Int = 1) {

    override fun toString(): String {
        return "d: \"" + enchantment.string + "\", lvl: " + level + "s"
    }
}
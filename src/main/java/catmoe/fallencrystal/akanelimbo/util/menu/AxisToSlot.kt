package catmoe.fallencrystal.akanelimbo.util.menu

import dev.simplix.protocolize.data.inventory.InventoryType

object AxisToSlot {
    fun calculation(x: Int, y: Int, inv: InventoryType): Int {
        var maxXaxis = 9
        var minXaxis = 1
        var maxYaxis = 0
        var minYaxis = 1
        if (inv == InventoryType.GENERIC_9X1) { maxYaxis = 1 }
        if (inv == InventoryType.GENERIC_9X2) { maxYaxis = 2 }
        if (inv == InventoryType.GENERIC_9X3) { maxYaxis = 3 }
        if (inv == InventoryType.GENERIC_9X4) { maxYaxis = 4 }
        if (inv == InventoryType.GENERIC_9X5) { maxYaxis = 5 }
        if (inv == InventoryType.GENERIC_9X6) { maxYaxis = 6}
        if (inv == InventoryType.GENERIC_3X3) {
            maxXaxis = 3
            maxYaxis = 3
        }
        if (x < minXaxis) { throw IllegalArgumentException("x must be greater than $minXaxis in $inv type. ($x)") }
        if (x > maxXaxis) { throw IllegalArgumentException("x must be less than $maxXaxis in $inv type. ($x)") }
        if (y < minYaxis) { throw IllegalArgumentException("y must be greater than $minYaxis in $inv type. ($y)") }
        if (y > maxYaxis) { throw IllegalArgumentException("y must be less than $maxYaxis in $inv type. ($y)") }
        return (y - 1) * maxYaxis + x - 1
    }
}
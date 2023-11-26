package io.github.sunshinewzy.serverconductor

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

fun ItemStack?.isItemSimilar(
    item: ItemStack,
    checkLore: Boolean = true,
    checkAmount: Boolean = true,
    checkName: Boolean = true,
    checkMeta: Boolean = true,
    checkDurability: Boolean = false
): Boolean {
    return if (this == null) {
        false
    } else if (type != item.type) {
        false
    } else if (checkAmount && amount < item.amount) {
        false
    } else if (checkDurability && durability != item.durability) {
        false
    } else if (!checkMeta) {
        true
    } else if (hasItemMeta()) {
        val itemMeta = itemMeta ?: return true

        if (item.hasItemMeta()) {
            val itemMeta2 = item.itemMeta ?: return true
            itemMeta.isMetaSimilar(itemMeta2, checkLore, checkName)
        } else false
    } else !item.hasItemMeta()
}

fun ItemStack?.isItemSimilar(item: ItemStack): Boolean = isItemSimilar(item, true)

fun ItemStack?.isItemSimilar(item: ItemStack, checkLore: Boolean): Boolean = isItemSimilar(item, checkLore, true)

fun ItemMeta.isMetaSimilar(itemMeta: ItemMeta, checkLore: Boolean = true, checkName: Boolean = true): Boolean {
    return if (checkName && itemMeta.hasDisplayName() != hasDisplayName()) {
        false
    } else if (checkName && itemMeta.hasDisplayName() && hasDisplayName() && itemMeta.displayName != displayName) {
        false
    } else if (!checkLore) {
        true
    } else if (itemMeta.hasLore() && hasLore()) {
        val lore = lore ?: return true
        val lore2 = itemMeta.lore ?: return true

        if (lore.isEmpty() && lore2.isEmpty()) return true
        lore.toString() == lore2.toString()
    } else !itemMeta.hasLore() && !hasLore()
}

fun Player.giveItem(item: ItemStack, amount: Int = 0) {
    if (amount > 0) {
        if (amount < 64) {
            item.amount = amount
        } else item.amount = 64
    }

    if (inventory.isFull()) {
        world.dropItem(location, item)
    } else inventory.addItem(item)
}

fun Player.giveItem(items: Array<ItemStack>) {
    items.forEach {
        giveItem(it)
    }
}

fun Player.giveItem(items: List<ItemStack>) {
    items.forEach {
        giveItem(it)
    }
}

fun Inventory.isFull(): Boolean = firstEmpty() == -1 || firstEmpty() > size
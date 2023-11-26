package io.github.sunshinewzy.serverconductor

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.EquipmentSlot
import taboolib.common.platform.Plugin
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.info
import taboolib.platform.util.buildItem
import taboolib.platform.util.bukkitPlugin

object ServerConductor : Plugin() {

    private val itemConductor = buildItem(Material.CLOCK) {
        name = "&e切换服务器"
        lore += ""
        lore += "&a前往你感兴趣的子服吧~"
        lore += "&f右键: &7打开主菜单"
        lore += "&f左键: &7打开小游戏菜单"
        colored()
    }
    
    
    override fun onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(bukkitPlugin, "server_conductor:switch")
        info("Successfully running ServerConductor!")
    }
    
    
    @SubscribeEvent
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (!event.player.hasPlayedBefore()) {
            event.player.giveItem(itemConductor.clone())
        }
    }
    
    @SubscribeEvent(EventPriority.HIGHEST, ignoreCancelled = false)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.hand != EquipmentSlot.HAND) return
        val item = event.item ?: return
        val action = event.action
        
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (!item.isItemSimilar(itemConductor)) return
            
            event.isCancelled = true
            ConductorMenu.openMain(event.player)
        } else if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            if (!item.isItemSimilar(itemConductor)) return
            
            event.isCancelled = true
            ConductorMenu.openGame(event.player)
        }
    }
    
}
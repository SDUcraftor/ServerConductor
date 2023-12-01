package io.github.sunshinewzy.serverconductor

import com.google.common.io.ByteStreams
import org.bukkit.Material
import org.bukkit.entity.Player
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Basic
import taboolib.platform.util.buildItem
import taboolib.platform.util.bukkitPlugin

object ConductorMenu {
    
    private val EDGE = buildItem(Material.GRAY_STAINED_GLASS_PANE) { name = " " }
    private val itemSurvival = buildItem(Material.STONE_PICKAXE) {
        name = "&a生存服 - Survival"
        lore += ""
        lore += "&f1.20.1 普通生存服"
        lore += "&f（首次进服默认进入生存服）"
        colored()
    }
    private val itemMirror = buildItem(Material.GLASS) {
        name = "&b镜像服 - Mirror"
        lore += ""
        lore += "&f1.20.1 生存服的镜像"
        lore += "&f创造模式！想干什么就干什么"
        colored()
    }
    private val itemCreative = buildItem(Material.CRAFTING_TABLE) {
        name = "&e创造服 - Creative"
        lore += ""
        lore += "&f1.20.1 超平坦"
        lore += "&f创造模式！建筑基地与红石实验室！"
        colored()
    }
    private val itemIsland = buildItem(Material.OAK_SAPLING) {
        name = "&a空岛服 - Island"
        lore += ""
        lore += "&f1.17.1 空岛"
        lore += "&f有丰富的成就系统，推荐给萌新～"
        colored()
    }
    private val itemArchitecture = buildItem(Material.STONE_BRICKS) {
        name = "&6建筑服 - Architecture"
        lore += ""
        lore += "&f1.20.1 地皮世界"
        lore += "&f创造！可以领取一块自己的领地！"
        colored()
    }
    private val itemForbiddenCity = buildItem(Material.BRICKS) {
        name = "&d故宫服 - Forbidden City"
        lore += ""
        lore += "&f1.20.1 故宫"
        lore += "&f重檐翠瓦缀天绫，砌玉楼栏嵌彩龙！"
        colored()
    }
    private val itemTest = buildItem(Material.STICK) {
        name = "&c测试服 - Test"
        lore += ""
        lore += "&f1.20.1 进行功能测试"
        lore += "&f用来整活的"
        colored()
    }
    private val itemGame = buildItem(Material.SLIME_BALL) {
        name = "&a小游戏服 - Game"
        lore += ""
        lore += "&f1.20.1 小游戏"
        lore += "&f含有bingo和skywar等小游戏"
        colored()
    }
    private val itemGameBingo = buildItem(Material.MAP) {
        name = "&a宾果小游戏 - Bingo"
        lore += ""
        lore += "&f一种原版生存竞赛小游戏"
        lore += "&f玩家需要通过探索,采掘,搏斗"
        lore += "&f与合成来收集Bingo卡片上的物品"
        colored()
    }
    private val itemGameSkywar = buildItem(Material.ENDER_EYE) {
        name = "&e空岛战争 - Skywar"
        lore += ""
        lore += "&f一种多人PvP小游戏"
        lore += "&f玩家在各自的空岛上开始游戏"
        lore += "&f收集资源，并彼此交战"
        lore += "&f努力活到最后吧！"
        colored()
    }
    
    
    fun openMain(player: Player) {
        player.openMenu<Basic>("选择子服") { 
            rows(5)
            
            map(
                "---------",
                "- s m c -",
                "- i r f -",
                "-  t g  -",
                "---------"
            )
            
            set('-', EDGE)
            
            set('s', itemSurvival) {
                switchServer(player, "s")
            }
            
            set('m', itemMirror) {
                switchServer(player, "m")
            }
            
            set('c', itemCreative) {
                switchServer(player, "c")
            }
            
            set('i', itemIsland) {
                switchServer(player, "i")
            }
            
            set('r', itemArchitecture) {
                switchServer(player, "ar")
            }
            
            set('f', itemForbiddenCity) {
                switchServer(player, "fc")
            }
            
            set('t', itemTest) {
                switchServer(player, "t")
            }
            
            set('g', itemGame) {
                openGame(player)
            }
            
            onClick(lock = true)
        }
    }
    
    fun openGame(player: Player) {
        player.openMenu<Basic>("选择小游戏服") { 
            rows(3)
            
            map(
                "---------",
                "-  b s  -",
                "---------"
            )
            
            set('-', EDGE)
            
            set('b', itemGameBingo) {
                switchServer(player, "bingo")
            }
            
            set('s', itemGameSkywar) {
                switchServer(player, "skywar")
            }
            
            onClick(lock = true)
        }
    }
    
    @Suppress("UnstableApiUsage")
    fun switchServer(player: Player, server: String) {
        val out = ByteStreams.newDataOutput()
        out.writeUTF(server)
        player.sendPluginMessage(bukkitPlugin, "server_conductor:switch", out.toByteArray())
    }
    
}
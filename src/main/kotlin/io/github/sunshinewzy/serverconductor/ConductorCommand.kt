package io.github.sunshinewzy.serverconductor

import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand

@CommandHeader(
    name = "serverconductor",
    aliases = ["ser"],
    description = "切换子服",
    permission = "serverconductor.command",
    permissionDefault = PermissionDefault.TRUE
)
object ConductorCommand {
    
    @CommandBody
    val main = mainCommand { 
        execute<Player> { sender, _, _ -> 
            ConductorMenu.openMain(sender)
        }
    }
    
}
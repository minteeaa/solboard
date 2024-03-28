package pw.mintea.solboard

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import pw.mintea.solboard.util.Board
import java.io.IOException

class SolBoard : JavaPlugin(), Listener {

    companion object {
        private lateinit var instance: SolBoard
        private lateinit var addon: SkriptAddon

        fun getInstance(): SolBoard {
            return instance
        }

        fun getAddonInstance(): SkriptAddon {
            return addon
        }
    }

    override fun onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Skript")) {
            logger.severe("disabling SolBoard because Skript is disabled");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        instance = this
        addon = Skript.registerAddon(this)
        Bukkit.getPluginManager().registerEvents(this, this)
        try {
            logger.info("registering class syntaxes...")
            addon.loadClasses("pw.mintea.solboard", "effects")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        logger.info("enabled successfully, registered classes")
    }

    @EventHandler
    fun playerJoin(e: PlayerJoinEvent) {
        logger.info("creating solboard for " + e.player.name)
        Board.createBoard(e.player)
    }

    @EventHandler
    fun playerLeave(e: PlayerQuitEvent) {
        logger.info("deleting solboard of " + e.player.name)
        Board.deleteBoard(e.player)
    }

}

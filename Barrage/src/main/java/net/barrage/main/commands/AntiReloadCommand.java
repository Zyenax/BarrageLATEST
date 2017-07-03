package net.barrage.main.commands;

import net.barrage.main.Barrage;
import net.barrage.main.utils.Utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class AntiReloadCommand implements Listener {
	
	@SuppressWarnings("unused")
	private Barrage plugin;

	public AntiReloadCommand(Barrage hub) {
		this.plugin = hub;
	}
	
	@EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/reload")) {
			e.getPlayer().sendMessage(Utils.color(Barrage.name + " &cThis command is blocked!"));
            e.setCancelled(true);
        }
    }

}

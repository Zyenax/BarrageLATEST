package net.barrage.main.handlers;

import net.barrage.main.Barrage;
import net.barrage.main.SQL.SQLRanks;
import net.barrage.main.utils.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener{

	@SuppressWarnings("unused")
	private static Barrage plugin;

	public ChatHandler(Barrage hub) {
		ChatHandler.plugin = hub;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		e.setFormat(Utils.color(SQLRanks.getRankColor(p) + "&l" + SQLRanks.getRank(p) + " " + SQLRanks.getRankColor(p) + p.getName() + " &r" + e.getMessage()));
	}
}

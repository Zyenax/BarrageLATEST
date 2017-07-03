package net.barrage.main.handlers;

import net.barrage.main.Barrage;
import net.barrage.main.SQL.SQLRanks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionHandler implements Listener{
	
	private static Barrage plugin;
	public ConnectionHandler(Barrage listener) {
		ConnectionHandler.plugin = listener;		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		final Player p = (Player) e.getPlayer();
		
		
		//SQL HANDLING
		if(SQLRanks.getRank(p) == null){
			SQLRanks.setRank(p, Rank.DEFAULT.getName());
			SQLRanks.setRankHeirArchy(p, Rank.DEFAULT.getHeirArchy());
		}
		SQLRanks.loadPlayer(p);
		
		
		//TAB HANDLING
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
    		public void run() {
    			TabHandler.setName(p);
    		}
    	}, 3);
		
		
		//TEAM HANDLING TODO
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		
		
		//SQL HANDLING
		if(SQLRanks.rank.containsKey(e.getPlayer().getUniqueId())){
			SQLRanks.savePlayer(e.getPlayer());
		}
		
		
		//TEAM HANDLING TODO
	}
}

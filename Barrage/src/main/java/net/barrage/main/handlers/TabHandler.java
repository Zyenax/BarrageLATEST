package net.barrage.main.handlers;

import net.barrage.main.Barrage;
import net.barrage.main.SQL.SQLRanks;
import net.barrage.main.utils.Packets;
import net.barrage.main.utils.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class TabHandler implements Listener{
	
	@SuppressWarnings("unused")
	private Barrage plugin;
	public TabHandler(Barrage listener) {
		this.plugin = listener;		
	}
	
	public static void setName(Player p){
		p.setPlayerListName(Utils.color(SQLRanks.getRankColor(p) + p.getName()));
		Utils.setTag(p, Utils.color(SQLRanks.getRankColor(p) + SQLRanks.getRank(p) + " "));
		Packets.sendTabTitle(p, Utils.color("&eCRAZYPVP"), Utils.color("&8Don't be hazy, PvP like Crazy."), Utils.color("&e&m-----------------------------------"), Utils.color("&e&m-----------------------------------"), Utils.color("&8RANK: " + SQLRanks.getRank(p)), Utils.color("&eCrazyPvP.Me"));
	}
}

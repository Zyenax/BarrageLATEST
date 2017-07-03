package net.barrage.main.utils;

import java.util.List;
import java.util.Random;

import net.barrage.main.Barrage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Utils implements Listener{
	
	@SuppressWarnings("unused")
	private static Barrage plugin;

	public Utils(Barrage hub) {
		Utils.plugin = hub;
	}

	public static String color(String string){
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static int randomNum(int Low, int High){
		Random r = new Random();
		int R = r.nextInt(High-Low) + Low;
		return R;
	}
	
	public static ItemStack createItem(Material material, int amount ,int dataValue, String name,
			List<String> list) {
		ItemStack selector = new ItemStack(material, amount, (short) dataValue);
		ItemMeta selectorMeta = selector.getItemMeta();
		selectorMeta.setDisplayName(name);
		if (list != null)
			selectorMeta.setLore(list);
		selector.setItemMeta(selectorMeta);
		return selector;
	}

	public static ItemStack createSkull(String pname, String name,
			List<String> lore) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta meta3 = (SkullMeta) skull.getItemMeta();
		meta3.setOwner(pname);
		meta3.setDisplayName(name);
		meta3.setLore(lore);
		skull.setItemMeta(meta3);
		return skull;
	}
	
	public static void setGamemode(Player player, GameMode gamemode){
		player.setGameMode(gamemode);
	}
	
	public static void setTag(Player player, String tag){
	    for(Player p : Bukkit.getOnlinePlayers()){
	    	if(p.getScoreboard().getTeam(player.getName()) == null){
	    		p.getScoreboard().registerNewTeam(player.getName());
	    	}
	    	p.getScoreboard().getTeam(player.getName()).setPrefix(tag);
	    	p.getScoreboard().getTeam(player.getName()).addPlayer(player);
	    }
	  }
	
}

package net.barrage.main.utils;

import java.lang.reflect.Field;

import net.barrage.main.Barrage;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftFirework;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;


public class Packets implements Listener{
	
	private static Barrage plugin;
	public Packets(Barrage listener) {
		Packets.plugin = listener;
	}
	
	public static void sendTabTitle(Player player, String header1, String header2, String header3, String footer1, String footer2, String footer3) {
		if (header1 == null) {
			header1 = "";
		}
		if(header2 == null){
			header2 = "";
		}
		if(header3 == null){
			header3 = "";
		}
		header1 = ChatColor.translateAlternateColorCodes('&', header1);
		if (footer1 == null) {
			footer1 = "";
		}
		if (footer2 == null) {
			footer2 = "";
		}
		if (footer3 == null) {
			footer3 = "";
		}
		footer1 = ChatColor.translateAlternateColorCodes('&', footer1);

		header1 = header1.replaceAll("%player%", player.getDisplayName());
		footer1 = footer1.replaceAll("%player%", player.getDisplayName());

		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + header1 + "\n" + header2 + "\n" + header3 + "\"}");
		IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer1 + "\n" + footer2 + "\n" + footer3 + "\"}");
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(
				tabTitle);
		try {
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFoot);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.sendPacket(headerPacket);
		}
	}

	public static void sendTitle(Player player, String titlestr,
			String subtitlestr) {
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \""
				+ titlestr + "\"}");
		IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \""
				+ subtitlestr + "\"}");
		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(
				EnumTitleAction.SUBTITLE, chatSubTitle);
		PacketPlayOutTitle title = new PacketPlayOutTitle(
				EnumTitleAction.TITLE, chatTitle);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
		((CraftPlayer) player).getHandle().playerConnection
				.sendPacket(subtitle);
	}
	public static void sendActionBar(Player player, String message) {
		CraftPlayer p = (CraftPlayer) player;
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	}
	public static void playFirework(Location paramLocation,
			FireworkEffect paramFireworkEffect, Integer lifespan) {
		Entity localEntity = paramLocation.getWorld().spawnEntity(
				paramLocation, EntityType.FIREWORK);
		Firework localFirework = (Firework) localEntity;
		FireworkMeta localFireworkMeta = localFirework.getFireworkMeta();
		localFireworkMeta.addEffect(paramFireworkEffect);
		localFireworkMeta.setPower(1);
		localFirework.setFireworkMeta(localFireworkMeta);

		((CraftFirework) localFirework).getHandle().expectedLifespan = lifespan;
	}
	
	public static String createJson(Player player, String message, String hoverText, String commandToRun) {
        IChatBaseComponent comp = ChatSerializer.a("{\"text\":\"\",\"extra\":[{\"text\":\"" + message + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + hoverText + "\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + commandToRun + "\"}}]}");
        PacketPlayOutChat packet = new PacketPlayOutChat(comp);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		return message;
    }
	
	public static void createParticleHalo(final Location loc, final Integer MaxHeight, final Integer radius, final double speed, final EnumParticle particletype) {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			double y = 0;
			double rotSpeed;
    		public void run() {
		if(rotSpeed > 20){
			rotSpeed=0;
		}
			rotSpeed = rotSpeed + speed;
	        double a = radius * Math.cos(rotSpeed + 5);
	        double b = radius * Math.sin(rotSpeed + 5);
	        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particletype, true, (float) (loc.getX() + a), (float) (loc.getY() + y), (float) (loc.getZ() + b), 0, 0, 0, 0, 1, null);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
	        }
	        double c = radius * Math.cos(rotSpeed + 10);
	        double d = radius * Math.sin(rotSpeed + 10);
	        PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(particletype, true, (float) (loc.getX() + c), (float) (loc.getY() + y), (float) (loc.getZ() + d), 0, 0, 0, 0, 1, null);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet1);
	        }
	        double e = radius * Math.cos(rotSpeed + 15);
	        double f = radius * Math.sin(rotSpeed + 15);
	        PacketPlayOutWorldParticles packet2 = new PacketPlayOutWorldParticles(particletype, true, (float) (loc.getX() + e), (float) (loc.getY() + y), (float) (loc.getZ() + f), 0, 0, 0, 0, 1, null);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet2);
	        }
	        double g = radius * Math.cos(rotSpeed + 20);
	        double h = radius * Math.sin(rotSpeed + 20);
	        PacketPlayOutWorldParticles packet3 = new PacketPlayOutWorldParticles(particletype, true, (float) (loc.getX() + g), (float) (loc.getY() + y), (float) (loc.getZ() + h), 0, 0, 0, 0, 1, null);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet3);
	        }
	        double i = radius * Math.cos(rotSpeed + 25);
	        double j = radius * Math.sin(rotSpeed + 25);
	        PacketPlayOutWorldParticles packet4 = new PacketPlayOutWorldParticles(particletype, true, (float) (loc.getX() + i), (float) (loc.getY() + y), (float) (loc.getZ() + j), 0, 0, 0, 0, 1, null);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet4);
	        }
    		}
		}, 0, 1);
	}
	
	public static void createParticleCircle(final Location loc, final Integer MaxHeight, final Integer radius, final double speed, final EnumParticle particletype) {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			double y = 0;
			double rotSpeed;
    		public void run() {
		if(rotSpeed > 20){
			rotSpeed=0;
		}
			rotSpeed = rotSpeed + speed;
	        double a = radius * Math.cos(rotSpeed + 5);
	        double b = radius * Math.sin(rotSpeed + 5);
	        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particletype, true, (float) (loc.getX() + a), (float) (loc.getY() + y), (float) (loc.getZ() + b), 0, 0, 0, 0, 1, null);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
	        }
    		}
		}, 0, 1);
	}
	
	public static void createParticleHelix(final Location loc, final Integer MaxHeight, final Integer radius, final double speed, final EnumParticle particletype) {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			double y = 0;
    		public void run() {
		if(y > MaxHeight){
			y=0;
		}
			y = y + speed;
			double a = radius * Math.cos(y + 5);
	        double b = radius * Math.sin(y + 5);
	        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particletype, true, (float) (loc.getX() + a), (float) (loc.getY() + y), (float) (loc.getZ() + b), 0, 0, 0, 0, 1, null);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
	        }
	        double c = radius * Math.cos(y + 10);
	        double d = radius * Math.sin(y + 10);
	        PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(particletype, true, (float) (loc.getX() + c), (float) (loc.getY() + y), (float) (loc.getZ() + d), 0, 0, 0, 0, 1, null);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet1);
	        }
	        double e = radius * Math.cos(y + 15);
	        double f = radius * Math.sin(y + 15);
	        PacketPlayOutWorldParticles packet2 = new PacketPlayOutWorldParticles(particletype, true, (float) (loc.getX() + e), (float) (loc.getY() + y), (float) (loc.getZ() + f), 0, 0, 0, 0, 1, null);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet2);
	        }
	        double g = radius * Math.cos(y + 20);
	        double h = radius * Math.sin(y + 20);
	        PacketPlayOutWorldParticles packet3 = new PacketPlayOutWorldParticles(particletype, true, (float) (loc.getX() + g), (float) (loc.getY() + y), (float) (loc.getZ() + h), 0, 0, 0, 0, 1, null);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet3);
	        }
	        double i = radius * Math.cos(y + 25);
	        double j = radius * Math.sin(y + 25);
	        PacketPlayOutWorldParticles packet4 = new PacketPlayOutWorldParticles(particletype, true, (float) (loc.getX() + i), (float) (loc.getY() + y), (float) (loc.getZ() + j), 0, 0, 0, 0, 1, null);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet4);
	        }
    		}
		}, 0, 1);
	}
	
	
}

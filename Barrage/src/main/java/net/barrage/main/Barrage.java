package net.barrage.main;

import java.sql.PreparedStatement;

import net.barrage.main.SQL.SQL;
import net.barrage.main.SQL.SQLRanks;
import net.barrage.main.commands.AntiReloadCommand;
import net.barrage.main.commands.MSGCommand;
import net.barrage.main.commands.RankCommand;
import net.barrage.main.handlers.ChatHandler;
import net.barrage.main.handlers.ConnectionHandler;
import net.barrage.main.utils.Packets;
import net.barrage.main.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Barrage extends JavaPlugin implements Listener{
	
	public ConsoleCommandSender console = Bukkit.getConsoleSender();
	public static String name = "&8&l[&e&lBARRAGE&8&l]";
	public static SQL mysql;
	
	public void onEnable(){
		console.sendMessage(Utils.color(name + " &aBarrage has been enabled!"));
		connectSQL();
		registerListeners();
		registerCommands();
		for(Player p : Bukkit.getOnlinePlayers()){
			SQLRanks.loadPlayer(p);
		}
	}
	
	public void onDisable(){
		console.sendMessage(Utils.color(name + " &cBarrage has been disabled!"));
		SQLRanks.onDisableSavePlayer();
		mysql.close();
	}
	
	public void registerListeners(){
		PluginManager manager = Bukkit.getPluginManager();
		manager.registerEvents(new Utils(this), this);
		manager.registerEvents(new Packets(this), this);
		manager.registerEvents(new ChatHandler(this), this);
		manager.registerEvents(new ConnectionHandler(this), this);
		manager.registerEvents(new AntiReloadCommand(this), this);
		manager.registerEvents(new RankCommand(this), this);
		manager.registerEvents(new MSGCommand(this), this);
	}
	
	public void registerCommands(){
		getCommand("msg").setExecutor(new MSGCommand(this));
		getCommand("setrank").setExecutor(new RankCommand(this));
	}
	
	public void connectSQL() {
		// IPADDRESS, PORT, DATABASE, USERNAME, PASSWORD
	     mysql = new SQL("172.106.203.70", "3306", "fh_1146", "fh_1146", "050208595a");
	     PreparedStatement ranks = mysql.prepareStatement("CREATE TABLE IF NOT EXISTS Ranks(UUID varchar(36) NOT NULL, NAME VARCHAR(16) NOT NULL, RANK VARCHAR(45) NOT NULL, HEIRARCHY INT(20) NOT NULL, PRIMARY KEY(UUID))");
	     
	     //USED TO CREATE TABLES FOR INFORMATION
	     mysql.update(ranks);
	}

}

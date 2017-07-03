package net.barrage.main.commands;

import net.barrage.main.Barrage;
import net.barrage.main.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class MSGCommand implements Listener, CommandExecutor {
	
	@SuppressWarnings("unused")
	private Barrage plugin;

	public MSGCommand(Barrage hub) {
		this.plugin = hub;
	}

	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (command.getName().equalsIgnoreCase("msg")){
		if(args.length == 0){
			String incompleteCommand = Utils.color(Barrage.name + " &eThe command is /msg [Player] [message]");
			String badSender = Utils.color(Barrage.name + " &eYou do not have permission to send this command!");
			if(sender instanceof Player){
				Player Player = (Player)sender;
					Player.sendMessage(incompleteCommand);
			}else if(sender instanceof CommandSender){
				sender.sendMessage(incompleteCommand);
			}else{
				sender.sendMessage(badSender);
			}
		}
		
		if(args.length == 1){
			Player target = Bukkit.getServer().getPlayer(args[0]);
			String noMessageProvided = Utils.color(Barrage.name + " &eYou must supply a message to send!");
			String invalidPlayer = Utils.color(Barrage.name + " &eYou must supply an online Players name!");
			String badSender = Utils.color(Barrage.name + " &eYou do not have permission to send this command!");
				if(sender instanceof Player){
					Player Player = (Player)sender;
					if(target != null){
							Player.sendMessage(noMessageProvided);
					}else{
						Player.sendMessage(invalidPlayer);
					}
				}else if(sender instanceof CommandSender){
					if(target != null){
						sender.sendMessage(noMessageProvided);
					}else{
						sender.sendMessage(invalidPlayer);
					}
				}else{
					sender.sendMessage(badSender);
				}
			}
		
		
		
		
			if(args.length >= 2){
				Player target = Bukkit.getServer().getPlayer(args[0]);
				String msg = "";
	            String[] arrayOfString;
	            int j = (arrayOfString = args).length;
	            for (int i = 1; i < j; i++)
	            {
	              String str = arrayOfString[i];
	              msg = msg + str + " ";
	            }
				if(sender instanceof Player){
					Player p = (Player) sender;
					target.sendMessage(Utils.color("&c&l" + p.getName() + " &b> " + " &e&lYou &6= &a" + msg));
				}else if(sender instanceof CommandSender){
					target.sendMessage(Utils.color("&c&l" + sender.getName() + " &b> " + " &e&lYou &6= &a" + msg));
				}
			}
		}
		return true;
	}
}

package net.barrage.main.commands;

import net.barrage.main.Barrage;
import net.barrage.main.SQL.SQLRanks;
import net.barrage.main.handlers.Rank;
import net.barrage.main.utils.Utils;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class RankCommand implements Listener, CommandExecutor {
	
	@SuppressWarnings("unused")
	private Barrage plugin;

	public RankCommand(Barrage hub) {
		this.plugin = hub;
	}

	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		String noPerms = Utils.color(Barrage.name + " &cYou must have " + Rank.ADMIN.getColor() + Rank.ADMIN.getName() + " &cor above to use this!");
		String invalidPlayer = Utils.color(Barrage.name + " &cYou must supply an online Players name!");
		String invalidRank = Utils.color(Barrage.name + " &cYou must supply a valid rank name!");
		String badSender = Utils.color(Barrage.name + " &cYou do not have permission to send this command!");
		String noHigherPerm = Utils.color(Barrage.name + " &cYou are not allowed to edit this Players rank!");
		String noRankProvided = Utils.color(Barrage.name + " &cYou must supply a rank to set!");
		String incompleteCommand = Utils.color(Barrage.name + " &cThe command is /setrank [Player] [rankvalue]");
		
		
		
			if (command.getName().equalsIgnoreCase("setrank")){
				if(args.length == 0){
					if(sender instanceof Player){
						Player player = (Player)sender;
						if(SQLRanks.getRankHeirArchy(player) <= Rank.ADMIN.getHeirArchy()){
							player.sendMessage(incompleteCommand);
						}else{
							player.sendMessage(noPerms);
						}
					}else if(sender instanceof CommandSender){
						sender.sendMessage(incompleteCommand);
					}else{
						sender.sendMessage(badSender);
					}
				}
			
				if(args.length == 1){
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if(sender instanceof Player){
							Player player = (Player)sender;
							if(target != null){
								if(SQLRanks.getRankHeirArchy(player) <= Rank.ADMIN.getHeirArchy()){
									player.sendMessage(noRankProvided);
								}else{
									player.sendMessage(noPerms);
								}
							}else{
								player.sendMessage(invalidPlayer);
							}
						}else if(sender instanceof CommandSender){
							if(target != null){
								sender.sendMessage(noRankProvided);
							}else{
								sender.sendMessage(invalidPlayer);
							}
						}else{
							sender.sendMessage(badSender);
						}
					}
			
				if(args.length == 2){
					String rank = args[1].toString(); 
					Player target = Bukkit.getServer().getPlayer(args[0]);
					String kickMessage = Utils.color(Barrage.name + "\n&cYou have been kicked by: &b" + sender.getName() + "\n&aReason: &eYour rank has been updated! \n&aInfo: &ePlease relog to see your updated rank! \n&aChange: &eRank updated to &c" + rank.toUpperCase());
					if(sender instanceof Player){
						Player player = (Player)sender;
						if(target != null){
							if(SQLRanks.getRankHeirArchy(player) <= Rank.ADMIN.getHeirArchy()){
								if(SQLRanks.getRankHeirArchy(target) >= Rank.DEVELOPER.getHeirArchy()){
									if(EnumUtils.isValidEnum(Rank.class, rank.toUpperCase())){
											if(Rank.valueOf(rank.toUpperCase()).getHeirArchy() >= Rank.ADMIN.getHeirArchy()){
												//UPDATE RANK THEN SAVE THEN PROVIDE KICKMESSAGE
												SQLRanks.setRank(target, rank.toUpperCase());
												if(target.isOnline()){
													target.kickPlayer(kickMessage);
												}
											}else{
												sender.sendMessage(invalidRank);
											}
										}else{
											sender.sendMessage(invalidRank);
										}
								}else{
									sender.sendMessage(noHigherPerm);
								}
							}else{
								player.sendMessage(noPerms);
							}
						}else{
							player.sendMessage(invalidPlayer);
						}
					}else if(sender instanceof CommandSender){
						if(target != null){
							if(EnumUtils.isValidEnum(Rank.class, rank.toUpperCase())){
								if(Rank.valueOf(rank.toUpperCase()).getHeirArchy() >= Rank.ADMIN.getHeirArchy()){
									//UPDATE RANK THEN SAVE THEN PROVIDE KICKMESSAGE
									SQLRanks.setRank(target, rank.toUpperCase());
									if(target.isOnline()){
										target.kickPlayer(kickMessage);
									}
								}else{
									sender.sendMessage(invalidRank);
								}
							}else{
								sender.sendMessage(invalidRank);
							}
						}else{
							sender.sendMessage(invalidPlayer);
						}
					}else{
						sender.sendMessage(badSender);
					}
				}
			}
			return true;
		}
}
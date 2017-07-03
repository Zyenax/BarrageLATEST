package net.barrage.main.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import net.barrage.main.Barrage;
import net.barrage.main.handlers.Rank;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class SQLRanks implements Listener{
	
	@SuppressWarnings("unused")
	private static Barrage plugin;
	
	public SQLRanks(Barrage listener) {
		SQLRanks.plugin = listener;		
	}

	public static HashMap<UUID, String> rank = new HashMap<UUID, String>();
	public static HashMap<UUID, Integer> heirarchy = new HashMap<UUID, Integer>();

    private static final String INSERT = "INSERT INTO Ranks VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE NAME=?";
    private static final String SELECT = "SELECT * FROM Ranks WHERE UUID=?";
    private static final String SAVE = "UPDATE Ranks SET RANK=?, HEIRARCHY=? WHERE UUID=?";
    
    private static void addPlayer(Player p, String rankName, int heirArchy) {
        removePlayer(p);
        rank.put(p.getUniqueId(), rankName);
        heirarchy.put(p.getUniqueId(), heirArchy);
    }

    public static void removePlayer(Player p) {
        rank.remove(p.getUniqueId());
        heirarchy.remove(p.getUniqueId());
    }

    public static String getRank(Player target) {
    	return rank.get(target.getUniqueId());
    }
    
    public static String getRankColor(Player target) {
    	return Rank.valueOf(getRank(target)).getColor();
    }
    
    public static int getRankHeirArchy(Player target) {
    	if(heirarchy.containsKey(target.getUniqueId())){
    		return heirarchy.get(target.getUniqueId());
    	}else{
    		return 0;
    	}
    }
    
    public static void setRankHeirArchy(Player target, int value) {
    	if (heirarchy.containsKey(target.getUniqueId())){
        	heirarchy.remove(target.getUniqueId());
            heirarchy.put(target.getUniqueId(), value);
        }else{
           heirarchy.put(target.getUniqueId(), value);
        }
    }

    public static void setRank(Player target, String rankname) {
        if (rank.containsKey(target.getUniqueId())){
        	rank.remove(target.getUniqueId());
            rank.put(target.getUniqueId(), rankname);
            heirarchy.replace(target.getUniqueId(), Rank.valueOf(getRank(target)).getHeirArchy());
        }else{
           rank.put(target.getUniqueId(), rankname);
           heirarchy.replace(target.getUniqueId(), Rank.valueOf(getRank(target)).getHeirArchy());
        }
    }
    
    public static void loadPlayer(final Player p) {
    	try {
    		PreparedStatement statement = Barrage.mysql.prepareStatement(INSERT);
            statement.setString(1, p.getUniqueId().toString());
            statement.setString(2, p.getName());
            statement.setString(3, rank.get(p.getUniqueId()));
            statement.setInt(4, heirarchy.get(p.getUniqueId()));
            statement.setString(5, p.getName());
            Barrage.mysql.update(statement);

            statement = Barrage.mysql.prepareStatement(SELECT);
            statement.setString(1, p.getUniqueId().toString());
            ResultSet result = Barrage.mysql.query(statement);
            if (result.next()){
            	addPlayer(p, result.getString("RANK"), result.getInt("HEIRARCHY"));
                result.close();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void savePlayer(final Player p) {
                try {
                    PreparedStatement statement = Barrage.mysql.prepareStatement(SAVE);
                    statement.setString(1, rank.get(p.getUniqueId()));
                    statement.setInt(2, heirarchy.get(p.getUniqueId()));
                    statement.setString(3, p.getUniqueId().toString());
                    Barrage.mysql.update(statement);
                    removePlayer(p);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    }
    
    public static void onDisableSavePlayer() {
            	for(Player p : Bukkit.getOnlinePlayers()){
                try {
                	if(rank.containsKey(p.getUniqueId())){
                    PreparedStatement statement = Barrage.mysql.prepareStatement(SAVE);
                    statement.setString(1, rank.get(p.getUniqueId()));
                    statement.setInt(2, heirarchy.get(p.getUniqueId()));
                    statement.setString(3, p.getUniqueId().toString());
                    Barrage.mysql.update(statement);
                    removePlayer(p);
                	}
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            	}
    }
}

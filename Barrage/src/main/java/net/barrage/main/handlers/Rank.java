package net.barrage.main.handlers;

import net.barrage.main.utils.Utils;

public enum Rank {
	
	ADMIN("ADMIN", Utils.color("&c"), 1),
	DEVELOPER("DEVELOPER", Utils.color("&4"), 2),
	MOD("MOD", Utils.color("&e"), 3),
	HELPER("HELPER", Utils.color("&9"), 4),
	BUILDER("BUILDER", Utils.color("&a"), 5),
	WARLORD("WARLORD", Utils.color("&3"), 6),
	SCOUT("SCOUT", Utils.color("&6"), 7),
	KNIGHT("KNIGHT", Utils.color("&b"), 8),
	DEFAULT("DEFAULT", Utils.color("&7"), 9);
	
	private String RankName;
	private String RankColor;
	private int HeirArchy;
	
	private Rank(String RankName, String RankColor, int HeirArchy){
		this.RankName = RankName;
		this.RankColor = RankColor;
		this.HeirArchy = HeirArchy;
	}
	
	public String getName(){
		return RankName;
	}
	
	public String getColor(){
		return RankColor;
	}
	
	public int getHeirArchy(){
		return HeirArchy;
	}
	
}

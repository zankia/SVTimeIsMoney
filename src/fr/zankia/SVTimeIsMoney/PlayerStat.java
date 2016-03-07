package fr.zankia.SVTimeIsMoney;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerStat {
	private HashMap<Player, Long> afk;
	private HashMap<Player, Long> playerList;
	
	public PlayerStat() {
		this.afk = new HashMap<Player, Long>();
		this.playerList = new HashMap<Player, Long>();
	}
	
	public void afkBegin(Player p) {
		if(playerList.containsKey(p))
			afk.put(p, System.currentTimeMillis());
	}
	public void afkEnd(Player p) {
		if(playerList.containsKey(p))
			playerList.put(p, playerList.get(p) - (System.currentTimeMillis() - afk.remove(p)));
	}

	public long getTime(Player p) {
		return playerList.get(p);
	}
	public void addPlayer(Player p) {
		playerList.put(p, System.currentTimeMillis());
	}
	
	public long removePlayer(Player p) {
		if(afk.get(p) != null)
			afkEnd(p);
		return System.currentTimeMillis() - playerList.remove(p);
	}

}

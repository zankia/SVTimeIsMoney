package fr.zankia.svtimeismoney;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.ess3.api.events.AfkStatusChangeEvent;

public class SVTIMListener implements Listener {
	private PlayerStat stat;
	private double moneyPerHour;
	
	public SVTIMListener(SVTIM plugin) {
		this.stat = new PlayerStat();
		this.moneyPerHour = plugin.getConfig().getDouble("moneyPerHour");
	}


	@EventHandler (priority = EventPriority.NORMAL)
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		double hours = (double) stat.removePlayer(player) / (1000 * 60 * 60);
		VaultLink.economy.depositPlayer(player, hours * moneyPerHour);
		
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent e) {
		stat.addPlayer(e.getPlayer());
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onAfk(AfkStatusChangeEvent e) {
		if(e.getAffected().isAfk())
			stat.afkEnd(e.getAffected().getBase());
		else
			stat.afkBegin(e.getAffected().getBase());
	}
	
}

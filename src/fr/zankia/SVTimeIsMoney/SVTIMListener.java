package fr.zankia.SVTimeIsMoney;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.ess3.api.events.AfkStatusChangeEvent;

public class SVTIMListener implements Listener {
	private SVTIM plugin;
	private PlayerStat stat;
	
	public SVTIMListener(SVTIM plugin) {
		this.plugin = plugin;
		this.stat = new PlayerStat();
	}


	@EventHandler (priority = EventPriority.NORMAL)
	public void onQuit(PlayerQuitEvent e) {
		FileConfiguration configs = plugin.getConfig();
		Player player = e.getPlayer();

		double moneyPerHour = configs.getDouble("moneyPerHour");
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

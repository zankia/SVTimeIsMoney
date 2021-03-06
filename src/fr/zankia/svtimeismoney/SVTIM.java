 package fr.zankia.svtimeismoney;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class SVTIM extends JavaPlugin {
	private static final String NO_PERMISSION = "Erreur : Vous n'avez pas la permission pour cette commande.";
	private static SVTIMListener listener;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		listener = new SVTIMListener(this);
		getServer().getPluginManager().registerEvents(listener, this);
		VaultLink.setupEconomy(getServer().getServicesManager().getRegistration(Economy.class));
		getLogger().info("Enabled");
	}

	@Override
	public void onDisable() {
		for(Player p : this.getServer().getOnlinePlayers())
			listener.giveMoney(p);
		getLogger().info("Disabled");
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
		if(name.equalsIgnoreCase("svtim") || name.equalsIgnoreCase("svtimeismoney")) {
			if(sender.hasPermission("svtimeismoney.admin")) {
				if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
					this.reloadConfig();
					sender.sendMessage(ChatColor.RED + "SVTimeIsMoney : " + ChatColor.GREEN + "Reload done.");
				} else
					return false;
			} else
				sender.sendMessage(ChatColor.RED + NO_PERMISSION);
			return true;
		}
		return false;
	}

}

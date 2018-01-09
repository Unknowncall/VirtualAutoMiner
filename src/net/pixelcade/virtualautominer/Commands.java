package net.pixelcade.virtualautominer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	private VirtualAutoMiner plugin;

	public Commands(VirtualAutoMiner virtualAutoMiner) {
		this.plugin = virtualAutoMiner;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to use this command.");
				return true;
			}
			Player player = (Player) sender;
			if (!(player.hasPermission("vam.open"))) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			new GUI(player, this.plugin.getSave().getInt("players." + player.getUniqueId().toString() + ".amount")).open();
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				// reload
				return true;
			}
			if (sender.hasPermission("ama.reload")) {
				sender.sendMessage(ChatColor.RED + "Wrong usage. Did you mean /am reload?");
				return true;
			} else {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "Wrong usage. Did you mean /am reload?");
					return true;
				} else {
					Player player = (Player) sender;
					new GUI(player, this.plugin.getSave().getInt("players." + player.getUniqueId().toString() + ".amount")).open();
				return true;
				}
			}
		}
		
		sender.sendMessage(ChatColor.RED + "Wrong usage! Please use /am or /autominer");
		return true;
	}

}

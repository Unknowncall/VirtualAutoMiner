package net.pixelcade.virtualautominer;

import java.text.DecimalFormat;

import org.bukkit.entity.Player;

import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;

import net.md_5.bungee.api.ChatColor;

public class Task implements Runnable {

	private VirtualAutoMiner plugin;

	public Task(VirtualAutoMiner virtualAutoMiner) {
		this.plugin = virtualAutoMiner;
	}

	@Override
	public void run() {
		for (Player player : this.plugin.getServer().getOnlinePlayers()) {
			if (this.plugin.getSave().getString("players." + player.getUniqueId().toString()) != null) {
				if (this.plugin.getSave().getInt("players." + player.getUniqueId().toString() + ".amount") > 0) {
					double payout = this.plugin.getSave()
							.getInt("players." + player.getUniqueId().toString() + ".amount")
							* this.plugin.getProductivityPerMiner();
					VirtualAutoMiner.getEconomy().depositPlayer(player, (payout * 5.00));
					DecimalFormat df2 = new DecimalFormat("0.00");
					df2.setGroupingUsed(true);
					df2.setGroupingSize(3);
					double tokenPayout = this.plugin.getSave()
							.getInt("players." + player.getUniqueId().toString() + ".amount")
							* VirtualAutoMiner.tokenProductivityPerMiner;
					TokenEnchantAPI.getInstance().addTokens(player, tokenPayout * 5);
					if (!(this.plugin.getSave().getBoolean("players." + player.getUniqueId().toString() + ".silentMessage"))) {
						player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.RESET + " " + ChatColor.GREEN + "AutoMiner Payout " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------");
						player.sendMessage("");
						player.sendMessage(ChatColor.GREEN + "$: " + ChatColor.YELLOW + df2.format(payout * 5));
						player.sendMessage(ChatColor.GREEN + "✪: " + ChatColor.YELLOW + tokenPayout * 5);
						player.sendMessage("");
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "TIP:");
						player.sendMessage(ChatColor.GRAY + "/am silent to disable this.");
						player.sendMessage("");
						player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------------");
					}
				}
			}
		}
	}
}

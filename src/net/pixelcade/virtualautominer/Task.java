package net.pixelcade.virtualautominer;

import org.bukkit.entity.Player;

public class Task implements Runnable {

	private VirtualAutoMiner plugin;

	public Task(VirtualAutoMiner virtualAutoMiner) {
		this.plugin = virtualAutoMiner;
	}

	@Override
	public void run() {
		for (Player player : this.plugin.getServer().getOnlinePlayers()) {
			if (this.plugin.getSave().getString("players." + player.getUniqueId().toString()) != null) {
				double payout = this.plugin.getSave().getInt("players." + player.getUniqueId().toString() + ".amount") * this.plugin.getProductivityPerMiner();
				VirtualAutoMiner.getEconomy().depositPlayer(player, payout);
			}
		}
	}
}

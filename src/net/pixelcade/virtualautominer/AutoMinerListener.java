package net.pixelcade.virtualautominer;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class AutoMinerListener implements Listener {
	
	private VirtualAutoMiner plugin;
	
	public AutoMinerListener(VirtualAutoMiner plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent event) {
		if (this.plugin.getSave().getString("players." + event.getPlayer().getUniqueId().toString()) == null) {
			this.plugin.getSave().set("players." + event.getPlayer().getUniqueId().toString() + ".amount", 0);
			this.plugin.saveSaveFile();
		}
	}
	
	@EventHandler
	public void inventoryClickEvent(InventoryClickEvent event) {
		if (event.getInventory() == null) {
			return;
		}
		if (event.getClick() == null) {
			return;
		}
		if (event.getClickedInventory() == null) {
			return;
		}
		if (!event.getInventory().getTitle().equalsIgnoreCase("Virtual Auto Miner")) {
			return;
		}
		if (event.getSlot() == 2) {
			event.setCancelled(true);
			return;
		}
		if (event.getSlot() == 6) {
			event.setCancelled(true);
			if (this.plugin.getSave().getInt("players." + event.getWhoClicked().getUniqueId().toString() + ".amount") >= VirtualAutoMiner.maxMiners) {
				event.getWhoClicked().sendMessage(ChatColor.RED + "You already have the maximum amount of miners allowed.");
				return;
			}
			int maxLevel = 0;
			for (int i = 0; i < VirtualAutoMiner.maxMiners; i++) {
				if (event.getWhoClicked().hasPermission("vam.miners." + i)) {
					maxLevel = i;
				}
			}
			if (this.plugin.getSave().getInt("players." + event.getWhoClicked().getUniqueId().toString() + ".amount") >= maxLevel) {
				event.getWhoClicked().sendMessage(ChatColor.RED + "You cannot upgrade your miners any further. You must level-up before you are allowed to purchase more miners.");
				return;
			}
			double upgradeCost = VirtualAutoMiner.defaultUpgradeAmount * Math.pow(VirtualAutoMiner.growthFactor, this.plugin.getSave().getInt("players." + event.getWhoClicked().getUniqueId().toString() + ".amount"));
			if (VirtualAutoMiner.getEconomy().getBalance((OfflinePlayer) event.getWhoClicked()) >= upgradeCost) {
				VirtualAutoMiner.getEconomy().withdrawPlayer((OfflinePlayer) event.getWhoClicked(), upgradeCost);
				event.getWhoClicked().sendMessage(ChatColor.GREEN + "Successfully upgraded your autominer.");
				this.plugin.getSave().set("players." + event.getWhoClicked().getUniqueId().toString() + ".amount", this.plugin.getSave().getInt("players." + event.getWhoClicked().getUniqueId().toString() + ".amount") + 1 );
				this.plugin.saveSaveFile();
				event.getWhoClicked().getOpenInventory().close();
				new GUI((Player) event.getWhoClicked(), this.plugin.getSave().getInt("players." + event.getWhoClicked().getUniqueId().toString() + ".amount")).open();
				return;
			} else {
				event.getWhoClicked().sendMessage(ChatColor.RED + "You do not have enough money to purchase this.");
			}
			return;
		}
	}

}

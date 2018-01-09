package net.pixelcade.virtualautominer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
			event.getWhoClicked().sendMessage("Test.");
			return;
		}
	}

}

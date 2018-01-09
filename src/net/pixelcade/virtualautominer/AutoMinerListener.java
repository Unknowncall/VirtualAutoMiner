package net.pixelcade.virtualautominer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

}

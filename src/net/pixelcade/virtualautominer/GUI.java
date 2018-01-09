package net.pixelcade.virtualautominer;

import org.bukkit.entity.Player;

public class GUI {
	
	private Player owner;

	public GUI(Player owner) {
		this.owner = owner;
		this.open();
	}
	
	public void refresh() {
		if (this.owner.getOpenInventory() != null) {
			this.owner.getOpenInventory().close();
		}
		this.open();
	}
	
	public void open() {
		
	}

}

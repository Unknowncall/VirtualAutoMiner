package net.pixelcade.virtualautominer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI {
	
	private Player owner;
	private int amountOfMiners;

	public GUI(Player owner, int amountOfMiners) {
		this.owner = owner;
		this.amountOfMiners = amountOfMiners;
		this.open();
	}
	
	public void refresh() {
		if (this.owner.getOpenInventory() != null) {
			this.owner.getOpenInventory().close();
		}
		this.open();
	}
	
	public void open() {
		ItemStack infoItem = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta im = infoItem.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "Current Miner(s)");
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.RED + "Miner(s) Active:");
		lore.add(ChatColor.GREEN + " " + this.amountOfMiners);
		lore.add("");
		lore.add(ChatColor.RED + "Current Earnings:");
		lore.add(ChatColor.GREEN + " $" + (this.amountOfMiners * VirtualAutoMiner.productivityPerMiner) + "/minute");
		lore.add("");
		im.setLore(lore);
		infoItem.setItemMeta(im);
		
		ItemStack upgradeItem = new ItemStack(Material.PAPER, 1);
		im = upgradeItem.getItemMeta();
		im.setDisplayName("Next Upgrade");
		lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.RED + "Next Upgrade Amount:");
		lore.add(ChatColor.GREEN +" " + (this.amountOfMiners + 1));
		lore.add(ChatColor.RED + "Cost:");
		lore.add(ChatColor.GREEN +" " + VirtualAutoMiner.defaultUpgradeAmount * Math.pow(VirtualAutoMiner.growthFactor, this.amountOfMiners));
		lore.add("");
		lore.add(ChatColor.RED + "Right or Left Click to Upgrade");
		lore.add("");
		im.setLore(lore);
		upgradeItem.setItemMeta(im);
		
		Inventory inv = Bukkit.createInventory(owner, 9);
		inv.setItem(2, infoItem);
		inv.setItem(6, upgradeItem);
	}
}
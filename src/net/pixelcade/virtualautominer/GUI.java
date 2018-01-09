package net.pixelcade.virtualautominer;

import java.text.DecimalFormat;
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
		im.setDisplayName(ChatColor.GREEN + "Next Upgrade");
		lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.RED + "Next Upgrade Amount:");
		lore.add(ChatColor.GREEN +" " + (this.amountOfMiners + 1));
		lore.add(ChatColor.RED + "Cost:");
		double upgradeCost = VirtualAutoMiner.defaultUpgradeAmount * Math.pow(VirtualAutoMiner.growthFactor, this.amountOfMiners);
		DecimalFormat df2 = new DecimalFormat( "#.00" );
		df2.setGroupingUsed(true);
		df2.setGroupingSize(3);
		lore.add(ChatColor.GREEN +" " + df2.format(upgradeCost));
		lore.add("");
		lore.add(ChatColor.RED + "Right or Left Click to Upgrade");
		lore.add("");
		im.setLore(lore);
		upgradeItem.setItemMeta(im);
		
		Inventory inv = Bukkit.createInventory(owner, 9, "Virtual Auto Miner");
		inv.setItem(2, infoItem);
		inv.setItem(6, upgradeItem);
		this.owner.openInventory(inv);
	}
}
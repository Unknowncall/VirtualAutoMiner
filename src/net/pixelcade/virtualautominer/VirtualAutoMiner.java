package net.pixelcade.virtualautominer;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class VirtualAutoMiner extends JavaPlugin {

	public static Economy economy = null;
	public static double productivityPerMiner;
	public static double tokenProductivityPerMiner;
	public static double defaultUpgradeAmount;
	public static double growthFactor;
	public static int maxMiners;

	@SuppressWarnings("deprecation")
	public void onEnable() {
		this.createFiles();
		this.setupEconomy();
		VirtualAutoMiner.productivityPerMiner = this.getConfig().getDouble("productivity_per_miner");
		VirtualAutoMiner.defaultUpgradeAmount = this.getConfig().getDouble("base_cost");
		VirtualAutoMiner.growthFactor = this.getConfig().getDouble("growth_factor");
		VirtualAutoMiner.maxMiners = this.getConfig().getInt("max_miners");
		VirtualAutoMiner.tokenProductivityPerMiner = this.getConfig().getDouble("token_productivity_per_miner");
		this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Task(this), 0, 1200 * 5);
		this.getCommand("automine").setExecutor(new Commands(this));
		this.getServer().getPluginManager().registerEvents(new AutoMinerListener(this), this);
	}

	private File configf, savef;
	private FileConfiguration config, save;

	private void createFiles() {

		configf = new File(getDataFolder(), "config.yml");
		savef = new File(getDataFolder(), "save.yml");

		if (!configf.exists()) {
			configf.getParentFile().mkdirs();
			saveResource("config.yml", false);
		}
		if (!savef.exists()) {
			savef.getParentFile().mkdirs();
			saveResource("save.yml", false);
		}

		config = new YamlConfiguration();
		save = new YamlConfiguration();
		try {
			config.load(configf);
			save.load(savef);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void saveSaveFile() {
		try {
			this.save.save(savef);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public FileConfiguration getSave() {
		return save;
	}

	public void onDisable() {

	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	public static Economy getEconomy() {
		return economy;
	}

	public static void setEconomy(Economy economy) {
		VirtualAutoMiner.economy = economy;
	}

	public double getProductivityPerMiner() {
		return productivityPerMiner;
	} 

}

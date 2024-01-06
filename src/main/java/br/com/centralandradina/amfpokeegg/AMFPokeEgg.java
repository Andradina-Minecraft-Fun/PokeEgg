package br.com.centralandradina.amfpokeegg;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * main class
 */
public class AMFPokeEgg extends JavaPlugin {
	/**
	 * on enable
	 */
	@Override
	public void onEnable() {
		PluginManager pluginManager = this.getServer().getPluginManager();

		// NBTAPI
		if (!pluginManager.isPluginEnabled("NBTAPI")) {
			getLogger().severe("This plugin depends on NBTAPI plugin");
			pluginManager.disablePlugin(this);
			return;
		}

		// set default configs
		FileConfiguration config = getConfig();
		config.options().copyDefaults(true);
		config.addDefault("unique.name", "&f&lPoke&c&lEgg");
		// https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html
		config.addDefault("unique.material", "SLIME_BALL");

		List<String> emptyLore = new ArrayList<>();
		emptyLore.add("&7right click to catch");
		config.addDefault("empty-lore", emptyLore);

		List<String> storedLore = new ArrayList<>();
		storedLore.add("&7right click to release");
		storedLore.add("");
		storedLore.add("&fType: &b%type%");
		storedLore.add("&fName: &b%name%");
		config.addDefault("nonempty-lore", storedLore);

		config.addDefault("show-villager-trades", true);


		// https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html
		List<String> entities = new ArrayList<>();
		entities.add("VILLAGER");
		config.addDefault("entities", entities);

		config.addDefault("messages.entity-no-permitted", "Entity no permitted");
		config.addDefault("messages.catched", "Entity catched");
		config.addDefault("messages.not-empty", "PokeEgg not empty");
		config.addDefault("messages.is-empty", "PokeEgg is empty");

		saveConfig();

		// register listeners
		getServer().getPluginManager().registerEvents(new CatchListener(this), this);
		getServer().getPluginManager().registerEvents(new ReleaseListener(this), this);

		// commands
		this.getCommand("pokeegg").setExecutor(new CommandsExecutor(this));

		// all ok
		getLogger().info("AMFPokeEgg enabled");
	}

	/**
	 * on disable
	 */
	@Override
	public void onDisable() {
		getLogger().info("AMFPokeEgg disabled");
	}

	/**
	 * helper for color
	 */
	public String color(String msg)
	{
		return ChatColor.translateAlternateColorCodes('&', "&f" + msg);
	}

}
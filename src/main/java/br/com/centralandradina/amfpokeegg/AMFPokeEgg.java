package br.com.centralandradina.amfpokeegg;

import br.com.centralandradina.LanguageUtils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



/**
 * main class
 */
public class AMFPokeEgg extends JavaPlugin 
{

	LanguageUtils langs;
	
	/**
	 * on enable
	 */
 	@Override
	public void onEnable() 
	{
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
		emptyLore.add("");
		config.addDefault("empty-lore", emptyLore);

		List<String> storedLore = new ArrayList<>();
		storedLore.add("&7right click to release");
		storedLore.add("");
		storedLore.add("&f» Type:");
		storedLore.add("&b%type%");
		config.addDefault("nonempty-lore", storedLore);

		config.addDefault("show-villager-trades", true);

		config.addDefault("trade-category-lore", "&f» Trades");
		config.addDefault("trade-item-lore", "&e%item%");
		config.addDefault("trade-enchant-lore", " &7%enchant% %level%");


		// https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html
		List<String> entities = new ArrayList<>();
		entities.add("VILLAGER");
		config.addDefault("entities", entities);

		config.addDefault("messages.entity-no-permitted", "Entity no permitted");
		config.addDefault("messages.catched", "Entity catched");
		config.addDefault("messages.not-empty", "PokeEgg not empty");
		config.addDefault("messages.is-empty", "PokeEgg is empty");

		saveConfig();

		// load language
		langs = new LanguageUtils(this);
		langs.loadTranslations("pt_br");
		// langs.loadTranslations("en_us");

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
	public void onDisable() 
	{
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
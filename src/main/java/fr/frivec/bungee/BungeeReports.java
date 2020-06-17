package fr.frivec.bungee;

import fr.frivec.bungee.config.Config;
import fr.frivec.bungee.storage.database.Database;
import fr.frivec.core.json.GsonManager;
import fr.frivec.core.logger.Logger;
import fr.frivec.core.logger.Logger.LogLevel;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public class BungeeReports extends Plugin {
	
	private static BungeeReports instance;
	
	private Config config;
	private Logger logger;
	private Database database;
	private GsonManager gson;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		this.config = new Config();
		this.logger = new Logger(this);
		this.gson = new GsonManager();
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		
		
		super.onDisable();
	}
	
	public static BungeeReports getInstance() {
		return instance;
	}
	
	public Configuration getConfig() {
		
		return this.config.getConfig();
		
	}
	
	public Database getDatabase() {
		return database;
	}
	
	public GsonManager getGson() {
		return gson;
	}
	
	public static void log(final LogLevel level, final String message) {
		
		getInstance().logger.log(level, getInstance().getConfig().getString("prefix").replace("&", "§") + " " + message);
		
	}			

}

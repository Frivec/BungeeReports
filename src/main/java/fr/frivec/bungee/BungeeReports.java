package fr.frivec.bungee;

import fr.frivec.bungee.config.Config;
import fr.frivec.bungee.storage.Credentials;
import fr.frivec.bungee.storage.database.Database;
import fr.frivec.bungee.storage.files.ReportStorage;
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
	private ReportStorage fileStorage;
	private GsonManager gson;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		//Instanciate all the managers and the config
		this.config = new Config();
		this.logger = new Logger(this);
		this.gson = new GsonManager();
		
		boolean sql = getConfig().getBoolean("MySQL.Enable"), file = getConfig().getBoolean("File.Enable");
		
		if(sql && file) {
			
			log(LogLevel.WARNING, "Error. Both of storage ways can't be active at the same time. Disabling the both.");
			sql = false;
			file = false;
			
		}
			
		if(sql)
			
			this.database = new Database(new Credentials(getConfig().getString("MySQL.Host"), getConfig().getString("MySQL."), 
										getConfig().getString("MySQL.Password"), getConfig().getInt("MySQL.Port", 3006)));
		
		else if(file)
			
			this.fileStorage = new ReportStorage();
		
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
	
	public ReportStorage getFileStorage() {
		return fileStorage;
	}
	
	public static void log(final LogLevel level, final String message) {
		
		getInstance().logger.log(level, getInstance().getConfig().getString("prefix").replace("&", "§") + " " + level.getColorTag() + message);
		
	}			

}

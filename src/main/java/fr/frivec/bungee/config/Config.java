package fr.frivec.bungee.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;

import fr.frivec.bungee.BungeeReports;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {
	
	private File file;
	private Configuration configuration;
	private BungeeReports api;
	
	public Config() {
		
		this.api = BungeeReports.getInstance();
		createFile();
		
	}
	
	private void createFile() {
		
		if(!this.api.getDataFolder().exists())
			
			this.api.getDataFolder().mkdirs();
		
		this.file = new File(this.api.getDataFolder(), "config.yml");
		
		if(!this.file.exists()) {
			
			try (final InputStream source = BungeeReports.class.getResourceAsStream("/bungeeConfig.yml")) {
			
				Files.copy(source, this.file.toPath(), new CopyOption[0]);
				
			}catch (IOException e) {
				
				e.printStackTrace();
			
			}
			
		}
		
		if(this.file == null)
			
			System.err.println("Error while create config file");
		
		try {
			
			this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Configuration getConfig() {
		
		return this.configuration;
		
	}

}

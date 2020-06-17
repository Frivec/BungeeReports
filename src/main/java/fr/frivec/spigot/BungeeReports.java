package fr.frivec.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public class BungeeReports extends JavaPlugin {
	
	private static BungeeReports instance;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		
		
		super.onDisable();
	}
	
	public static BungeeReports getInstance() {
		return instance;
	}

}

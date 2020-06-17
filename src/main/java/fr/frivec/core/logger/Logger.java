package fr.frivec.core.logger;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

public class Logger {
	
	private JavaPlugin javaPlugin;
	private ConsoleCommandSender console;
	private CommandSender bungeeConsole;
	
	public Logger(final JavaPlugin javaPlugin) {
		
		this.javaPlugin = javaPlugin;
		this.console = this.javaPlugin.getServer().getConsoleSender();
		
	}
	
	public Logger(final Plugin plugin) {
		
		this.bungeeConsole = plugin.getProxy().getConsole();
		
	}
	
	public void log(final LogLevel level, final String message) {
		
		if(this.bungeeConsole != null)
			
			this.bungeeConsole.sendMessage(new TextComponent(level.getColorTag() + message));
		
		else
		
			this.console.sendMessage(level.getColorTag() + message);
		
	}
	
	public enum LogLevel {
		
		INFO("§a"),
		WARNING("§e"),
		SEVERE("§c"),
		DANGER("§4");
		
		private String colorTag;
		
		private LogLevel(final String colorTag) {
			
			this.colorTag = colorTag;
			
		}
		
		public String getColorTag() {
			return colorTag;
		}
		
	}

}

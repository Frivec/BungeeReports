package fr.frivec.bungee.storage.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.frivec.bungee.BungeeReports;
import fr.frivec.core.logger.Logger.LogLevel;

public class ReportStorage {
	
	private BungeeReports main;
	private Path file;
	
	public ReportStorage() {
		
		this.main = BungeeReports.getInstance();
		
		try {
		
			if(Files.notExists(this.main.getDataFolder().toPath()))
					
				Files.createDirectory(this.main.getDataFolder().toPath());
			
			this.file = Paths.get(this.main.getDataFolder().toPath() + "/reports.json");
			
			if(Files.notExists(this.file))
				
				Files.createFile(this.file);
			
			BungeeReports.log(LogLevel.INFO, "Created successfully storage file");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

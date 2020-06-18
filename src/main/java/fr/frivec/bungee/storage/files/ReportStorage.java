package fr.frivec.bungee.storage.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

import fr.frivec.bungee.BungeeReports;
import fr.frivec.core.Report;
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
	
	public boolean write() {
		
		try {
			
			final BufferedWriter writer = Files.newBufferedWriter(this.file);
			
			writer.write(BungeeReports.getInstance().getGson().serializeObject(Report.reports));
			
			writer.flush();
			writer.close();
			
			return true;
			
		} catch (IOException e) {
			BungeeReports.log(LogLevel.SEVERE, "Error while writing the file reports.json. Please see below");
			e.printStackTrace();
			return false;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<String> loadReports() {
		
		BufferedReader reader = null;
		
		try {
			
			reader = Files.newBufferedReader(this.file);
			final StringBuilder builder = new StringBuilder();
			
			while(reader.read() != -1)
				
				builder.append(reader.readLine());
			
			return (HashSet<String>) BungeeReports.getInstance().getGson().deSeralizeJson(builder.toString(), HashSet.class);
			
		} catch (IOException e) {
			
			BungeeReports.log(LogLevel.WARNING, "Error while trying to read the file reports.json. Please see below.");
			e.printStackTrace();
			
			return null;
		
		}finally {
			
			if(reader != null) {
				
				try {
					
					reader.close();
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
				
		}
		
	}

}

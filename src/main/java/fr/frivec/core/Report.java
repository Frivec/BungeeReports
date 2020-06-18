package fr.frivec.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.entity.Player;

import fr.frivec.bungee.BungeeReports;
import fr.frivec.core.logger.Logger.LogLevel;
import fr.frivec.core.mojang.MojangRequest;
import fr.frivec.core.utils.Utils;

public class Report {
	
	public static HashSet<Report> reports = new HashSet<>();
	
	private int id;
	private UUID reporterUUID, victimUUID;
	private String reporter, victim, reason;
	
	public Report(final Player reporter, final Player victim, final String reason) {
		
		this.id = 0;
		this.reporterUUID = reporter.getUniqueId();
		this.victimUUID = victim.getUniqueId();
		this.reporter = reporter.getName();
		this.victim = victim.getName();
		this.reason = reason;
		
		reports.add(this);
		
	}
	
	public Report(final String reporter, final String victim, final String reason) {
		
		this.reason = reason;
		this.reporter = reporter;
		this.victim = victim;
		
		this.reporterUUID = null;
		this.victimUUID = null;
		
		reports.add(this);
		
	}
	
	public Report(final UUID reporter, final UUID victim, final String reason) {
		
		this.reporterUUID = reporter;
		this.victimUUID = victim;
		this.reason = reason;
		
		this.reporter = MojangRequest.getName(this.reporterUUID.toString());
		this.victim = MojangRequest.getName(this.victimUUID.toString());
		
		reports.add(this);
		
	}
	
	public void save() {
		
		if(!Utils.isBungee)
			
			return;
		
		final BungeeReports main = BungeeReports.getInstance();
		
		if(main.getDatabase() != null) {
			
			try {
				
				addInDatabase(main);
			
			} catch (SQLException e) {
				BungeeReports.log(LogLevel.SEVERE, "Error while trying to save a report in the database. Please see below.");
				e.printStackTrace();
			}
			
		}else if(main.getFileStorage() != null)
			
			main.getFileStorage().write();
			
	}
	
	private void addInDatabase(final BungeeReports main) throws SQLException {
			
		final PreparedStatement state = main.getDatabase().getConnection().prepareStatement("INSERT INTO REPORT_TABLE (reporter, reported, reasons) VALUES (?, ?, ?)", 1);
		    
		if(main.useUUID()) {
			
			state.setString(1, this.getReporterUUID().toString());
			state.setString(2, this.getVictimUUID().toString());
			    
		}else{
		    	
			state.setString(1, this.getReporter());
			state.setString(2, this.getVictim());
		    	
		}
		
		state.setString(3, this.getReason());
		    
		int row = state.executeUpdate();
		
		final ResultSet resultSet = state.getGeneratedKeys();
		
		if((row > 0) && (resultSet.next())) {
			
			int id = resultSet.getInt(1);
			
			this.setId(id);
			
		}
		    
		state.close();
		    
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UUID getReporterUUID() {
		return reporterUUID;
	}

	public void setReporterUUID(UUID reporterUUID) {
		this.reporterUUID = reporterUUID;
	}

	public UUID getVictimUUID() {
		return victimUUID;
	}

	public void setVictimUUID(UUID victimUUID) {
		this.victimUUID = victimUUID;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getVictim() {
		return victim;
	}

	public void setVictim(String victim) {
		this.victim = victim;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}

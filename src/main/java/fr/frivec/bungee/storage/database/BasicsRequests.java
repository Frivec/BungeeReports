package fr.frivec.bungee.storage.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import fr.frivec.bungee.BungeeReports;
import fr.frivec.core.logger.Logger.LogLevel;
import fr.frivec.core.mojang.MojangRequest;
import fr.frivec.core.utils.Utils;

public class BasicsRequests {
	
	public static void createTables() {
		
		try (Connection connection = BungeeReports.getInstance().getDatabase().getConnection()) {
			
			final PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS REPORT_TABLE (id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, reporter varchar(36) NOT NULL, reported varchar(36) NOT NULL, reasons varchar(255) NOT NULL)");
			
			statement.executeUpdate();
			
			statement.close();
			
			BungeeReports.log(LogLevel.INFO, "Successfully created tables");
			
		} catch (SQLException e) {
			
			BungeeReports.log(LogLevel.SEVERE, "Error while creating the tables. Please see below.");
			
			e.printStackTrace();
		}
		
	}
	
	public static void convertAllTables() {
		
		final BungeeReports main = BungeeReports.getInstance();
		
		try (Connection connection = main.getDatabase().getConnection()) {
		
			final DatabaseMetaData databaseMetaData = connection.getMetaData();
			
			if(databaseMetaData.getTables(null, null, "reports", null).next()) {
				
				BungeeReports.log(LogLevel.WARNING, "Found a table from an old version of BungeeReports. The converter has been launched. Please wait while the convertion is running");
			
				final PreparedStatement reportConverter = connection.prepareStatement("SELECT * FROM reports");
				
				reportConverter.executeQuery();
				
				final ResultSet result = reportConverter.getResultSet();
					
				while(result.next()) {
					
					BungeeReports.log(LogLevel.INFO, "Please wait while converting old report table...");
						
					final String reporter = result.getString("reporter"),
									reported = result.getString("reported");
					final UUID reportedUUID = UUID.fromString(result.getString("reportedUUID"));
						
					final PreparedStatement sendInfos = connection.prepareStatement("INSERT INTO REPORT_TABLE (reporter, reported) VALUES (?,?)");
						
					if(main.useUUID()) {
							
						final UUID reporterUUID = UUID.fromString(Utils.addUUIDDashes(MojangRequest.getUUIDFromMojang(reporter)));
							
						if(reporterUUID == null)
								
							sendInfos.setString(1, "null");
							
						else
								
							sendInfos.setString(1, reporterUUID.toString());
							
						sendInfos.setString(2, reportedUUID.toString());
							
					}else {
								
						sendInfos.setString(1, reporter);
						
						sendInfos.setString(2, reported);
							
					}
						
					sendInfos.executeUpdate();
					sendInfos.close();
						
				}
				
				reportConverter.close();
			
			}
			
			final PreparedStatement dropReportTable = connection.prepareStatement("DROP TABLE IF EXISTS reports"),
									dropReasonsTable = connection.prepareStatement("DROP TABLE IF EXISTS reasons");
			
			dropReasonsTable.executeUpdate();
			dropReportTable.executeUpdate();
			
			dropReasonsTable.close();
			dropReportTable.close();
			
		}catch (SQLException e) {
			BungeeReports.log(LogLevel.SEVERE, "Error while converting the old tables to the newers. Please see below");
			e.printStackTrace();
		}
		
	}

}

package fr.frivec.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import fr.frivec.bungee.BungeeReports;

public class CheckVersion {
	
	//V�rification de la version via l'api de spigot
	public static boolean hasUpdate() {
		
		boolean hasUpdate = false;
		
		try {
			
			final HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=44406").openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			
			String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
		      
			if(version.length() <= 13)
				
				if(Utils.isBungee) {
				
					if(!version.equals(BungeeReports.getInstance().getDescription().getVersion()))
			    		  
						hasUpdate = true;
					
				}else {
					
					if(!version.equals(fr.frivec.spigot.BungeeReports.getInstance().getDescription().getVersion()))
			    		  
						hasUpdate = true;
					
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return hasUpdate;
		
	}

}

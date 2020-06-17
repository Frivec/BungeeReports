package fr.frivec.bungee.utils;

import fr.frivec.bungee.BungeeReports;

public enum Messages {
	
	MOD_MESSAGE("modmessage"),
	REPORT_MESSAGE("reportermessage"),
	USAGE("usage"),
	NO_TARGET("notarget"),
	No_PERMISSION("nopermission"),
	COOLDOWN("cooldown");
	
	private String path;
	
	private Messages(final String path) {
		
		this.path = path;
		
	}
	
	public String getMessage(final String victim, final String reporter, final String reason, final float timeLeft) {
		
		String message = BungeeReports.getInstance().getConfig().getString("messages." + getPath());
		
		message = message.replace("&", "§");
		
		if(message.contains("%reporter%") && reporter != null)
			
			message = message.replace("%reporter%", reporter);
		
		if(message.contains("%victim%") && victim != null)
			
			message = message.replace("%victim%", victim);
		
		if(message.contains("%reason%") && reason != null)
			
			message = message.replace("%reason%", reason);
		
		if(message.contains("%timeleft%") && timeLeft >= 0)
			
			message = message.replace("%timeleft%", timeLeft + "");
		
		return message;
		
	}
	
	public String getPath() {
		return path;
	}

}

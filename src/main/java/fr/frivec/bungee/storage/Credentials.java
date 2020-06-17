package fr.frivec.bungee.storage;

public class Credentials {
	
	private int port;
	private String host,
					client,
					dbName,
					password;
	
	public Credentials(final String host, String username, String dbName, String password, int port) {
		
		this.port = port;
		this.host = host;
		this.setClient(username);
		this.dbName = dbName;
		this.setPassword(password);
		
	}
	
	public Credentials(final String host, String clientName, String password, int port) {
		
		this.port = port;
		this.host = host;
		this.setClient(clientName);
		this.setPassword(password);
		
	}
	
	public String toMySqlURL() {
		
		final StringBuilder builder = new StringBuilder();
		
		builder.append("jdbc:mysql://")
		.append(host)
		.append(":")
		.append(port)
		.append("/")
		.append(dbName)
		.append("?autoReconnect=true&useSSL=false");
		
		return builder.toString();
		
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	public String getDbName() {
		return dbName;
	}
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

package fr.frivec.core.json;

import java.text.DateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonManager {
	
	private Gson gson;
	
	public GsonManager() {
		
		this.gson = createGsonInstance();
		
	}
	
	private Gson createGsonInstance() {
		
		return new GsonBuilder()
				.setDateFormat(DateFormat.SHORT)
				.setPrettyPrinting()
				.serializeNulls()
				.disableHtmlEscaping()
				.create();
	}
	
	public String serializeObject(final Object object) {
		return this.gson.toJson(object);
	}
	
	public Object deSeralizeJson(final String json, Class<?> object) {
		return this.gson.fromJson(json, object);
	}
	
	public Gson getGson() {
		return gson;
	}

}

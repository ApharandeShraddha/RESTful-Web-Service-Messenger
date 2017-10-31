package com.ws.restful.messenger.database;

import java.util.HashMap;
import java.util.Map;

import com.ws.restful.messenger.model.Message;
import com.ws.restful.messenger.model.Profile;

//Database Service stub
//Main aim of project is to implement RestApi using jersey so instead of database connections ,
//I have used simple maps to store messages.

public class DatabaseClass {
	
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();

	
	public static Map<Long, Message> getMessages() {
		return messages;
	}
	
	public static Map<String, Profile> getProfiles() {
		return profiles;
	}

	

}

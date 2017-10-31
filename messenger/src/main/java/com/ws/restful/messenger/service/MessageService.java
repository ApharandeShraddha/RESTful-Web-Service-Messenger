package com.ws.restful.messenger.service;

import java.util.ArrayList;
import java.util.List;

import com.ws.restful.messenger.model.Message;

public class MessageService {
	
	public List<Message> getAllMessages(){
		Message m1 = new Message(1L ,"Hello" , "Shraddha");
		Message m2 = new Message(1L ,"Hello" , "Shraddha");
		List<Message> messages = new ArrayList<Message>();
		messages.add(m1);
		messages.add(m2);
		return messages;
	}

}

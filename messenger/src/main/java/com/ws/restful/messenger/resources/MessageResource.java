package com.ws.restful.messenger.resources;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ws.restful.messenger.model.Message;
import com.ws.restful.messenger.resources.beans.MessageFilterBean;
import com.ws.restful.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService ms = new MessageService();
	
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean messageFilterBean) {
		if (messageFilterBean.getYear() > 0) {
			return ms.getAllMessagesForYear(messageFilterBean.getYear());
		}
		if (messageFilterBean.getStart() >= 0 && messageFilterBean.getSize() > 0) {
			return ms.getAllMessagesPaginated(messageFilterBean.getStart(), messageFilterBean.getSize());
		}
		return ms.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId){
		return ms.getMessage(messageId);
	
	}
	
	@POST
	public Message addMessage(Message message){
		return ms.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long messageId , Message message){
		message.setId(messageId);
		return ms.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long messageId){
		ms.removeMessage(messageId);
	}
	
	
	
}

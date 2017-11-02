package com.ws.restful.messenger.resources;

import java.net.URI;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.ws.restful.messenger.model.Message;
import com.ws.restful.messenger.resources.beans.MessageFilterBean;
import com.ws.restful.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value={MediaType.APPLICATION_JSON , MediaType.TEXT_XML })
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
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = ms.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		
		return message;
		
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
	
	//SubRoutes to Comments , Return new instance of another resource
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
	//to add links
	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()   //get base url
				.path(MessageResource.class)    //messages
	       		.path(MessageResource.class, "getCommentResource") // path of method mentioned
	       		.path(CommentResource.class) //comments
	       		.resolveTemplate("messageId", message.getId())//messageId is a variable
	            .build();
	    return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
       		 .path(ProfileResource.class)
       		 .path(message.getAuthor())
             .build();
        return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
		 .path(MessageResource.class)
		 .path(Long.toString(message.getId()))
		 .build()
		 .toString();
		return uri;
	}
	
	
	
}

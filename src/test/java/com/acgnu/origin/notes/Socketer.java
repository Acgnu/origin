package com.acgnu.origin.notes;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/{listener}")  
public class Socketer{
	private Session session;
	
	@OnOpen
	public void onOpen(Session session, @PathParam(value = "listener")String listener){
		this.session = session;
		System.out.println("onOpen id : " + session.getId());
	}
	
	@OnMessage
	public void OnMessage(String msg){
		try {
			System.out.println("messageIn : " + msg);
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void onClose(Session session, CloseReason reason) throws IOException{
		System.out.println("onClose");
	}
	
	@OnError
	public void onError(Throwable e, Session session){
		System.out.println("ERROR");
	}
}

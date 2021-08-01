package com.pranjal.wsclient;

import java.io.IOException;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/game")
public class TestServerEndPoint {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@OnOpen
	public void onOpen(Session session) {
		
		logger.info("Connected..."+ session.getId());
	}
	
	@OnMessage 
	public String onMessage( String message, Session session) {
		logger.info("Received message: " + message );
		
		if(message.equalsIgnoreCase("quit")) {
			try {
				session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Game ends"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "Next Message";
	}
	
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		logger.info(String.format("Session %s closed because of %s", session.getId(),reason));
	}
}

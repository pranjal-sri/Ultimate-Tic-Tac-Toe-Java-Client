package com.pranjal.wsclient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.pranjal.wsclient.grid.MainContainer;

/**
 * This example demonstrates how to create a websocket connection to a server.
 * Only the most important callbacks are overloaded.
 */
public class TicTacToeEndPoint extends WebSocketClient {
	MainContainer containerp;

	public TicTacToeEndPoint(URI serverUri, Draft draft) {
		super(serverUri, draft);
	}

	public TicTacToeEndPoint(URI serverURI) {
		super(serverURI);
	}

	public TicTacToeEndPoint(URI serverUri, Map<String, String> httpHeaders) {
		super(serverUri, httpHeaders);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		System.out.println("opened connection");
		containerp.onConnectedToServer();
	}

	@Override
	public void onMessage(String message) {
		System.out.println("received: " + message);
		try {
			
			JSONObject jsonObj = (JSONObject) new JSONParser().parse(message);
			int gameStateChange = Integer.parseInt(jsonObj.get(ClientContract.Keys.GAME_STATE_CHANGED).toString());
			
			
			if (gameStateChange == ClientContract.GameStateChanges.GAME_STARTED) {
				JSONArray arr = (JSONArray) jsonObj.get(ClientContract.Keys.PAYLOAD);

				containerp.onStartFromServer(Integer.parseInt(arr.get(0).toString()));
				
				
			} else if (gameStateChange == ClientContract.GameStateChanges.GAME_ENDED) {
				JSONArray arr = (JSONArray) jsonObj.get(ClientContract.Keys.PAYLOAD);
				int gameResult = Integer.parseInt(arr.get(0).toString());
				int winner = Integer.parseInt(arr.get(1).toString());
				
				containerp.onEndFromServer(gameResult, winner);
				
			} else {
				int turn = Integer.parseInt(jsonObj.get(ClientContract.Keys.PLAYER_TURN).toString());

				JSONArray arr = (JSONArray) jsonObj.get(ClientContract.Keys.LAST_MOVE);

				int lastGrid = Integer.parseInt(arr.get(0).toString());
				int lastCell = Integer.parseInt(arr.get(1).toString());
				
				int gridStateChange = ClientContract.GridChanges.GRID_TIED;

				boolean isGridChanged = Integer.parseInt(jsonObj.get(ClientContract.Keys.GRID_CHANGED).toString()) 
						== ClientContract.GridStateChanged.GRID_CHANGED;
				
				if(isGridChanged) {
					JSONArray payloadArr = (JSONArray) jsonObj.get(ClientContract.Keys.PAYLOAD);
					gridStateChange = Integer.parseInt(payloadArr.get(0).toString());
				}

				containerp.processGameMessage(turn, lastGrid, lastCell, isGridChanged, gridStateChange);
			}

			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		// The codecodes are documented in class org.java_websocket.framing.CloseFrame
		System.out.println(
				"Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
	}

	@Override
	public void onError(Exception ex) {
		ex.printStackTrace();
		// if the error is fatal then onClose will be called additionally
	}

	public void setConfig(MainContainer ctr) throws URISyntaxException {
		containerp = ctr;
		connect();
	}

}
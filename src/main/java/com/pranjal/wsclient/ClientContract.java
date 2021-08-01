package com.pranjal.wsclient;

public class ClientContract {
	
	public static final class Keys{
		public static final String GAME_STATE_CHANGED = "gameStateChanged";
		public static final String PLAYER_TURN ="playerTurn";
		public static final String GRID_CHANGED = "gridChanged";
		public static final String LAST_MOVE = "lastMove";
		public static final String PAYLOAD = "payload";
	}
	
	
	public static final class GameStateChanges{
		public static final String KEY = "gameStateChanged"; 
		public static final int GAME_UNCHANGED = 0;
		public static final int GAME_STARTED = 1;
		public static final int GAME_ENDED = 2;
	}
	
	public static final class gameChanges{
		public static final int GAME_WON = 0;
		public static final int GAME_TIE = 1;
	}
	
	public static final class GridStateChanged{
		public static final int GRID_UNCHANGED = 0;
		public static final int GRID_CHANGED = 1;
	}
	
	public static final class GridChanges{
		public static final int GRID_WON = 0;
		public static final int GRID_TIED = 1;
	}
}

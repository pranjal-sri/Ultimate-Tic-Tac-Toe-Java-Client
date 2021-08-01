package com.pranjal.wsclient.grid;

import java.awt.Color;
import java.awt.FlowLayout;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.pranjal.wsclient.ClientContract;
import com.pranjal.wsclient.TicTacToeEndPoint;

public class MainContainer {

	private RecursiveGridPanel grid;
	private GameStatePanel statePanel;
	private TicTacToeEndPoint client;

	int player;

	public MainContainer() {
		setPanelSettings();
		try {
			client = new TicTacToeEndPoint(new URI("ws://localhost:3000"));
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			client.setConfig(this);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMove(PlayedChance chance){
		client.send(String.format("[%d,%d]", chance.grid,chance.cell));
	}

	public void onConnectedToServer() {
		System.out.println("Waiting for Player 2");
		statePanel.setStateText("Waiting for\nPlayer 2");
	}

	public void onStartFromServer(int n) {
		player = n;
		if (player == Contract.O) {
			grid.setAllActive();
			statePanel.setStateText("Game Started! Your turn");
		}

		else {
			grid.setAllInactive();
			statePanel.setStateText("Game Started! Player2's turn");

		}

	}

	public void processGameMessage(int turn, int lastMoveGrid, int lastMoveCell, boolean isGridChanged,
			int gridChange) {
		if (turn == player) {
			statePanel.setStateText("Your turn");
			grid.makeMove(lastMoveGrid, lastMoveCell, isGridChanged, gridChange);
			grid.togglePlayer();
		} else {
			statePanel.setStateText("Player 2's Turn");
			grid.processLastMove(lastMoveGrid, isGridChanged, gridChange, player);
			grid.setAllInactive();
		}
	}

	public void onEndFromServer(int gameResult, int gameWinner) {
		grid.setAllInactive();
		if (gameResult == ClientContract.gameChanges.GAME_WON) {
			if (gameWinner == player)
				statePanel.setStateText("You Won!!");

			else
				statePanel.setStateText("You Lost!!");
		}
		else if(gameResult == ClientContract.gameChanges.GAME_TIE) {
			statePanel.setStateText("It's a Tie!");
		}
	}

	private void setPanelSettings() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(213, 236, 194));
		panel.setLayout(new FlowLayout());
		grid = new RecursiveGridPanel(this);
		statePanel = new GameStatePanel();

		panel.add(grid);
		panel.add(statePanel);

		JFrame frame = new JFrame("The Ultimate TIC-TAC-TOE");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

}

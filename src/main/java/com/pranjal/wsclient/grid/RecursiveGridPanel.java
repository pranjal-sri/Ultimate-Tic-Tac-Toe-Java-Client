package com.pranjal.wsclient.grid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Stroke;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.pranjal.wsclient.ClientContract;

public class RecursiveGridPanel extends JPanel {

	int currentPlayer;
	PlayedChance lastChance = null;
	int activeGridIndex;
	GridPanel[] arr;
	
	MainContainer controller;

	public RecursiveGridPanel(MainContainer ctr) {

		controller = ctr;
		currentPlayer = Contract.O;
		arr = new GridPanel[9];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new GridPanel(this, i);
		}

		setActiveGridIndex((int) (Math.random() * arr.length));
		setLayout(new GridBagLayout());

		layGridPanels();
		setBackground(new Color(98, 210, 162));
		setBorder(BorderFactory.createEtchedBorder());

		setMinimumSize(new Dimension(780, 780));
		setPreferredSize(new Dimension(780, 780));

	}
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setAllActive() {
		for (int i = 0; i < arr.length; i++) {
			arr[i].setActive(true);
		}
	}
	
	public void setAllInactive() {
		for (int i = 0; i < arr.length; i++) {
			arr[i].setActive(false);
		}	
	}
	
	public void makeMove(int grid, int cell, boolean isGridChanged, int gridChange) {
		System.out.println("\n[Inside grid] makeMove");
		arr[grid].makeMove(cell);
		
		System.out.println("isGridChanged: "+isGridChanged);
		if(isGridChanged) {
			System.out.println("gridChange: "+gridChange+" player: "+currentPlayer);
			if(gridChange == ClientContract.GridChanges.GRID_WON) {
				arr[grid].setState(currentPlayer);
			}
			else if(gridChange == ClientContract.GridChanges.GRID_TIED) {
				arr[grid].setState(Contract.TIE);
			}
		}
		if(arr[cell].currentState == Contract.EMPTY)
			setActiveGridIndex(cell);
		else
			setAllActive();
	}
	
	public void processLastMove(int grid, boolean isGridChanged, int gridChange,int player) {
		System.out.println("\n[Inside grid] processLastMove");
		System.out.println("isGridChanged: "+isGridChanged);
		if(isGridChanged) {
			System.out.println("gridChange: "+gridChange+" player: "+player);
			if(gridChange == ClientContract.GridChanges.GRID_WON) {
				arr[grid].setState(player);
			}
			else if(gridChange == ClientContract.GridChanges.GRID_TIED) {
				arr[grid].setState(Contract.TIE);
			}
		}
	}

	public void setActiveGridIndex(int i) {
		activeGridIndex = i;
		for (int j = 0; j < arr.length; j++) {
			if (i == j)
				arr[i].setActive(true);
			else
				arr[j].setActive(false);

		}

	}

	public void gameEventOccured(PlayedChance lastChance) {
		controller.sendMove(lastChance);
		System.out.println(lastChance.grid + "," + lastChance.cell);
		togglePlayer();
	}

	public void togglePlayer() {
		currentPlayer = (currentPlayer == Contract.O) ? Contract.X : Contract.O;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.setColor(new Color(57, 62, 70));

		drawGridLine(g, 260, 10, 260, 770);
		drawGridLine(g, 520, 10, 520, 770);
		drawGridLine(g, 10, 260, 770, 260);
		drawGridLine(g, 10, 520, 770, 520);
	}

	public void drawGridLine(Graphics g, int x1, int y1, int x2, int y2) {

		// creates a copy of the Graphics instance
		Graphics2D g2d = (Graphics2D) g.create();
		// set the stroke of the copy, not the original
		Stroke str = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2d.setStroke(str);
		g2d.drawLine(x1, y1, x2, y2);

		// gets rid of the copy
		g2d.dispose();
	}

	private void layGridPanels() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				gc.gridx = col;
				gc.gridy = row;
				add(arr[(row * 3) + col], gc);
			}
		}
	}



	

}

package com.pranjal.wsclient.grid;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class CellPanel extends JPanel {
	private int currentState;
	private boolean isActive;
	RecursiveGridPanel mainGrid;
	
	int cell, grid;
	
	Color defaultBgColor = new Color(98, 210, 162);
	Color selectedActiveBgColor = new Color(31, 171, 137);
	Color selectedInactiveBgColor = new Color(184, 183, 163);

	public CellPanel(RecursiveGridPanel rGrid, int gridNo, int cellNo) {
		isActive = true;
		mainGrid = rGrid;
		currentState = Contract.EMPTY;
		grid = gridNo;
		cell = cellNo;

		setBackground(defaultBgColor);
		setForeground(new Color(206, 118, 113));

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentState == Contract.EMPTY) {
					if (isActive) {
						currentState = mainGrid.currentPlayer;
						mainGrid.gameEventOccured(new PlayedChance(grid, cell));
						repaint();
					}
				}
				super.mouseClicked(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (isActive)
					setBackground(selectedActiveBgColor);
				else
					setBackground(selectedInactiveBgColor);
				super.mouseEntered(e);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(defaultBgColor);
				super.mouseExited(e);
			}

		});

	}
	
	public void setState(int nState) {
		currentState = nState;
		repaint();
	}
	
	public void setIsActive(boolean newValue) {
		isActive = newValue;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		switch (currentState) {
		case Contract.O:
			addString("O", g);
			break;

		case Contract.X:
			addString("X", g);
			break;
		default:
			break;
		}
	}

	private void addString(String text, Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Font font = new Font("Arial", Font.BOLD, getHeight() - 3);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int x = ((getWidth() - fm.stringWidth(text)) / 2);
		int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
		g2d.drawString(text, x, y);
	}

}

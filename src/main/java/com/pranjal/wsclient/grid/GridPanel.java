package com.pranjal.wsclient.grid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JPanel;

public class GridPanel extends JPanel {

	CellPanel[] arr;
	int currentState;
	boolean isActive;
	int grid;
	
	RecursiveGridPanel mainGrid;

	private final Color BACKGROUND_COLOR = new Color(98, 210, 162);

	public GridPanel(RecursiveGridPanel pMainGrid, int gridNo) {

		currentState = Contract.EMPTY;
		grid = gridNo;
		arr = new CellPanel[9];
		mainGrid = pMainGrid;

		for (int i = 0; i < arr.length; i++) {
			arr[i] = new CellPanel(mainGrid, grid, i);
		}

		layCellPanels();

		setSize(new Dimension(240, 240));
		setPreferredSize(new Dimension(240, 240));
		setMinimumSize(new Dimension(240, 240));

		setBackground(BACKGROUND_COLOR);

	}

	public void setActive(boolean newValue) {
		isActive = newValue;
		for (int i = 0; i < arr.length; i++) {
			arr[i].setIsActive(newValue);
		}
	}

	public void setState(int newState) {
		currentState = newState;
		repaint();
	}
	
	public void makeMove(int cell) {
		arr[cell].setState(mainGrid.currentPlayer);
	}

	private void layCellPanels() {
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.ipadx = 5;
		gc.ipady = 5;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				gc.gridx = col;
				gc.gridy = row;
				add(arr[(row * 3) + col], gc);
			}
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		switch (currentState) {
		case Contract.EMPTY:
			g.setColor(new Color(31, 171, 137));
			drawDashedLine(g, 80, 0, 80, 240);
			drawDashedLine(g, 160, 0, 160, 240);
			drawDashedLine(g, 0, 80, 240, 80);
			drawDashedLine(g, 0, 160, 240, 160);
			break;
		case Contract.O:
			removeCellPanels();
			g.setColor(new Color(206, 118, 113));
			addString("O", g);
			break;
		case Contract.X:
			removeCellPanels();
			g.setColor(new Color(206, 118, 113));
			addString("X", g);
			break;
		default:
			break;
		}

	}

	private void removeCellPanels() {
		// TODO Auto-generated method stub
		for (int i = 0; i < arr.length; i++) {
			remove(arr[i]);
		}
	}

	private void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2) {

		// creates a copy of the Graphics instance
		Graphics2D g2d = (Graphics2D) g.create();
		// set the stroke of the copy, not the original
		Stroke dashed = new BasicStroke(2.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[] { 9 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(x1, y1, x2, y2);

		// gets rid of the copy
		g2d.dispose();
	}

	private void addString(String text, Graphics g) {
		g.setColor(new Color(98, 210, 162));
		g.drawRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(206, 118, 113));
		// TODO Auto-generated method stub

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Font font = new Font("Arial", Font.BOLD, getHeight() - 3);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int x = ((getWidth() - fm.stringWidth(text)) / 2);
		int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
		g2d.drawString(text, x, y);
		g2d.dispose();
	}


}

//rgb(57,62,70) - line color inner grid

//rgb(34,40,49) - line color outer grid

//rgb(113,201,206) - background color unselcted

//max- 810 * 810

//rgb(166,227,233) - background color selected

//rgb(206,118,113) - foreground color
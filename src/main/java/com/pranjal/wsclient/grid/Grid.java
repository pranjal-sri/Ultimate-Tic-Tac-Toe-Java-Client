package com.pranjal.wsclient.grid;

import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Grid {
	private int[] cells = new int[9];
	
	GridPanel panel;
	
	private GridStates currentGridState;
	
	public Grid() {
		
		//panel = new GridPanel();
		currentGridState = GridStates.empty;
		for (int i = 0; i < cells.length; i++) 
			cells[i] = Contract.EMPTY;
	}
	
	public void makeMove(int cellNumber, int player) {
		cells[cellNumber] = player;
		checkState();
	}
	
	private void checkState() {
		if(!checkWin())
			checkTie();
	}
	
	private boolean checkWin() {
		for(int i = 0; i < 9; i+=3){
	        int j = i/3;
	            if(cells[i] != Contract.EMPTY && cells[i] == cells[i+1] && cells[i+1] == cells[i+2]) {
	            	currentGridState = cells[i] == Contract.O ? GridStates.o : GridStates.x;
	            	return true;
	            }
	            if(cells[j] != Contract.EMPTY && cells[j] == cells[j+3] && cells[j+3] == cells[j+6]) {
	            	currentGridState = cells[j] == Contract.O ? GridStates.o : GridStates.x;
	            	return true;
	            }
		}
	        if(cells[0] != Contract.EMPTY && cells[0] == cells[4] && cells[4] == cells[8]){
            	currentGridState = cells[0] == Contract.O ? GridStates.o : GridStates.x;
            	return true;
            }
	        
	        if(cells[2] != Contract.EMPTY && cells[2] == cells[4] && cells[4] == cells[6]){
            	currentGridState = cells[2] == Contract.O ? GridStates.o : GridStates.x;
            	return true;
	        }
	        
	        return false;
	}
	
	private void checkTie() {
		boolean isfilled = true;
		
		for (int i = 0; i < cells.length; i++) {
			if(cells[i] == Contract.EMPTY) {
				isfilled = !isfilled;
				break;
			}
		}
		
		if(isfilled)
			currentGridState = GridStates.tie;
	}	
	
}


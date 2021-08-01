package com.pranjal.wsclient.grid;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStatePanel extends JPanel {
	String currentText;
	JLabel label;

	public GameStatePanel() {
		setBackground(new Color(213, 236, 194));

		label = new JLabel("ljherjfjvejbvghkjljvgccvhjlkhkhvjhkjlkjklbkvjhkblknbvhjvbjlk;jljbkvjhkjlhgfgjhkjkhgjhjgjkhgj");
		Font f = new Font("Garamond", Font.BOLD, 52);

		label.setFont(f);

		setStateText("Welcome");
		add(label);
	}

	public void setStateText(String text) {
		currentText = text;

		label.setText(currentText);
		label.repaint();
		repaint();
	}
}

package com.bobinho.client;

import com.bobinho.common.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

@Slf4j
public class DrawingPanel extends JPanel {

	private List<Square> board;

	public DrawingPanel() {
		super();

		setBackground(Color.lightGray);
		setBorder(BorderFactory.createLineBorder(Color.lightGray));
	}

	public void repaint(List<Square> board) {
		this.board = board;

		super.repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (this.board != null && !this.board.isEmpty()) {

			g.setColor(Color.BLACK);
			g.fillRect((int) View.GAMEBOARD_TOP_LEFT_CORNER.getX(), (int) View.GAMEBOARD_TOP_LEFT_CORNER.getY(), View.GAMEBOARD_SIZE, View.GAMEBOARD_SIZE);

			for (int i = 0; i < ConfigUtils.BOARD_LENGTH * ConfigUtils.BOARD_LENGTH; i++) {
				this.board.get(i).draw(g, View.GAMEBOARD_TOP_LEFT_CORNER, View.SQUARE_SIZE);
			}
		}

		else {
			g.setColor(this.getBackground());
			g.fillRect((int) View.GAMEBOARD_TOP_LEFT_CORNER.getX(), (int) View.GAMEBOARD_TOP_LEFT_CORNER.getY(), View.GAMEBOARD_SIZE, View.GAMEBOARD_SIZE);
		}
	}

}
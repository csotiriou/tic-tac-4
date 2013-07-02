/*
 * Copyright (c) 2013, Christos Sotiriou
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * -- Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * -- Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.sai.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sai.game.BoardGame;
import com.sai.game.BoardGame.GameState;
import com.sai.model.BoardTwoPlayers;

/**
 * UI element, which is used to incorporate drawing of the pieces
 * @author Christos Sotiriou
 *
 */
public class GameCanvas extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6474899766649949747L;

	/**
	 * How many columns does the canvas have.
	 */
	private int divisionsWidth = 0;
	
	/**
	 * How many rows the canvas has.
	 */
	private int divisionsHeight = 0;

	/**
	 * The board game, used as a controller for the game.
	 */
	BoardGame currentBoardGame;

	
	/**
	 * If this set to false, no clicks inside the {@link GameCanvas}
	 * will be taken into account
	 */
	boolean detectClicks = true;
	
	
	public GameCanvas(BoardGame boardGame){
		resetWithNewGame(boardGame);
	}
	
	/**
	 * Resets the canvas and sets it up again with a new {@link BoardGame}
	 * instance	
	 * @param boardGame
	 */
	public void resetWithNewGame(BoardGame boardGame){
		if (currentBoardGame != null) {
			currentBoardGame.deleteObservers();
		}
		currentBoardGame = boardGame;
		currentBoardGame.addObserver(this);
		init();
	}


	
	private void init(){
		divisionsHeight = currentBoardGame.getBoard().getHeight();
		divisionsWidth = currentBoardGame.getBoard().getWidth();
		detectClicks = true;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (detectClicks) {
					mouseClickHandler(e);
				}
			}
		});
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getBounds().width, getBounds().height);

		drawLines(g);
		fillWithMoves(g);
	}

	
	/**
	 * Draw the vertical and horizontal lines according to the board's divisions
	 * @param g
	 */
	private void drawLines(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.YELLOW);

		int fullWidth = getBounds().width;
		int fullheight = getBounds().height;

		g2.setStroke(new BasicStroke(3));
		for (int i = 1; i < divisionsWidth; i++) {
			float currentX = i * (fullWidth / divisionsWidth);
			Line2D newLine = new Line2D.Float(new Point2D.Float(currentX, 0.0f), new Point2D.Float(currentX, fullheight));
			g2.draw(newLine);	
		}

		for (int i = 1; i < divisionsHeight; i++) {
			float currentY = i * (fullheight / divisionsHeight);
			Line2D newLine = new Line2D.Float(new Point2D.Float(0, currentY), new Point2D.Float(fullWidth, currentY));
			g2.draw(newLine);	
		}
	}

	/**
	 * Draw the filled rectangles with the respective shape
	 * @param g
	 */
	private void fillWithMoves(Graphics g){
		int divisionWidth = getWidth() / divisionsWidth;
		int divisionHeight = getHeight() / divisionsHeight;
		
		int fontSize = Math.min(divisionWidth, divisionHeight) /2;
		g.setFont(new Font("Arial", Font.PLAIN, fontSize));
		
		
		for (int i = 0; i < divisionsWidth; i++) {
			for (int j = 0; j < divisionsHeight; j++) {
				
				String str = "-";
				int currentPosition = this.currentBoardGame.getBoard().getPiece(new Point(i,j));
				if (currentPosition == BoardTwoPlayers.PLAYER_EMPTY) {
					str = "-";
				}else if (currentPosition == BoardTwoPlayers.PLAYER_O) {
					str = "O";
				}else {
					str = "X";
				}
				
				int currentOffsetX = (i * divisionWidth) + (divisionWidth / 2) - fontSize/2;
				int currentOffsetY = (j * divisionHeight) + (divisionHeight /2);
				g.drawString(str, currentOffsetX, currentOffsetY);
			}
		}

	}

	/**
	 * Handle events from the mouse inside the canvas
	 * @param event
	 */
	private void mouseClickHandler(MouseEvent event){
		if (!detectClicks) {
			return;
		}
		int width = getWidth();
		int height = getHeight();

		int cellWidth = width / divisionsWidth;
		int cellHeight = height / divisionsHeight;

		int column = event.getX() / cellWidth;
		int row = event.getY() / cellHeight;

		Point selectedCell = new Point(column, row);
		System.out.println("point touched: " + selectedCell);
		
	
		if (currentBoardGame.getBoard().isValidMove(selectedCell)){
			try {
				currentBoardGame.makeMoveForNextCurrentPlayer(selectedCell);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("not a valid move. Valid moves are:");
			System.out.println(currentBoardGame.getBoard().getEmptyValidSpaces().toString());
			System.out.println("\n");
		}
		
		
	}

	public enum GameType{
		connect4, tictactoe
	}



	/**
	 * Called whenever a {@link BoardGame} sends a signal that the model is changed. We need to update the
	 * board and repaint it, and determine the winner, if any.
	 */
	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
		
		GameState currentState = currentBoardGame.getCurrentGameState();
		if (currentState == GameState.oWins || currentState == GameState.xWins) {
			JOptionPane.showMessageDialog(null, (currentState == GameState.oWins? "O" : "X") +  " wins the game!");
			detectClicks = false;
		}else {
			if (currentBoardGame.getCurrentGameState() == GameState.draw) {
				JOptionPane.showMessageDialog(null, "Game is a draw!");
			}
		}
		
		System.out.println(currentBoardGame.getBoard());
	}
	
	public boolean gameHasStopped(){
		return !detectClicks;
	}
}

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

package com.sai.model;

import java.awt.Point;
import java.util.List;

import com.sai.exception.InvariantException;
import com.sai.game.BoardGame;

/**
 * Represents a {@link Board} meand to be used by a {@link BoardGame}. It is a representation
 * of a two-dimensional {@link Board} that can be used by two players, X and O. Subclasses must
 * override the {@link #getEmptyValidSpaces()} function to specify the behavior of the board.
 * 
 * @author Christos Sotiriou
 *
 */
public abstract class BoardTwoPlayers extends Board{
	
	/**
	 * Integer for denoting player X
	 */
	public final static int PLAYER_X = 1;
	
	/**
	 * Integer for implying player O
	 */
	public final static int PLAYER_O = 2;
	
	/**
	 * Integer that means that this field is empty.
	 */
	public final static int PLAYER_EMPTY = 0;
	
	
	
	
	public BoardTwoPlayers(int sizeX, int sizeY) throws InvariantException{
		super(sizeX, sizeY);
		reset();
	}
	
	public BoardTwoPlayers(int [][] board) throws InvariantException{
		super(board);
	}
	
	/**
	 * Resets the board to its default state (all empty)
	 */
	public void reset(){
		setAllSpacesTo(PLAYER_EMPTY);
	}
	
	
	/**
	 * Returns whether the move about to be made is a valid move or not.
	 * Different implementations of a {@link BoardTwoPlayers} can have different
	 * attributes, so valid and invalid moves are determined depending on
	 * whether the move about to be made is contained in the
	 * result of the {@link #getEmptyValidSpaces()} function (which must be overriden
	 * by the subclasses)
	 * @param move
	 * @return
	 */
	public boolean isValidMove(Point move){
		if (getEmptyValidSpaces().contains(move)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns a list of empty {@link Point} elements, which show the available positions for
	 * play.
	 * @return
	 */
	abstract public List<Point> getEmptyValidSpaces();
	
	
	
	/**
	 * Gets the symbol corresponding to an integer. This is for the purpose of displaying
	 * the current state of the board to the console.
	 * @param symbolNum
	 * @return
	 */
	private char getPlayerSymbol(int symbolNum){
		switch (symbolNum) {
		case 0:
			return '-';
		case 1:
			return 'X';
		default:
			return 'O';
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		
		for (int i = 0; i < internalBoard.length; i++) {
			
			for (int j = 0; j < internalBoard[i].length; j++) {
				result += "| " + getPlayerSymbol(internalBoard[i][j]) + " ";
			}
			result += "|\n";
		}
		return result;
	}
}

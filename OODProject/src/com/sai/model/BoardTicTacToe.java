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
import java.util.ArrayList;
import java.util.List;

import com.sai.exception.InvariantException;


/**
 * Represents a {@link BoardTwoPlayers}, specific for Noughts and Crosses game (otherwise known as Tic-Tac-Toe).
 * It is a 3x3 board, and the players can play in each position not already taken by another player.
 * @author Christos Sotiriou
 *
 */
public class BoardTicTacToe extends BoardTwoPlayers {

	public BoardTicTacToe(int sizeX, int sizeY) throws InvariantException {
		super(sizeX, sizeY);
	}
	
	public BoardTicTacToe(int [][] board) throws InvariantException{
		super(board);
	}
	
	public BoardTicTacToe() throws InvariantException{
		super(3,3);
	}

	
	@Override
	public List<Point> getEmptyValidSpaces(){
		List<Point> result = new ArrayList<Point>();
		for (int i = 0; i < getInternalBoard().length; i++) {
			for (int j = 0; j < getInternalBoard()[i].length; j++) {
				if (getInternalBoard()[i][j] == BoardTwoPlayers.PLAYER_EMPTY) {
					result.add(new Point(j,i));
				}
			}
		}
		return result;
	}
}

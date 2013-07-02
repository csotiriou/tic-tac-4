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
 * An implementation of a {@link BoardTwoPlayers}, which gives the attributes necessary for a board
 * to be used by a game of Connect-4. The valid moves in each state of the board are determined by
 * getting the empty spaces on top of each piece.
 * 
 * @author Christos Sotiriou
 *
 */
public class BoardConnect4 extends BoardTwoPlayers{

	public BoardConnect4(int sizeX, int sizeY) throws InvariantException {
		super(sizeX, sizeY);
	}

	public BoardConnect4(int[][] board) throws InvariantException {
		super(board);
	}

	
	
	@Override
	public Point getCenterCoordinate() {
		int i = (int) Math.floor( (this.getWidth() - 1) /2);
		int j = getHeight() -1;
		return new Point(i,j);
	}
	
	@Override
	public List<Point> getEmptyValidSpaces() {
		List<Point> result = new ArrayList<Point>();
		
		for (int i = 0; i < this.getInternalBoard().length; i++) {
			for (int j = 0; j < this.getInternalBoard()[i].length; j++) {

				int currentValue = this.internalBoard[i][j];
				if (currentValue == PLAYER_EMPTY) {	
					//check the space underneath
					if (canMakeMove(new Point(j,i+1))) {
						int currentUnderneathTileValue = this.internalBoard[i+1][j];
						if (currentUnderneathTileValue != PLAYER_EMPTY) {
							result.add(new Point(j,i));
						}
					}else{
						if (i == this.getHeight()-1) {
							result.add(new Point(j,i));
						}
					}
				}
			}
		}
		return result;
	}
	@Override
	public boolean invariant() {
		
		int offset = 0;
		while (offset < getInternalBoard()[0].length) {
			boolean lastPieceEmpty = true;
			for (int i = 0; i < getInternalBoard().length; i++) {
				if (getInternalBoard()[i][offset] != BoardTwoPlayers.PLAYER_EMPTY) {
					lastPieceEmpty = false;
				}else{
					if (lastPieceEmpty == false) {
						return false;
					}
				}
			}
			offset ++;
			
		}	
		return super.invariant();
	}
}

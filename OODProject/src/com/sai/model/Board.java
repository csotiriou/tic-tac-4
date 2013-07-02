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
import java.util.Arrays;

import com.sai.abs.InvariantInterface;
import com.sai.exception.InvariantException;
import com.sai.util.Util;


/**
 * Generic board implementation that represents a two-dimensional board, with
 * arbitrary width and height.
 * Note that when trying to get a position on the board, you must use the {@link #getPiece(int, int)} function, and
 * not acces directly the {@link #internalBoard}, since it will handle coordinate transformations properly.
 * 
 * Note that in the {@link Board} the piece representation will not have the same coordinate representation as the {@link #internalBoard}
 * array. The coordinate system employed by the {@link Board} and its subclasses is the Machine Coordinate System approach. Example:
 * Considering a board whose representation is this:<br>
 * | 1 | 2 | 3 | 4 | <br>
 * | 5 | 6 | 7 | 8 | <br>
 * | 9 | 10 | 11 | 12 | <br>
 * 
 * -- getPiece(new Point(3,0)) will return "3" <br>
 * -- getPiece(new Point(0 1)) will return "5" <br>
 * -- getPiece(new Point(4 1)) will return "7" <br>
 * 
 * 
 * @author Christos Sotiriou
 *
 */
public class Board implements InvariantInterface{

	protected int [][] internalBoard;

	protected int width;
	protected int height;


	/**
	 * Initializes a {@link Board} with sizes denoted by the arguments.
	 * @param sizeX
	 * @param sizeY
	 * @throws InvariantException
	 */
	public Board(int sizeX, int sizeY)throws InvariantException{
		width = sizeY;
		height = sizeX;
		internalBoard = new int[sizeX][sizeY];
		if (!invariant()) {
			throw new InvariantException("Cannot instantiate a Board class with this board array;");
		}
	}

	/**
	 * Initializes the board from a two dimensional array. The array must be orthogonal or an {@link InvariantException} is thrown
	 * The array is copied, not assigned, so the original one can be freely modified
	 * @param board
	 * @throws InvariantException
	 */
	public Board(int [][] board) throws InvariantException{
		this(board.length, board[0].length);
		if (Util.isOrthogonalArray(board)) {
			internalBoard = Util.new2DArrayFromA2DArray(board);
			if (!invariant()) {
				throw new InvariantException("Cannot instantiate a Board class with this board array;");
			}
		}else{
			this.internalBoard = null;
			throw new InvariantException("Board must be orthogonal");
		}
	}


	/**
	 * returns a {@link Point} indicating the center coordinate
	 * @return
	 */
	public Point getCenterCoordinate(){
		int i = (int) Math.floor( (this.getWidth() - 1) /2);
		int j = (int) Math.floor( (this.getHeight() - 1) /2);
		return new Point(i,j);
	}

	/**
	 * Sets all pieces of the {@link Board} to the given value
	 * @param num
	 */
	public void setAllSpacesTo(int num){
		for (int i = 0; i < internalBoard.length; i++) {
			for (int j = 0; j < internalBoard[i].length; j++) {
				internalBoard[i][j] = num;
			}
		}
	}


	/**
	 * Determined if the move can be made (does a bound cheking)
	 * @param movePoint
	 * @return
	 */
	public boolean canMakeMove(Point movePoint){
		return canMakeMove(movePoint.y, movePoint.x);
	}

	/**
	 * Checks if the current move can be made
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean canMakeMove(int i, int j){
		if (i < internalBoard.length) {
			if (j < internalBoard[i].length) {
				return true;
			}
		}
		return false;
	}


	void makeMove(int i, int j, int move){
		internalBoard[i][j] = move;
	}

	/**
	 * Makes the move. Does not do bound checking. For bounds checking,
	 * use first the {@link #canMakeMove(Point)} function to avoid errors
	 * @param i
	 * @param j
	 * @param move
	 */
	public void makeMove(Point movePoint, int move){
		makeMove(movePoint.y, movePoint.x, move);
	}
	
	/**
	 * Make many moves at once
	 * @param points
	 */
	public void makeMoves(Point [] points, int move){
		for (Point point : points) {
			makeMove(point, move);
		}
	}

	@Override
	public int hashCode() {
		int sum = 0;
		for (int i = 0; i < getInternalBoard().length; i++) {
			for (int j = 0; j < getInternalBoard()[i].length; j++) {
				sum += getInternalBoard()[i][j] + i + j;
			}
		}
		return sum;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!obj.getClass().equals(this.getClass())) {
			return false;
		}
		Board otherboard = (Board) obj;
		if (otherboard.invariant() && Arrays.deepEquals(internalBoard, otherboard.getInternalBoard())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean invariant() {
		int width = getInternalBoard().length;
		int firstHeight = getInternalBoard()[0].length;
		for (int i = 0; i < width; i++) {
			if (getInternalBoard()[i].length != firstHeight) {
				return false;
			}
		}
		return (
				internalBoard != null &&
				internalBoard[0].length > 1
				);
	}
	/**
	 * Gets the piece identified by i,j. Note that the {@link #getPiece(Point)} function
	 * takes into account the coordinate transformations. More specifically, the {@link #getInternalBoard()}[i][j]
	 * will not have the same result with the {@link #getPiece(Point)} where {@link Point} = new Point(i,j). The coordinates
	 * are being decided as "X" and "Y", where x is the horizontal axis, increasing from left to right (minimum is zero) and
	 * y is the vertical axis, which increases from top to botton.
	 * 
	 * Example:
	 * Considering a board whose representation is this:<br>
	 * | 1 | 2 | 3 | 4 | <br>
	 * | 5 | 6 | 7 | 8 | <br>
	 * | 9 | 10 | 11 | 12 | <br>
	 * 
	 * -- getPiece(new Point(3,0)) will return "3" <br>
	 * -- getPiece(new Point(0 1)) will return "5" <br>
	 * -- getPiece(new Point(4 1)) will return "7" <br>
	 * 
	 */
	private int getPiece(int i, int j){
		return internalBoard[i][j];
	}

	public int getPiece(Point movePoint){
		return getPiece(movePoint.y, movePoint.x);
	}


	public int[][] getInternalBoard() {
		return internalBoard;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	@Override
	public String toString() {
		String result = "";

		for (int i = 0; i < internalBoard.length; i++) {

			for (int j = 0; j < internalBoard[i].length; j++) {
				result += "| " + internalBoard[i][j] + " ";
			}
			result += "|\n";
		}
		return result;
	}
}

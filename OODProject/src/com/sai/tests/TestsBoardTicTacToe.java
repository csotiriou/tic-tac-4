package com.sai.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.List;

import org.junit.Test;

import com.sai.exception.InvariantException;
import com.sai.model.BoardTicTacToe;
import com.sai.model.BoardTwoPlayers;

public class TestsBoardTicTacToe extends TestsBoardAbstract{

	@Test
	public void testTicTacToeGetValidSpaces() throws InvariantException{
		BoardTwoPlayers board = getNewEmptyBoard();
		
		board.makeMove(new Point(1,2), BoardTwoPlayers.PLAYER_X);
		board.makeMove(new Point(0, 1), BoardTwoPlayers.PLAYER_O);
		
		List<Point> emptyValidSpaces = board.getEmptyValidSpaces();
		assertFalse(emptyValidSpaces.contains(new Point(1,2)));
		assertFalse(emptyValidSpaces.contains(new Point(0,1)));
		
		assertTrue(emptyValidSpaces.contains(new Point(0,0)));
		assertTrue(emptyValidSpaces.contains(new Point(1,0)));
		assertTrue(emptyValidSpaces.contains(new Point(2,0)));
		assertTrue(emptyValidSpaces.contains(new Point(1,1)));
		assertTrue(emptyValidSpaces.contains(new Point(2,1)));
		assertTrue(emptyValidSpaces.contains(new Point(0,2)));
		assertTrue(emptyValidSpaces.contains(new Point(2,2)));
	}
	
	@Override
	public BoardTwoPlayers getNewBoard(int[][] board) throws InvariantException {
		return new BoardTicTacToe(board);
	}
	@Override
	public BoardTwoPlayers getNewEmptyBoard() throws InvariantException {
		return new BoardTicTacToe();
	}

}

package com.sai.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Test;

import com.sai.exception.InvariantException;
import com.sai.model.Board;

public abstract class TestsBoardAbstract {

	
	@Test
	public void testCreation() throws InvariantException{
		Board currentBoard = getNewEmptyBoard();
		assertNotNull(currentBoard);
		assertTrue(currentBoard.invariant());
	}
	
	@Test
	public void testCreation2() throws InvariantException{
		Board currentBoard = getNewBoard(new int[10][10]);
		assertNotNull(currentBoard);
		assertTrue(currentBoard.invariant());
		assertEquals(currentBoard.getWidth(), 10);
		assertEquals(currentBoard.getWidth(), 10);
		currentBoard.makeMove(new Point(2, 1), 1);
	}
	
	
	@Test
	public void testCreation3() throws InvariantException{
		Board b = new Board(new int[][]{
				{1,2,3,4},
				{5,6,7,8},
				{9,10,11,12}
		});
		int num = 1;
		for (int i = 0; i < b.getHeight(); i++) {
			for (int j = 0; j < b.getWidth(); j++) {
				assertEquals(num, b.getInternalBoard()[i][j]);
				num++;
			}
		}
		
	}
	
	@Test(expected=InvariantException.class)
	public void testInvalidCreation() throws InvariantException{
		getNewBoard(new int[][]{
				{0,0,0},
				{0,0},
				{0,0,0}
		});
	}
	
	@Test
	public void testEquals() throws InvariantException{
		assertFalse(getNewEmptyBoard().equals(null));
		assertTrue(getNewEmptyBoard().equals(getNewEmptyBoard()));
		assertFalse(getNewEmptyBoard().equals(getNewBoard(new int[10][10])));
	}
	
	@Test
	public void testCanMakeMove() throws InvariantException{
		Board currentBoard = getNewEmptyBoard();
		assertFalse(currentBoard.canMakeMove(new Point(100,100)));
		assertTrue(currentBoard.canMakeMove(new Point(0, 0)));
	}
	
	@Test
	public void testMove() throws InvariantException{
		Board currentBoard = getNewEmptyBoard();
		currentBoard.makeMove(new Point(0,0), 10);
		currentBoard.makeMove(new Point(1,1), 11);
		
		assertEquals(10, currentBoard.getPiece(new Point(0,0)));
		assertEquals(11, currentBoard.getPiece(new Point(1,1)));
	}
	
	@Test
	public void testSetAllSpace() throws InvariantException{
		Board currentBoard = getNewEmptyBoard();
		currentBoard.setAllSpacesTo(100);
		for (int i = 0; i < currentBoard.getHeight(); i++) {
			for (int j = 0; j < currentBoard.getWidth(); j++) {
				assertEquals(100, currentBoard.getInternalBoard()[i][j]);
			}
		}
	}
	
	@Test
	public void validationTests() throws InvariantException{
		Board b = new Board(new int [][]{
				{0,0,0},
				{0,0,0},
				{0,0,0},
				{0,0,0}
		});
		assertTrue(b.canMakeMove(new Point(2, 0)));
		assertFalse(b.canMakeMove(new Point(3,0)));
		assertTrue(b.canMakeMove(new Point(0,3)));
		assertFalse(b.canMakeMove(new Point(0,4)));
	}
	
	@Test
	public void setAllSpacesTest() throws InvariantException{
		Board b = getNewBoard(new int[][]{
				{1,2,3,4},
				{5,6,7,8},
				{9,10,11,12}
		});
		
		b.setAllSpacesTo(0);
		
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				assertEquals(0, b.getPiece(new Point(i,j)));
			}
		}
	}
	
	public abstract Board getNewBoard(int [][] board) throws InvariantException;
	public abstract Board getNewEmptyBoard() throws InvariantException;
}

package com.sai.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sai.exception.InvariantException;
import com.sai.model.BoardConnect4;
import com.sai.model.BoardTwoPlayers;
import com.sai.util.PointComparator;

public class TestsBoardConnect4 extends TestsBoardAbstract{

	
	@Test(expected = InvariantException.class)
	public void testFalseInit() throws InvariantException{
		new BoardConnect4(new int[][]{
				{0,0,0},
				{0,1,2},
				{0,2,0},
				{1,2,1}
			});
	}
	
	@Test(expected = InvariantException.class)
	public void testFalseInit2() throws InvariantException{
		new BoardConnect4(new int[][]{
				{0,0,0},
				{0,1,2},
				{0,1,2},
				{1,0,1}
			});
	}
	
	@Test
	public void testConnect4GetValidSpaces() throws InvariantException {


		BoardTwoPlayers board = getNewBoard(new int[][]{
				{0,0,0,0},
				{0,0,0,0},
				{0,0,0,0},
				{0,0,0,0}
		});
		
		List<Point> expectedList = new ArrayList<Point>();
		expectedList.add(new Point(0,3));
		expectedList.add(new Point(1,3));
		expectedList.add(new Point(2,3));
		expectedList.add(new Point(3,3));
		
		List<Point> points = board.getEmptyValidSpaces();
		
		Collections.sort(points, new PointComparator());
		Collections.sort(expectedList, new PointComparator());
		
		Assert.assertEquals(expectedList, points);		

		
		
		BoardTwoPlayers bigBoard = getNewBoard(new int[][]{
				{0,0,0,0,0,0},
				{2,0,0,1,0,2},
				{1,2,1,2,1,2}
		});

		List<Point> pointsC = bigBoard.getEmptyValidSpaces();
		expectedList.clear();
		expectedList.add(new Point(0,0));
		expectedList.add(new Point(1,1));
		expectedList.add(new Point(2,1));
		expectedList.add(new Point(3,0));
		expectedList.add(new Point(4,1));
		expectedList.add(new Point(5,0));


		
		Collections.sort(pointsC, new PointComparator());
		Collections.sort(expectedList, new PointComparator());
		
		assertEquals(expectedList, pointsC);
		assertFalse(expectedList.contains(new Point(1,0)));
		assertFalse(expectedList.contains(new Point(2,0)));
		assertFalse(expectedList.contains(new Point(4,0)));
	}
	
	

	@Override
	public BoardTwoPlayers getNewBoard(int[][] board) throws InvariantException {
		return new BoardConnect4(board);
	}

	@Override
	public BoardTwoPlayers getNewEmptyBoard() throws InvariantException {
		return new BoardConnect4(5, 4);
	}

	
	
}

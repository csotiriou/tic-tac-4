package com.sai.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sai.exception.InvariantException;
import com.sai.model.Board;
import com.sai.model.Pattern;
import com.sai.model.PatternMatcher;
import com.sai.util.PointComparator;

public class TestsPatternMatcher {

	PatternMatcher matcher;
	
	
	@Test
	public void creationTests(){
		assertNotNull(matcher);
	}
	
	@Test
	public void findPatternTest1() throws InvariantException{
		Board b = new Board(10, 10);
		List<Pattern> expectedPatterns = new ArrayList<Pattern>();
		List<Pattern> foundPatterns = matcher.findPatternsEqualTo(1, 4, b);
		
		assertEquals(expectedPatterns, foundPatterns);
		
		
		Board b2 = new Board(new int[][]{
				{0,0,1,1,1,1,0},
				{0,0,1,1,0,1,0},
				{0,0,1,1,0,1,0}
			});
		
		
		foundPatterns = matcher.findPatternsEqualTo(1, 4, b2);		
		
		Pattern pattern = new Pattern(new Point(2,0), new Point(3,0), new Point(4,0), new Point(5,0));
		
		pattern.setPatternMatched(1);
		assertTrue(foundPatterns.get(0).equals(pattern));

		
		foundPatterns = matcher.findPatternsEqualTo(0, 3, b2);
		expectedPatterns.clear();
		expectedPatterns.add(new Pattern(new Point[]{new Point(0, 0), new Point(0, 1), new Point(0, 2)}, 0));
		

		assertTrue(foundPatterns.contains(expectedPatterns.get(0)));
		
		List<Point> points = matcher.getWinningMovesForPattern(1, 5, 0, b2);
		assertEquals(2, points.size());
		b2.makeMove(points.get(0), 1);
		
		foundPatterns = matcher.findPatternsEqualTo(1, 5, b2);
		assertEquals(1, foundPatterns.size());
	}

	
	@Test
	public void findPatternTest2() throws InvariantException{
		int [][] alpha = new int [][]{
				{1,0,1,1},
				{0,0,0,0},
				{0,0,0,1},
				{0,0,0,1}
		};
		
		Board b = new Board(alpha);
		
		List<Point> l = matcher.getWinningMovesForPattern(1, 4, 0, b);
		
		assertTrue(l.contains(new Point(1,0)));
		assertTrue(l.contains(new Point(3, 1)));
	}
	
	
	@Test
	public void testPattern() throws InvariantException{
		int [][] alpha = new int [][]{
				{1,0,1,1},
				{0,0,1,0},
				{0,1,0,1},
				{1,0,0,1}
		};
		
		Board b = new Board(alpha);
		
		List<Pattern> list = matcher.findPatternsEqualTo(1, 4, b);
		
		assertEquals(1, list.size());
		
		Pattern resultPattern = list.get(0);
		Pattern p = new Pattern(new Point(3,0), new Point(0,3), new Point(2,1), new Point(1,2), new Point(0,3));
		p.setPatternMatched(1);
		
		Collections.sort(p.getPointsMatchingPattern(), new PointComparator());
		Collections.sort(resultPattern.getPointsMatchingPattern(), new PointComparator());
	}
	
	@Test
	public void testPattern2() throws InvariantException{
		
		int [][] alpha = new int [][]{
				{1,0,1,1},
				{0,0,1,0},
				{0,1,0,1},
				{1,0,0,1}
		};
		
		Board b = new Board(alpha);
		
		List<Pattern> patternsWithZero = matcher.findPatternsEqualTo(0, 2, b);
		assertEquals(9, patternsWithZero.size());
		assertTrue(patternsWithZero.contains(new Pattern(new Point[]{new Point(1, 0), new Point(1, 1)})));
		assertTrue(patternsWithZero.contains(new Pattern(new Point[]{new Point(0, 1), new Point(1, 0)})));
		assertTrue(patternsWithZero.contains(new Pattern(new Point[]{new Point(0, 1), new Point(1, 1)})));
		assertTrue(patternsWithZero.contains(new Pattern(new Point[]{new Point(0, 1), new Point(0, 2)})));
		assertTrue(patternsWithZero.contains(new Pattern(new Point[]{new Point(2, 2), new Point(3, 1)})));
		assertTrue(patternsWithZero.contains(new Pattern(new Point[]{new Point(0, 2), new Point(1, 3)})));
		assertTrue(patternsWithZero.contains(new Pattern(new Point[]{new Point(2, 2), new Point(2, 3)})));
		assertTrue(patternsWithZero.contains(new Pattern(new Point[]{new Point(1, 3), new Point(2, 2)})));
		assertTrue(patternsWithZero.contains(new Pattern(new Point[]{new Point(1, 3), new Point(2, 3)})));
	}



	@Before
	public void setup(){
		matcher = new PatternMatcher();
	}
}

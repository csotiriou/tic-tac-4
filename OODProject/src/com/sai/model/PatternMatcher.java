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


/**
 * {@link PatternMatcher} will match patterns in a two dimensional array, or
 * a {@link Board} and will return useful information, like the coordinates of sequential
 * ints in the array that form a pattern, like 4-in-a-row, 3-in-a-row, diagonal or vertical;
 * @author Christos Sotiriou
 *
 */
public class PatternMatcher {
	
	
	public PatternMatcher(){
		super();
	}
	
	
	
	
	
	/**
	 * Convenience function. Calls {@link #getWinningMovesForPattern(int, int, int, int[][])}
	 * @param matchPattern The symbol in the two dimensional array to look patterns for
	 * @param matchCount How many symbols in a row can actually form a winning condition
	 * @param emptyIndicator The symbol in the array that indicates an empty space
	 * @param board a two dimensional board to search for patterns.
	 * @return a {@link List} of {@link Point} that indicate moves that can be done in order to
	 * form a pattern
	 */
	public List<Point> getWinningMovesForPattern(int matchPattern, int matchCount, int emptyIndicator, Board board){
		return getWinningMovesForPattern(matchPattern, matchCount, emptyIndicator, board.getInternalBoard());
	}
	
	/**
	 * Returns a {@link List} of {@link Point}s that will will ensure winning if a match. The
	 * {@link PatternMatcher} will make make all valid possible moves, and will test if there
	 * is a winning condition for each one. 
	 * @param matchPattern The symbol in the two dimensional array to look patterns for
	 * @param matchCount How many symbols in a row can actually form a winning condition
	 * @param emptyIndicator The symbol in the array that indicates an empty space
	 * @param board a square, two dimensional board to search for patterns.
	 * @return a {@link List} of {@link Point} that indicate moves that can be done in order to
	 * form a pattern
	 */
	public List<Point> getWinningMovesForPattern(int matchPattern, int matchCount, int emptyIndicator, int[][] board){
		List<Point> result = new ArrayList<Point>();
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == emptyIndicator) {
					int oldSymbol = board[i][j];
					board[i][j] = matchPattern;
					if (findPatternsEqualTo(matchPattern, matchCount, board).size() > 0) {
						Point newPoint = new Point(j, i);
						if (!result.contains(newPoint)) {
							result.add(newPoint);	
						}						
					}
					board[i][j] = oldSymbol;
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * Convenience function. Calls {@link #findPatternsEqualTo(int, int, int[][])}
	 * @param matchPattern The symbol to search for in the array.
	 * @param matchCount How many symbols in a row actually form a winnig condition
	 * @param board a square, two dimensional board to search for patterns.
	 * @return a {@link List} of {@link Pattern}s. Each {@link Pattern} will contain {@link Point}s
	 * that form a winning condition.
	 */
	public List<Pattern> findPatternsEqualTo(int matchPattern, int matchCount, Board board){
		return findPatternsEqualTo(matchPattern, matchCount, board.getInternalBoard());
	}
	
	/**
	 * Returns all patterns inside the array that match a matchPattern.
	 * @param matchPattern The symbol to search for in the array.
	 * @param matchCount How many symbols in a row actually form a winnig condition
	 * @param board a square, two dimensional board to search for patterns.
	 * @return a {@link List} of {@link Pattern}s. Each {@link Pattern} will contain {@link Point}s
	 * that form a winning condition.
	 */
	public List<Pattern> findPatternsEqualTo(int matchPattern, int matchCount, int[][] board){
		List<Pattern> resultPatterns = new ArrayList<Pattern>();
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				List<Pattern> result = checkPiecePatterns(i, j, matchPattern, matchCount, board);
				if (result.size() > 0) {
					resultPatterns.addAll(result);
				}
			}
		}
		return resultPatterns;
	}
	
	
	/**
	 * Gets the patters that begin with an individual piece. Only searches from top to bottom, and from
	 * left to right for horizontal searches.
	 * @param board
	 * @param i
	 * @param j
	 * @param valueToFind
	 * @param patternCount
	 * @return
	 */ 
	List<Pattern> checkPiecePatterns(int i, int j, int valueToFind, int patternCount, int[][] board){
		List<Point> horizontal = new ArrayList<Point>();
		List<Point> vertical = new ArrayList<Point>();
		List<Point> diagonal = new ArrayList<Point>();
		
		if (j + patternCount <= board[0].length) {
			for (int k = 0; k <patternCount; k++) {
				int currentElement = board[i][j+k];
				if (currentElement == valueToFind) {
					horizontal.add(new Point(j+k, i));
				}else{
					horizontal.clear();
				}
			}	
		}	
		
		if (i + patternCount <= board.length){
			for (int k = 0; k < patternCount; k++) {
				int currentElement = board[i+k][j];
				if (currentElement == valueToFind) {
					vertical.add(new Point(j, i+k));
				}else{
					vertical.clear();
				}
			}			
		}
		
		//find diagonal left to right, top to bottom
		if (i + patternCount <= board.length && j + patternCount <= board[0].length) {
			for (int k = 0; k < patternCount; k++) {
				int currentElement = board[i+k][j+k];
				if (currentElement == valueToFind) {
					diagonal.add(new Point(j+k, i+k));
				}else{
					diagonal.clear();
				}
			}
		}
		
		
		//find diagonal matches from right to left, top to bottom
		if (patternCount + i <= board.length) {
			if ((j+1) - patternCount >= 0) {
				for (int k = 0; k < patternCount; k++) {
					int currentElement = board[i+k][j-k];
					if (currentElement == valueToFind) {
						diagonal.add(new Point( j-k, i+k));
					}else{
						diagonal.clear();
					}
				}	
			}
		}
		
		List<Pattern> pattern = new ArrayList<Pattern>();
				
		if (horizontal.size() == patternCount) {
			Pattern p = new Pattern();
			p.setPatternMatched(valueToFind);
			p.setPointsMatchingPattern(horizontal);
			pattern.add(p);
		}
		if (vertical.size() == patternCount) {
			Pattern p = new Pattern();
			p.setPatternMatched(valueToFind);
			p.setPointsMatchingPattern(vertical);	
			pattern.add(p);
		}
		if (diagonal.size() == patternCount) {
			Pattern p = new Pattern();
			p.setPatternMatched(valueToFind);
			p.setPointsMatchingPattern(diagonal);
			pattern.add(p);
		}
		return pattern;
	}
}

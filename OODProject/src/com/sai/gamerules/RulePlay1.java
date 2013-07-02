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


package com.sai.gamerules;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.sai.game.BoardGame;
import com.sai.game.BoardGame.GameState;
import com.sai.model.BoardTwoPlayers;
import com.sai.model.PatternMatcher;
import com.sai.util.Util;

/**
 * If you can win, then win
 * @author Christos Sotiriou
 *
 */
public class RulePlay1 extends RulePlay{

	public RulePlay1(String ruleAsString) {
		super(ruleAsString);
	}

	@Override
	public boolean playRuleForGame(BoardGame game, GameState gameState) throws Exception {
		PatternMatcher patternMatcher = new PatternMatcher();

		/**
		 * Get the points which will make this player win the game.
		 */
		List<Point> winningPoints = patternMatcher.getWinningMovesForPattern(game.getPlaySymbolForPlayer(gameState), game.getPiecesCountThatWin(), BoardTwoPlayers.PLAYER_EMPTY, game.getBoard());
		List<Point> validmovePoints = game.getBoard().getEmptyValidSpaces();
		List<Point> actualValidMoves = new ArrayList<Point>();

		/**
		 * The points got are just based on pattern matching with no game login implemented. In a game such as Connect-4, not
		 * all points are valid moves. Therefore, we must filter the moves in order to get
		 * the actual valid ones that will win the game.
		 */
		for (Point point : winningPoints) {
			if (validmovePoints.contains(point)) {
				actualValidMoves.add(point);
			}
		}

		/**
		 * If there are some valid moves to make, shuffle the list and make the first move available.
		 */
		if (actualValidMoves.size() > 0) {
			Util.shuffleList(actualValidMoves);
			game.makeMove(actualValidMoves.get(0), game.getPlaySymbolForPlayer(gameState));
			return true;
		}
		return false;
	}

}

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

import com.sai.game.BoardGame;
import com.sai.game.BoardGame.GameState;
import com.sai.model.Board;
import com.sai.model.BoardTwoPlayers;


/**
 * Rule interface to be implemented by all classes that want to represent a move
 * inside a {@link BoardTwoPlayers}
 * @author Christos Sotiriou
 *
 */
public interface RulePlayInterface {
	
	/**
	 * Makes a move based on a {@link BoardGame}'s {@link Board}
	 * @param game The game to draw the conditions from
	 * @param gameState The {@link GameState} of the current game. Usually, {@link BoardGame#getCurrentGameState()} is used.
	 * @return true if the move could be made using this rule. Otherwise, false.
	 * @throws Exception 
	 */
	public boolean playRuleForGame(BoardGame game, GameState gameState) throws Exception;
	
	
	
	/**
	 * Get the string representation of the rule.
	 * @return the string representation of the rule.
	 */
	public String getRule();
}

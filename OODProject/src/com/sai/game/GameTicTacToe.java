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


package com.sai.game;

import java.util.List;

import com.sai.exception.InvariantException;
import com.sai.gamerules.RulePlay;
import com.sai.model.BoardTicTacToe;
import com.sai.model.BoardTwoPlayers;


/**
 * A game that represents a Noughts and Crosses game (a.k.a. Tic Tac Toe)
 * @author Christos Sotiriou
 *
 */
public class GameTicTacToe extends BoardGame {

	
	protected GameTicTacToe(){
		super();
	}
	
	public GameTicTacToe(PlayerType playerX, PlayerType playerY, List<RulePlay> gameRulesForX, List<RulePlay> gameRulesForO, boolean consoleGame) throws InvariantException {
		super(playerX, playerY, gameRulesForX, gameRulesForO, consoleGame, 3);
	}


	@Override
	protected BoardTwoPlayers createBoard() throws InvariantException {
		return new BoardTicTacToe(3, 3);
	}



}

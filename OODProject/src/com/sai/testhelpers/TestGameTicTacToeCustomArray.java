package com.sai.testhelpers;

import java.util.List;

import com.sai.exception.InvariantException;
import com.sai.game.GameTicTacToe;
import com.sai.gamerules.RulePlay;
import com.sai.model.BoardTicTacToe;
import com.sai.model.BoardTwoPlayers;

public class TestGameTicTacToeCustomArray extends GameTicTacToe {

	BoardTicTacToe boardToUse;
	
	protected TestGameTicTacToeCustomArray(){
		super();
	}
	
	public TestGameTicTacToeCustomArray(PlayerType playerX, PlayerType playerY, List<RulePlay> gameRulesForX, List<RulePlay> gameRulesForO, boolean consoleGame, BoardTicTacToe board) throws InvariantException {
		this();
		this.boardToUse = board;
		initWithVariables(playerX, playerY, gameRulesForX, gameRulesForO, consoleGame, 3);
	}

	@Override
	protected BoardTwoPlayers createBoard() throws InvariantException {
		return boardToUse;
	}
}

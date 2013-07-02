package com.sai.testhelpers;

import java.util.List;

import com.sai.exception.InvariantException;
import com.sai.game.GameTicTacToe;
import com.sai.gamerules.RulePlay;
import com.sai.model.BoardTicTacToe;
import com.sai.model.BoardTwoPlayers;

public class GameTestBoardPreFilledTicTacToe extends GameTicTacToe {


	public GameTestBoardPreFilledTicTacToe(PlayerType playerX,
			PlayerType playerY, List<RulePlay> gameRulesForX,
			List<RulePlay> gameRulesForO, boolean consoleGame)
			throws InvariantException {
		super(playerX, playerY, gameRulesForX, gameRulesForO, consoleGame);
	}

	@Override
	protected BoardTwoPlayers createBoard() throws InvariantException {
		return new BoardTicTacToe(new int [][]{
				{BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O},
				{BoardTwoPlayers.PLAYER_EMPTY,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O},
				{BoardTwoPlayers.PLAYER_X, BoardTwoPlayers.PLAYER_O ,BoardTwoPlayers.PLAYER_X}
		});
	}

}

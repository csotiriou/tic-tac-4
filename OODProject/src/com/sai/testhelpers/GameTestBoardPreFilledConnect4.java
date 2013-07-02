package com.sai.testhelpers;

import java.util.List;

import com.sai.exception.InvariantException;
import com.sai.game.GameConnect4;
import com.sai.gamerules.RulePlay;
import com.sai.model.BoardConnect4;
import com.sai.model.BoardTwoPlayers;

public class GameTestBoardPreFilledConnect4 extends GameConnect4 {

	public GameTestBoardPreFilledConnect4(PlayerType playerX,
			PlayerType playerY, List<RulePlay> gameRulesForX,
			List<RulePlay> gameRulesForO, boolean consoleGame)
			throws InvariantException {
		super(playerX, playerY, gameRulesForX, gameRulesForO, consoleGame);
	}

	@Override
	protected BoardTwoPlayers createBoard() throws InvariantException {
		BoardConnect4 connect4Game = new BoardConnect4(new int[][]{
				{BoardTwoPlayers.PLAYER_EMPTY, BoardTwoPlayers.PLAYER_EMPTY,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O},
				{BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O},
				{BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X},
				{BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X},
				{BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X,BoardTwoPlayers.PLAYER_O,BoardTwoPlayers.PLAYER_X},
		});
		return connect4Game;
	}
	
}
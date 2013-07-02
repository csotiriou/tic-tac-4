package com.sai.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.List;

import org.junit.Test;

import com.sai.exception.GameException;
import com.sai.exception.InvariantException;
import com.sai.game.BoardGame;
import com.sai.game.GameConnect4;
import com.sai.game.BoardGame.GameState;
import com.sai.game.BoardGame.PlayerType;
import com.sai.gamerules.RulePlay;
import com.sai.model.BoardTwoPlayers;
import com.sai.testhelpers.GameTestBoardPreFilledConnect4;

public class TestsGameConnect4 extends TestsGameAbstract {

	
	@Test
	public void testWinState() throws Exception{
		BoardGame game = getNewGame();
		
		game.makeMove(new Point(0,4), BoardTwoPlayers.PLAYER_X);
		game.makeMove(new Point(1,4), BoardTwoPlayers.PLAYER_O);
		
		assertFalse(game.winStateForGameState(game.getCurrentGameState()));
		game.makeMove(new Point(0,3), BoardTwoPlayers.PLAYER_X);
		game.makeMove(new Point(1,3), BoardTwoPlayers.PLAYER_O);
		
		game.makeMove(new Point(0,2), BoardTwoPlayers.PLAYER_X);
		game.makeMove(new Point(1,2), BoardTwoPlayers.PLAYER_O);
		
		assertFalse(game.winStateForGameState(game.getCurrentGameState()));
		game.makeMove(new Point(0,1), BoardTwoPlayers.PLAYER_X);
	
		assertTrue(game.winStateForGameState(game.getCurrentGameState()));
		
		
	}
	
	@Test(expected=GameException.class)
	public void testWinStateCannotMoveAfterGameFinished() throws Exception{
		BoardGame game = getNewGame();
		
		game.makeMove(new Point(0,5), BoardTwoPlayers.PLAYER_X);
		game.makeMove(new Point(1,5), BoardTwoPlayers.PLAYER_O);
		
		game.makeMove(new Point(0,4), BoardTwoPlayers.PLAYER_X);
		game.makeMove(new Point(1,4), BoardTwoPlayers.PLAYER_O);
		
		game.makeMove(new Point(0,3), BoardTwoPlayers.PLAYER_X);
		game.makeMove(new Point(1,3), BoardTwoPlayers.PLAYER_O);
		game.makeMove(new Point(0,2), BoardTwoPlayers.PLAYER_X);
		assertTrue(game.winStateForGameState(game.getCurrentGameState()));
		
		game.makeMove(new Point(1,2), BoardTwoPlayers.PLAYER_O);
	}
	
	@Test
	public void testDraw() throws Exception{
		BoardGame newGame = new GameTestBoardPreFilledConnect4(PlayerType.human, PlayerType.human, getGoodRules(), getGoodRules(), false);
		newGame.makeMoveForNextCurrentPlayer(newGame.getBoard().getEmptyValidSpaces().get(0));
		newGame.makeMoveForNextCurrentPlayer(newGame.getBoard().getEmptyValidSpaces().get(0));
		
		assertFalse(newGame.winStateForGameState(newGame.getCurrentGameState()));
		assertEquals(GameState.draw, newGame.getCurrentGameState());
		
	}
	
	
	
	@Override
	public BoardGame getNewGame() throws InvariantException {
		BoardGame result = null;
		List<RulePlay> rules = getGoodRules();
		try {
			result = new GameConnect4(PlayerType.human, PlayerType.human, rules, rules, false);
		} catch (InvariantException e) {
			e.printStackTrace();
		}
		return result;
	}

	
	
	
}

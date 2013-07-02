package com.sai.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.List;

import org.junit.Test;

import com.sai.exception.InvariantException;
import com.sai.game.BoardGame;
import com.sai.game.GameTicTacToe;
import com.sai.game.BoardGame.GameState;
import com.sai.game.BoardGame.PlayerType;
import com.sai.gamerules.RulePlay;
import com.sai.model.BoardTwoPlayers;
import com.sai.testhelpers.GameTestBoardPreFilledTicTacToe;


public class TestsGameNoughtsAndCrosses extends TestsGameAbstract {

	
	
	
	@Test
	public void creationtests() throws InvariantException{
		BoardGame game = getNewGame();
		
		assertEquals(game.getBoard().getWidth(), 3);
		assertEquals(game.getBoard().getHeight(), 3);
		assertEquals(game.getPiecesCountThatWin(), 3);
	}
	
	
	
	
	@Test
	public void testWinState() throws Exception{
		BoardGame game = getNewGame();
		
		game.makeMove(new Point(0,0), BoardTwoPlayers.PLAYER_X);
		game.makeMove(new Point(1,0), BoardTwoPlayers.PLAYER_O);
		
		assertFalse(game.winStateForGameState(game.getCurrentGameState()));
		game.makeMove(new Point(1,1), BoardTwoPlayers.PLAYER_X);
		game.makeMove(new Point(0,1), BoardTwoPlayers.PLAYER_O);
	
		assertFalse(game.winStateForGameState(game.getCurrentGameState()));
		game.makeMove(new Point(2,2), BoardTwoPlayers.PLAYER_X);
		assertTrue(game.winStateForGameState(GameState.xPlays));
		
		assertEquals(GameState.xWins, game.getCurrentGameState());
	}
	
	@Test(expected=InvariantException.class)
	public void testWinStateCannotMakeMove() throws Exception{
		BoardGame game = getNewGame();
		
		game.makeMove(new Point(0,0), BoardTwoPlayers.PLAYER_X);
		game.makeMove(new Point(1,0), BoardTwoPlayers.PLAYER_O);
		game.makeMove(new Point(1,1), BoardTwoPlayers.PLAYER_X);
		game.makeMove(new Point(0,1), BoardTwoPlayers.PLAYER_O);
		game.makeMove(new Point(2,2), BoardTwoPlayers.PLAYER_X);
		assertTrue(game.winStateForGameState(GameState.xPlays));
		
		assertEquals(GameState.xWins, game.getCurrentGameState());
		
		game.makeMove(new Point(), 1);
	}
	
	
	@Test
	public void testDraw() throws Exception{
		BoardGame game = new GameTestBoardPreFilledTicTacToe(PlayerType.human, PlayerType.human, getGoodRules(), getGoodRules(), false);
		game.makeAIMoveForGameState(game.getCurrentGameState());
		assertEquals(GameState.draw, game.getCurrentGameState());
	}
	
	
	
	@Override
	public BoardGame getNewGame() throws InvariantException {
		BoardGame result = null;
		List<RulePlay> rules = getGoodRules();
		try {
			result = new GameTicTacToe(PlayerType.human, PlayerType.human, rules, rules, false);
				
		} catch (InvariantException e) {
			e.printStackTrace();
		}
		return result;
		
	}

	
	


}

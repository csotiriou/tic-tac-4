package com.sai.tests;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Assert;
import org.junit.Test;

import com.sai.game.BoardGame;
import com.sai.game.BoardGame.GameState;
import com.sai.model.BoardTwoPlayers;

public class TestRulesConnect4 extends TestsRulesAbstract {

	@Test
	public void testIfYouCanWinThenWin1() throws Exception{
		BoardGame game = gameConnect4WithRules(getGoodRules(), getGoodRules());
		game.makeMovesInSuccession(
				new Point(0, 4), 
				new Point(0, 3),
				new Point(1, 4), 
				new Point(1, 3),
				new Point(2, 4),
				new Point(2, 3)
		);
		game.makeAIMoveForGameState(game.getCurrentGameState());
		assertEquals(BoardTwoPlayers.PLAYER_X, game.getBoard().getPiece(new Point(3, 4)));
		assertEquals(GameState.xWins, game.getCurrentGameState());
	}
	
	@Test
	public void testIfYouCanWinThenWin2() throws Exception{
		BoardGame game = gameConnect4WithRules(getGoodRules(), getGoodRules());
		game.makeMovesInSuccession(
				new Point(0, 4),
				new Point(1, 4),
				new Point(0, 3),
				new Point(1, 3),
				new Point(0,2),
				new Point(1,2)
		);
		game.makeAIMoveForGameState(game.getCurrentGameState());
		assertEquals(BoardTwoPlayers.PLAYER_X, game.getBoard().getPiece(new Point(0, 1)));
		assertEquals(GameState.xWins, game.getCurrentGameState());
	}
	
	@Test
	public void ifYouCanBlockAWinThenBlockAWin1() throws Exception{
		BoardGame game = gameConnect4WithRules(getGoodRules(), getGoodRules());
		game.makeMovesInSuccession(
				new Point(0, 4),
				new Point(1, 4),
				new Point(0, 3),
				new Point(1, 3),
				new Point(0,2)
		);
		
		game.makeAIMoveForGameState(game.getCurrentGameState());
		assertEquals(BoardTwoPlayers.PLAYER_O, game.getBoard().getPiece(new Point(0, 1)));
		Assert.assertNotSame(GameState.xWins, game.getCurrentGameState());
	}
	
	
	@Test
	public void testMoveToCenterIfFree() throws Exception {
		BoardGame game = gameConnect4WithRules(getGoodRules(), getGoodRules());
		game.makeAIMoveForGameState(game.getCurrentGameState());
		assertEquals(BoardTwoPlayers.PLAYER_X, game.getBoard().getPiece(new Point(2, 4)));
	}
}

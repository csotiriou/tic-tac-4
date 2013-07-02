package com.sai.tests;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Assert;
import org.junit.Test;

import com.sai.game.BoardGame;
import com.sai.game.BoardGame.GameState;
import com.sai.model.BoardTwoPlayers;

public class TestRulesTicTacToe extends TestsRulesAbstract{


	@Test
	public void testIfYouCanWinThenWin1() throws Exception{
		BoardGame game = gameTicTacToeWithRules(getGoodRules(), getGoodRules());
		
		game.makeMovesInSuccession(
				new Point(0,0),
				new Point(1,0),
				new Point(0,1),
				new Point(1,1)
				);
		game.makeAIMoveForGameState(game.getCurrentGameState());
		assertEquals(BoardTwoPlayers.PLAYER_X, game.getBoard().getPiece(new Point(0, 2)));
		assertEquals(GameState.xWins, game.getCurrentGameState());
	}
	
	@Test
	public void testIfYouCanWinThenWin2() throws Exception{
		BoardGame game = gameTicTacToeWithRules(getGoodRules(), getGoodRules());
		
		game.makeMovesInSuccession(
				new Point(0,2),
				new Point(0,0),
				new Point(2,2),
				new Point(2,0)
				);
		game.makeAIMoveForGameState(game.getCurrentGameState());
				
		assertEquals(BoardTwoPlayers.PLAYER_X, game.getBoard().getPiece(new Point(1, 2)));
		assertEquals(GameState.xWins, game.getCurrentGameState());
	}
	
	@Test
	public void ifYouCanBlockAWinThenBlockAWin1() throws Exception{
		BoardGame game = gameTicTacToeWithRules(getGoodRules(), getGoodRules());
		game.makeMovesInSuccession(
				new Point(0, 0),
				new Point(0, 1), 
				new Point(1, 0)
		);
		
		game.makeAIMoveForGameState(game.getCurrentGameState());
		assertEquals(BoardTwoPlayers.PLAYER_O, game.getBoard().getPiece(new Point(2, 0)));
		Assert.assertNotSame(GameState.xWins, game.getCurrentGameState());
	}
	
	@Test
	public void testMoveToCenterIfFree() throws Exception {
		BoardGame game = gameTicTacToeWithRules(getGoodRules(), getGoodRules());
		game.makeAIMoveForGameState(game.getCurrentGameState());
		assertEquals(BoardTwoPlayers.PLAYER_X, game.getBoard().getPiece(new Point(1,1)));
		
		BoardGame game2 = gameTicTacToeWithRules(getGoodRules(), getGoodRules());
		game2.makeMovesInSuccession(new Point(0,0), new Point(1, 0), new Point(0,2), new Point(0, 1));
		game2.makeAIMoveForGameState(game2.getCurrentGameState());
		assertEquals(BoardTwoPlayers.PLAYER_X, game2.getBoard().getPiece(new Point(1, 1)));
	}

}

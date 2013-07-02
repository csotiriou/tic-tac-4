package com.sai.tests;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.sai.exception.InvariantException;
import com.sai.game.BoardGame;
import com.sai.game.BoardGame.GameState;
import com.sai.gamerules.GameRuleFactory;
import com.sai.gamerules.RulePlay;
import com.sai.model.BoardTwoPlayers;

public abstract class TestsGameAbstract {

	
	@Test
	public void testCreation() throws InvariantException{
		BoardGame currentGame = getNewGame();
		Assert.assertNotNull(currentGame);
		Assert.assertTrue(currentGame.invariant());
		
		assertEquals(currentGame.getCurrentGameState(), GameState.xPlays);
		assertEquals(currentGame.getPlaySymbolForOppositePlayer(currentGame.getCurrentGameState()), currentGame.getPlaySymbolForPlayer(GameState.oPlays));
		
		assertEquals(currentGame.getPlaySymbolForPlayer(GameState.oPlays), BoardTwoPlayers.PLAYER_O);
		assertEquals(currentGame.getPlaySymbolForPlayer(GameState.xPlays), BoardTwoPlayers.PLAYER_X);
	}
	
	
	@Test
	public void testsMove1() throws Exception{
		BoardGame game = getNewGame();

		Point firstMove = game.getBoard().getEmptyValidSpaces().get(0);
		
		game.makeMove(firstMove, BoardTwoPlayers.PLAYER_X);
		
		assertEquals(BoardTwoPlayers.PLAYER_X, game.getBoard().getPiece(firstMove));
		assertEquals(GameState.oPlays, game.getCurrentGameState());
		assertEquals(game.getPlaySymbolForOppositePlayer(game.getCurrentGameState()), game.getPlaySymbolForPlayer(GameState.xPlays));
		
		Point secondMove = game.getBoard().getEmptyValidSpaces().get(0);
		game.makeMoveForNextCurrentPlayer(secondMove);
		
		assertEquals(BoardTwoPlayers.PLAYER_O, game.getBoard().getPiece(secondMove));
		assertEquals(GameState.xPlays, game.getCurrentGameState());
		assertEquals(game.getPlaySymbolForOppositePlayer(game.getCurrentGameState()), game.getPlaySymbolForPlayer(GameState.oPlays));	
	}
	
	@Test
	public void testsMove2() throws Exception{
		BoardGame game = getNewGame();
		Point move = game.getBoard().getEmptyValidSpaces().get(0);
		game.makeMoveForNextCurrentPlayer(move);
		assertEquals(BoardTwoPlayers.PLAYER_X, game.getBoard().getPiece(move));
		assertEquals(game.getPlaySymbolForOppositePlayer(game.getCurrentGameState()), game.getPlaySymbolForPlayer(GameState.xPlays));
	}
	
	@Test(expected=Exception.class)
	public void testsInvalidMove() throws Exception{
		BoardGame game = getNewGame();
		game.makeMove(new Point(Integer.MAX_VALUE,Integer.MAX_VALUE), BoardTwoPlayers.PLAYER_X);
	}
	
	/**
	 * get a new {@link BoardGame} instance.
	 * @return
	 * @throws InvariantException 
	 */
	public abstract BoardGame getNewGame() throws InvariantException;
	
	
	public List<RulePlay> getGoodRules() throws InvariantException{
		List<RulePlay> rules = new ArrayList<RulePlay>();
		rules.add(GameRuleFactory.ruleForString("If you can win then win"));
		rules.add(GameRuleFactory.ruleForString("If you can block a win then block a win"));
		rules.add(GameRuleFactory.ruleForString("If the center is free then play in the center"));
		rules.add(GameRuleFactory.ruleForString("Choose one of the remaining free positions randomly"));
		return rules;
	}
}

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

import java.awt.Point;
import java.util.List;
import java.util.Observable;

import com.sai.abs.InvariantInterface;
import com.sai.exception.GameException;
import com.sai.exception.InvariantException;
import com.sai.gamerules.RulePlay;
import com.sai.model.BoardTwoPlayers;
import com.sai.model.Console;
import com.sai.model.Pattern;
import com.sai.model.PatternMatcher;

/**
 * An observable Game controller, handling all game interactions.
 * Must be subclassed in order to customize the setup of the {@link BoardTwoPlayers} needed to play the game.
 * @author Christos Sotiriou
 *
 */
public abstract class BoardGame extends Observable implements InvariantInterface{

	/**
	 * The game board of the current game
	 */
	protected BoardTwoPlayers gameBoard;
	
	/**
	 * Pattern matcher for matching patterns into the current board and determining
	 * winning-losing states.
	 */
	protected PatternMatcher patternMatcher = null;

	/**
	 * The current state of the game. Game flow decisions are
	 * based into the value of this variable.
	 */
	GameState currentGameState = GameState.xPlays;

	/**
	 * The rules which Player X uses to play.
	 */
	List<RulePlay> gameRulesPlayerX = null;
	
	/**
	 * The rules which player O uses to play. 
	 */
	List<RulePlay> gameRulesPlayerO = null;

	
	PlayerType playerXType = PlayerType.unassigned;
	PlayerType playerYType = PlayerType.unassigned;
	
	
	boolean isConsoleGame = true;
	
	/**
	 * How many pieces of the same type in a row can win the game?
	 */
	int piecesCountThatWin = 3;
	
	protected BoardGame(){
		
	}

	/**
	 * Constructor for a {@link BoardGame}. immediately calls {@link #initWithVariables(PlayerType, PlayerType, List, List, boolean, int)}
	 * function. Subclasses can avoid call this constructor directly in order to tackle with the initialization procedure.
	 *  Even when there are two humans playing the game, the class must be initialized with a list of game rules, because during the
	 *  game, one can call the {@link #makeAIMoveForGameState(GameState)} function, giving control to the AI only for this round.
	 * @param playerX Whether playerX is to be handled by the AI or a human. Only makes sense when consoleGame is set to true
	 * @param playerY Whether playerO is to be handled by the AI or a human. Only makes sense when consoleGame is set to true.
	 * @param gameRulesForX the rules for player X. 
	 * @param gameRulesForO the rules for player O.
	 * @param consoleGame Wether this game will be set up to handle console run loops or not. If you are using a {@link BoardGame} for anything else
	 * other than plying with the console, set this to false.
	 * @param winPieceCount
	 * @throws InvariantException
	 */
	public BoardGame(PlayerType playerX, PlayerType playerY, List<RulePlay> gameRulesForX, List<RulePlay> gameRulesForO, boolean consoleGame, int winPieceCount) throws InvariantException{
		initWithVariables(playerX, playerY, gameRulesForX, gameRulesForO, consoleGame, winPieceCount);
	}

	/**
	 * Initializer that is normally called by the constructor. DESIGN CHOICE:
	 * It consists a separate function, in case where subclasses need to change the order of
	 * the initialization proceture;
	 * @param playerX
	 * @param playerY
	 * @param gameRulesForX
	 * @param gameRulesForO
	 * @param consoleGame
	 * @param winPieceCount
	 * @throws InvariantException
	 */
	protected void initWithVariables(PlayerType playerX, PlayerType playerY, List<RulePlay> gameRulesForX, List<RulePlay> gameRulesForO, boolean consoleGame, int winPieceCount) throws InvariantException{
		this.playerXType = playerX;
		this.playerYType = playerY;
		if (gameRulesForO == null || gameRulesForX == null) {
			throw new InvariantException("Rules cannot be null");
		}
		setGameRulesPlayerO(gameRulesForO);
		setGameRulesPlayerX(gameRulesForX);
		this.isConsoleGame = consoleGame;
		this.piecesCountThatWin = winPieceCount;
		setup();
		if (!invariant()) {
			throw new InvariantException("Invariant broken while creating the board game");
		}
	}
	
	public enum PlayerType{
		human, AI, unassigned
	}

	public enum GameState{
		xPlays, oPlays, xWins, oWins, draw
	}
	
	private void setup() throws InvariantException{
		this.gameBoard = createBoard();
		this.patternMatcher = new PatternMatcher();
	}
	
	
	/**
	 * Generate the board that will be used for the game. Subclasses must override this
	 * method in order to setup the board specific for this game. The board will be automatically
	 * connected to the game's pattern matcher.
	 * @return a valid instance of {@link BoardTwoPlayers}
	 * @throws InvariantException
	 */
	protected abstract BoardTwoPlayers createBoard() throws InvariantException;


	/**
	 * The main run loop of the game, dedicated to the console play. 
	 * @throws Exception
	 */
	public void consoleRunLoop() throws Exception{
		currentGameState = GameState.xPlays;
		boolean shouldExit = false;
		while(!shouldExit){
			System.out.println(currentGameState.toString());
			System.out.println(gameBoard);

			if (currentGameState == GameState.xPlays || currentGameState == GameState.oPlays) {
				handlePlayerTurn(currentGameState);
			}else if(currentGameState == GameState.oWins || currentGameState == GameState.xWins){
				handlePlayerWin(currentGameState);
				shouldExit = true;
			}else if (currentGameState == GameState.draw) {
				handlePlayerWin(currentGameState);
				shouldExit = true;
			}
			/*
			 * If there are no more valid moves, and no one has already won, we have a
			 * draw.
			 */
			if (gameBoard.getEmptyValidSpaces().size() == 0 && currentGameState != GameState.xWins && currentGameState != GameState.oWins) {
				currentGameState = GameState.draw;
			}
		}
	}

	
	/**
	 * Called in case where it's the turn of a player to play.
	 * @param gameState
	 * @throws Exception
	 */
	private void handlePlayerTurn(GameState gameState) throws Exception{

		PlayerType currentPlayerType = getPlayerTypeForPlayer(gameState);
		
		if (currentPlayerType == PlayerType.AI) {
			/**
			 * If the player type is AI, then we iterate through all possible game rules,
			 * until an actual move could be made.
			 */
			makeAIMoveForGameState(currentGameState);
		}else{
			/**
			 * IF the player whose turn is to play is human, read from the command line the next point.
			 */
			if (isConsoleGame) {
				moveBasedOnConsoleInput();	
			}
		}
	}

	
	/**
	 * Plays an AI move for the current game state. The move will be done using the currently provided
	 * {@link #gameRulesPlayerO} or {@link #gameRulesPlayerX}, respecting the order in which they are given
	 * by the list.
	 * @param gameState
	 * @throws Exception
	 */
	public void makeAIMoveForGameState(GameState gameState) throws Exception{
		List<RulePlay> properGameRules = (gameState == GameState.oPlays? getGameRulesPlayerO() : getGameRulesPlayerX());
		for (RulePlay rulePlay : properGameRules) {
			boolean moveWasMade = rulePlay.playRuleForGame(this, currentGameState);
			if (moveWasMade) {
				break;
			}
		}
	}
	
	/**
	 * Asks for an input in the console, and if it is valid, it performs the move.
	 * If not, it does nothing.
	 * @return the result of the move. false if the move could not be made
	 * @throws Exception
	 */
	boolean moveBasedOnConsoleInput() throws Exception{
		boolean moveWasValid = false;
		Point nextMove = Console.readPoint("Give a move to make. Syntax: \"x y\" (without quotes)");
		System.out.println("point given: " + nextMove);

		List<Point> availableMoves = gameBoard.getEmptyValidSpaces();
		if (availableMoves.contains(nextMove)) {
			makeMove(nextMove, currentGameState == GameState.xPlays? BoardTwoPlayers.PLAYER_X : BoardTwoPlayers.PLAYER_O);
			moveWasValid = true;
		}else{
			System.out.println("invalid move: " + nextMove + ". You must play in an empty square");
		}
		return moveWasValid;
	}
	
	

	/**
	 * Called in case where a player has won a game.
	 * @param state
	 */
	private void handlePlayerWin(GameState state){
		if (state == GameState.oWins) {
			System.out.println("player O wins!");
		}else if (state == GameState.xWins){
			System.out.println("player X wins!");
		}else{
			System.out.println("Game is a draw");
		}
	}

	
	/**
	 * Returns wether the player denoted by a {@link GameState} has won the game as it stands
	 * now.
	 * @param player
	 * @return
	 */
	public boolean winStateForGameState(GameState player){
		
		if (player.equals(GameState.xWins) || player.equals(GameState.oWins)) {
			/**
			 * In case where the game is already beaten by one player, then the winning
			 * condition is true
			 */
			return true;
		}
		
		int playerSymbol = getPlaySymbolForPlayer(player);
		List<Pattern> list = patternMatcher.findPatternsEqualTo(playerSymbol, this.piecesCountThatWin, this.getBoard());
		if (list.size() > 0) {
			return true;
		}
		return false;
	}





	/**
	 * Called after a move was made in order to change the game state
	 * @throws Exception 
	 */
	private void postMoveCalculations() throws Exception{
		if (winStateForGameState(currentGameState)) {
			this.currentGameState = (currentGameState == GameState.oPlays? GameState.oWins : GameState.xWins);
		}else{
			if (getBoard().getEmptyValidSpaces().size() == 0) {
				this.currentGameState = GameState.draw;
			}else{
				this.currentGameState = (currentGameState == GameState.oPlays? GameState.xPlays : GameState.oPlays);
			}
		}
		
		setChanged();
		notifyObservers();
	}
	
	
	/**
	 * Convenience function. Calls {@link #makeMoveForNextCurrentPlayer(Point)} for each point given in the argument.
	 * After each move, the {@link GameState} of the game is updated to reflect changes.
	 * @param moves the moves to be executed.
	 * @throws Exception an exception in case of any error
	 */
	public void makeMovesInSuccession(Point ... moves) throws Exception{
		for (Point point : moves) {
			makeMoveForNextCurrentPlayer(point);
		}
	}
	
	/**
	 * Makes a move for the player that currently plays
	 * @param movePoint
	 * @throws Exception
	 */
	public void makeMoveForNextCurrentPlayer(Point movePoint) throws Exception{
		makeMove(movePoint, getPlaySymbolForPlayer(currentGameState));
	}
	

	/**
	 * Makes a move designated by the {@link Point} given as the argument.
	 * @param movePoint The point to move
	 * @param moveType the symbol of the move
	 * @throws Exception An exception is thrown in case where the move is being done in an invalid {@link Point}
	 */
	public void makeMove(Point movePoint, int moveType) throws Exception{
		if (currentGameState.equals(GameState.xWins) || currentGameState.equals(GameState.oWins)) {
			throw new InvariantException("Error: Tried to move after the game is finished");
		}
		if (gameBoard.canMakeMove(movePoint)) {
			if (!(gameBoard.getPiece(movePoint) == BoardTwoPlayers.PLAYER_EMPTY) ) {
				throw new GameException("Attempt to play in a non-empty square");
			}else{
				gameBoard.makeMove(movePoint, moveType);
				postMoveCalculations();
			}
		}else{
			throw new GameException("Point " + movePoint + "out of bounds");
		}
	}
	
	public void setGameRulesPlayerO(List<RulePlay> gameRulesPlayerO) {
		this.gameRulesPlayerO = gameRulesPlayerO;
	}
	
	public void setGameRulesPlayerX(List<RulePlay> gameRulesPlayerX) {
		this.gameRulesPlayerX = gameRulesPlayerX;
	}


	public PlayerType getPlayerTypeForPlayer(GameState playerThatPlays){
		if (playerThatPlays == GameState.xPlays) {
			return playerXType;
		}
		return playerYType;
	}

	
	public int getPlaySymbolForPlayer(GameState playerThatPlays){
		if (playerThatPlays == GameState.xPlays) {
			return BoardTwoPlayers.PLAYER_X;
		}
		return BoardTwoPlayers.PLAYER_O;
	}

	public int getPlaySymbolForOppositePlayer(GameState playerThatPlays){
		if (playerThatPlays == GameState.xPlays) {
			return BoardTwoPlayers.PLAYER_O;
		}
		return BoardTwoPlayers.PLAYER_X;
	}
	
	@Override
	public boolean invariant() {
		return (
				patternMatcher != null &&
				gameBoard != null &&
				gameRulesPlayerO != null &&
				gameRulesPlayerX != null &&
				playerXType != PlayerType.unassigned &&
				playerYType != PlayerType.unassigned &&
				piecesCountThatWin > 2
				);
	}
	
	
	
	public BoardTwoPlayers getBoard(){
		return gameBoard;
	}
	
	public int getPiecesCountThatWin() {
		return piecesCountThatWin;
	}

	public void setPlayerXType(PlayerType playerXType) {
		this.playerXType = playerXType;
	}

	public void setPlayerYType(PlayerType playerYType) {
		this.playerYType = playerYType;
	}

	public PlayerType getPlayerXType() {
		return playerXType;
	}

	public PlayerType getPlayerYType() {
		return playerYType;
	}

	public GameState getCurrentGameState() {
		return currentGameState;
	}

	public List<RulePlay> getGameRulesPlayerX() {
		return gameRulesPlayerX;
	}

	public List<RulePlay> getGameRulesPlayerO() {
		return gameRulesPlayerO;
	}

}

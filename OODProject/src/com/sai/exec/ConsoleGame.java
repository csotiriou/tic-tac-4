package com.sai.exec;


import java.io.FileNotFoundException;
import java.util.List;

import com.sai.exception.InvariantException;
import com.sai.game.BoardGame;
import com.sai.game.BoardGame.PlayerType;
import com.sai.game.GameConnect4;
import com.sai.game.GameTicTacToe;
import com.sai.gamerules.GameRuleFactory;
import com.sai.gamerules.GameRules;
import com.sai.gamerules.RulePlay;
import com.sai.model.Console;
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

/**
 * Console materialization of the requirements.
 * The two rule sets are being instantiated by reading the two files, config/badRules, and config/rules.
 * Make alterations in these two documents to change the order of the rules.
 * @author Christos Sotiriou
 *
 */
public class ConsoleGame {
	
	
	public static void main(String[] args) throws InvariantException {		
		GameRules goodRules = new GameRules();
		GameRules badRules = new GameRules();
		
		try {
			badRules.loadRulesFromLocalFile("config/badRules.properties");
			goodRules.loadRulesFromLocalFile("config/rules.properties");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		List<RulePlay> grulesO = GameRuleFactory.rulePlaysForGameRulesForObject(badRules);
		List<RulePlay> grulesX = GameRuleFactory.rulePlaysForGameRulesForObject(goodRules);
				
		
		PlayerType playerType1 = PlayerType.human;
		PlayerType playerType2 = PlayerType.human;
		
		boolean shouldExit = false;
		while (shouldExit == false) {
			int gameType = -1;
			int gameKind = -1;
			boolean validInput = true;
			
			System.out.println("\n");
			System.out.println("choose variant:\n1) AI vs AI\n2)AI vs Player\n3)Player vs Player\n99)Exit the game");
			try {
				gameType = Console.readInt("Enter your choice (1-3)");
				if ( !( (gameType > 0  && gameType < 4) || (gameType == 99) )) {
					System.out.println("Wrong game choice entered");
					validInput = false;
				}
				if (validInput && gameType != 99) {
					System.out.println("Choose game kind: \n1)Noughts and Crosses \n2)Connect-4");
					gameKind = Console.readInt("Enter your choice ('1' or '2')");
					if (!(gameKind == 1 || gameKind == 2)) {
						System.out.println("Wrong game choice entered");
						validInput = false;
					}	
				}
			} catch (Exception e) {
				validInput = false;
				System.out.println("ERROR: wrong argument given ---\n");
			}
			
			if (validInput) {
				switch (gameType) {
				case 1:
					playerType1 = PlayerType.AI;
					playerType2 = PlayerType.AI;
					break;
				case 2:
					playerType1 = PlayerType.AI;
					playerType2 = PlayerType.human;
					break;
				case 3:
					playerType1 = PlayerType.human;
					playerType2 = PlayerType.human;
					break;
				case 99:
					System.out.println("goodbye!");
					break;
				default:
					System.out.println("unrecognized command");
					break;
				}
				
				if (gameType == 99) {
					shouldExit = true;
				}else if (gameType > 0 && gameType < 4){
					BoardGame boardGame = (gameKind == 1? new GameTicTacToe(playerType1, playerType2, grulesO, grulesX, true) : new GameConnect4(playerType1, playerType2, grulesO, grulesX, true));
					try {
						boardGame.consoleRunLoop();
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
			}
		
		}
	}
	
	
	
}

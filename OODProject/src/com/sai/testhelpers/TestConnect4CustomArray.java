package com.sai.testhelpers;

import java.util.List;

import com.sai.exception.InvariantException;
import com.sai.game.GameConnect4;
import com.sai.gamerules.RulePlay;

public class TestConnect4CustomArray extends GameConnect4{

	
	public TestConnect4CustomArray(PlayerType playerX, PlayerType playerY, List<RulePlay> gameRulesForX, List<RulePlay> gameRulesForO, boolean consoleGame) throws InvariantException {
		super(playerX, playerY, gameRulesForX, gameRulesForO, consoleGame);
	}

	
}

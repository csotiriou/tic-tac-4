package com.sai.tests;

import java.util.ArrayList;
import java.util.List;

import com.sai.exception.InvariantException;
import com.sai.game.GameConnect4;
import com.sai.game.GameTicTacToe;
import com.sai.game.BoardGame.PlayerType;
import com.sai.gamerules.GameRuleFactory;
import com.sai.gamerules.RulePlay;

public abstract class TestsRulesAbstract {


	
	public List<RulePlay> getGoodRules() throws InvariantException{
		List<RulePlay> rules = new ArrayList<RulePlay>();
		rules.add(GameRuleFactory.ruleForString("If you can win then win"));
		rules.add(GameRuleFactory.ruleForString("If you can block a win then block a win"));
		rules.add(GameRuleFactory.ruleForString("If the center is free then play in the center"));
		rules.add(GameRuleFactory.ruleForString("Choose one of the remaining free positions randomly"));
		return rules;
	}
	
	public List<RulePlay> getBadRules() throws InvariantException{
		List<RulePlay> rules = new ArrayList<RulePlay>();
		rules.add(GameRuleFactory.ruleForString("Choose one of the remaining free positions randomly"));
		rules.add(GameRuleFactory.ruleForString("If the center is free then play in the center"));
		rules.add(GameRuleFactory.ruleForString("If you can block a win then block a win"));
		rules.add(GameRuleFactory.ruleForString("If you can win then win"));
		return rules;
	}
	
	public List<RulePlay> getMediumRules() throws InvariantException{
		List<RulePlay> rules = new ArrayList<RulePlay>();
		rules.add(GameRuleFactory.ruleForString("If the center is free then play in the center"));
		rules.add(GameRuleFactory.ruleForString("If you can block a win then block a win"));
		rules.add(GameRuleFactory.ruleForString("If you can win then win"));
		rules.add(GameRuleFactory.ruleForString("Choose one of the remaining free positions randomly"));
		return rules;
	}
	
	public GameTicTacToe gameTicTacToeWithRules(List<RulePlay> rulesForPlayerO, List<RulePlay> rulesForPlayerX) throws InvariantException{
		return new GameTicTacToe(PlayerType.human, PlayerType.human, rulesForPlayerX, rulesForPlayerO, false);
	}
	
	
	public GameConnect4 gameConnect4WithRules(List<RulePlay> rulesForPlayerO, List<RulePlay> rulesForPlayerX) throws InvariantException{
		return new GameConnect4(PlayerType.human, PlayerType.human, rulesForPlayerX, rulesForPlayerO, false);
	}
}

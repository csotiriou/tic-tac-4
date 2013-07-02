package com.sai.gamerules;

import java.util.ArrayList;
import java.util.List;

import com.sai.exception.InvariantException;
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
 * Game Rule factory for instantiating {@link RulePlay} objects based on the type of string given as the argument.
 * In order to add more {@link RulePlay} subclasses, one must also change this factory to return the proper instances
 * of the classes based on the string input.
 * @author Christos Sotiriou
 *
 */
public class GameRuleFactory {
	
	public static List<RulePlay> rulePlaysForGameRulesForObject(GameRules gameRules) throws InvariantException{
		
		int rulesSize = gameRules.getRulesSize();
		List<RulePlay> rules = new ArrayList<RulePlay>();
		
		for (int i = 0; i < rulesSize; i++) {
			String currentRule = gameRules.getRuleAtIndex(i+1);
			RulePlay newRule = ruleForString(currentRule);
			if (newRule != null) {
				rules.add(ruleForString(currentRule));	
			}else{
				throw new InvariantException("Invalid initialized. Rule \"" + currentRule + "\" not recognized" );
			}
			
		}
		return rules;
	}
	
	public static RulePlay ruleForString(String string) throws InvariantException{
		if (string.equalsIgnoreCase("If you can win then win")) {
			return new RulePlay1(string);
		}else if (string.equalsIgnoreCase("If you can block a win then block a win")){
			return new RulePlay2(string);
		}else if (string.equalsIgnoreCase("If the center is free then play in the center")){
			return new RulePlay3(string);
		}else if (string.equalsIgnoreCase("Choose one of the remaining free positions randomly")){
			return new RulePlay4(string);
		}
		throw new InvariantException("Invalid initializer. Rule \"" + string + "\" not recognized");
	}
}

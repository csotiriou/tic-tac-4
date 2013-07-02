package com.sai.gamerules;

import com.sai.abs.InvariantInterface;
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
 * Abstract class that defines the default behavior for all {@link RulePlay}.
 * Objects derive from this class represent a distinct kind of thinking before making
 * a move inside the board. Subclasses should implement this behavior using the 
 * {@link RulePlayInterface}{@link #playRuleForGameState(coml.sai.game.BoardGame.GameState)} function.
 * 
 * @author Christos Sotiriou
 *
 */
public abstract class RulePlay implements RulePlayInterface, InvariantInterface{

	/**
	 * The string representation of the rule.
	 */
	String ruleString = null;
	
	
	public RulePlay(String ruleAsString){
		this.ruleString = ruleAsString;
	}	
	
	
	public String getRuleString() {
		return ruleString;
	}
	
	
	
	@Override
	public String getRule() {
		return getRuleString();
	}
	
	@Override
	public String toString() {
		return ruleString;
	}
	
	@Override
	public boolean invariant() {
		return (ruleString != null); 
	}
}

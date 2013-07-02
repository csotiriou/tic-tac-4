package com.sai.gamerules;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.sai.game.BoardGame;
import com.sai.util.PropertiesGetter;
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
 * {@link GameRules} implements a container of game rules used in a {@link BoardGame},
 * and facilities for retrieving rules.
 * @author Christos Sotiriou
 *
 */
public class GameRules {
	PropertiesGetter gameRulesGetter = new PropertiesGetter();
	
	public String allGameRulesOrderedAsString(){
		String result = "";
		for (int i = 1; i <= 4; i++) {
			result += i + ":" + gameRulesGetter.getProperty("rule" + i) + "\n";
		}
		return result;
	}
	
	
	public String getRuleAtIndex(int index){
		return gameRulesGetter.getProperty("rule" + index);
	}
	
	/**
	 * Loads a file containing rules from a location inside the hard disk
	 * @param location
	 * @throws FileNotFoundException
	 */
	public void loadRulesFromLocation(String location) throws FileNotFoundException{
		gameRulesGetter.loadPropertiesFromInputStream(new FileInputStream(location));
	}
	
	/**
	 * Loads game rules from a file stored locally ie. inside a package of the project.
	 * @param localFile
	 * @throws FileNotFoundException
	 */
	public void loadRulesFromLocalFile(String localFile) throws FileNotFoundException{
		gameRulesGetter.loadPropertiesFromLocalFileLocation(localFile);
	}
	
	public int getRulesSize(){
		return gameRulesGetter.getPropertiesSize();
	}
	
}

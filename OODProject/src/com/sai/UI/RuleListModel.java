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

package com.sai.UI;

import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import com.sai.gamerules.RulePlay;


/**
 * {@link AbstractListModel} implementation for to allow swapping of elements
 * that are displayed into a {@link JList}
 * @author Christos Sotiriou
 *
 */
public class RuleListModel extends AbstractListModel {



	List<RulePlay> rules;
	
	private static final long serialVersionUID = 4708196218745255280L;

	public RuleListModel(List<RulePlay> r){
		rules = r;
	}
	
	@Override
	public Object getElementAt(int index) {
		return rules.get(index);
	}

	@Override
	public int getSize() {
		return rules.size();
	}

	
	public void setRules(List<RulePlay> rules) {
		this.rules = rules;
	}
	
	@Override
	protected void fireContentsChanged(Object source, int index0, int index1) {
		super.fireContentsChanged(source, index0, index1);
		System.out.println("fire contents changed");
	}
	
	/**
	 * Swap the elements that are selected. Automatically calls
	 * {@link #fireContentsChanged(Object, int, int)} when it has
	 * finished, so the content view is automatically updated 
	 * @param first
	 * @param second
	 */
	public void swap(int first, int second){
		Collections.swap(rules, first, second);
		fireContentsChanged(this, first, second);
	}
	
	public List<RulePlay> getRules() {
		return rules;
	}
	
}

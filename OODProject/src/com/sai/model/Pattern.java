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

package com.sai.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sai.util.PointComparator;


/**
 * Class that holds a list of points that were found to create a known pattern,
 * as specified by a {@link PatternMatcher}. These {@link Point}s represent positions
 * in a {@link Board}.
 * 
 * Note that the points recovered are inverse. The {@link Point}.getX() function will return the
 * distance on the X axis, and the {@link Point}.getY function will return the distance on the Y axis. 
 * 
 * @author Christos Sotiriou
 *
 */
public class Pattern {
	int patternMatched;
	
	/**
	 * The {@link List} of points that form a pattern.
	 */
	List<Point> pointsMatchingPattern = new ArrayList<Point>();
	
	
	
	public void setPointsMatchingPattern(List<Point> pointsMatchingPattern) {
		this.pointsMatchingPattern = pointsMatchingPattern;
		Collections.sort(pointsMatchingPattern, new PointComparator());
	}
	
	public Pattern(){
		super();
	}
	
	public Pattern(Point ...points){
		this();
		setPointsMatchingPattern(Arrays.asList(points));
	}
	
	public Pattern(Point [] points, int patternMatched){
		this();
		setPointsMatchingPattern(Arrays.asList(points));
		this.patternMatched = patternMatched;
	}
	
	public List<Point> getPointsMatchingPattern() {
		return pointsMatchingPattern;
	}
	
	public int getPatternMatched() {
		return patternMatched;
	}
	
	public void setPatternMatched(int patternMatched) {
		this.patternMatched = patternMatched;
	}
	
	@Override
	public int hashCode() {
		return pointsMatchingPattern.hashCode() + patternMatched;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Pattern)) {
			return false;
		}
		Pattern otherPattern = (Pattern)obj;
	
		return (
				this.getPatternMatched() == otherPattern.getPatternMatched() &&
				this.getPointsMatchingPattern().equals(otherPattern.getPointsMatchingPattern())
				);
	}
	
	@Override
	public String toString() {
		String result = "Matching for: " + patternMatched + "\n";
		for (Point p : pointsMatchingPattern) {
			result += "(" + p.x + "," + p.y + ")";
		}
		return result;
	}
}

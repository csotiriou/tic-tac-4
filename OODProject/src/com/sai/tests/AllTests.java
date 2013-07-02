package com.sai.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestsBoardConnect4.class,
	TestsBoardTicTacToe.class, 
	TestsGameNoughtsAndCrosses.class,
	TestsGameConnect4.class,
	TestsPatternMatcher.class,
	TestRulesTicTacToe.class,
})
public class AllTests {

}

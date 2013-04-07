package com.barsky.chainreaction1;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Score {
	long gameScore = 0;
	int levelBalls = 0;
	static DecimalFormat df = new DecimalFormat();
	static DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	
	public Score() {
		gameScore = 0;
	}
	
	public void addToScore(long score) {
		gameScore += score;
		levelBalls += 1;
	}
	
	public static String formatScore(long i) {
		dfs.setGroupingSeparator(',');
		df.setDecimalFormatSymbols(dfs);
		return df.format(i);
	}
}

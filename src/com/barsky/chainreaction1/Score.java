package com.barsky.chainreaction1;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.swarmconnect.SwarmAchievement;

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
		achievements(gameScore);
	}
	
	public static String formatScore(long i) {
		dfs.setGroupingSeparator(',');
		df.setDecimalFormatSymbols(dfs);
		return df.format(i);
	}
	
	public boolean achievements(long score) {
		if (score >= 2147483647) { //2,147,483,647 (max) points
			SwarmAchievement.unlock(12313);
			return true;
		} else if (score >= 1000000000) { //1 Bil points
			SwarmAchievement.unlock(12311);
			return true;
		} else if (score >= 50000000) { //50 Mil points
			SwarmAchievement.unlock(12309);
			return true;
		} else if (score >= 15000000) { //15 Mil points
			SwarmAchievement.unlock(12307);
			return true;
		} else if (score >= 5000000) { //5 Mil points
			SwarmAchievement.unlock(12305);
			return true;
		} else if (score >= 1000000) { //1 Mil points
			SwarmAchievement.unlock(12303);
			return true;
		}
		return false;
	}
}

package com.barsky.chainreaction1;

public class Score {
	long gameScore;
	int levelBalls;
	
	public Score() {
		gameScore = 0;
	}
	
	public void addToScore(long score) {
		gameScore += score;
		levelBalls += 1;
	}
}

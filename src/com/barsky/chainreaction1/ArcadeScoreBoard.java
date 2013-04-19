package com.barsky.chainreaction1;

import android.graphics.Canvas;

public class ArcadeScoreBoard extends ScoreBoard {
	long time, timeLeft;
	static long waveTimeLeft = 0;
	float textHeight;
	boolean set = false;
	static long wave = 0;
	
	public ArcadeScoreBoard(long time) {
		this.time = time;
	}
	
	public String checkWaveTime(boolean touched) {
		String nextWave;
		
		if (touched || waveTimeLeft > 0) {
			
			if(!set || waveTimeLeft < 0) {
				wave = System.currentTimeMillis() + 8000;
				set = true;
			}
			waveTimeLeft = wave - System.currentTimeMillis();
			nextWave = "Next Wave In: " + waveTimeLeft/1000 + " seconds";
			
		} else {
			nextWave = "Next Wave In: N/A";
			set = false;
			wave = 0;
		}
		
		return nextWave;
	}
	
	public void drawScore(Canvas canvas, Score score, boolean touched) {
		timeLeft = this.time - System.currentTimeMillis();
		String timer = "Time Left: " + timeLeft/1000 + " seconds";
		canvas.drawText(timer, 10, 20, paint);
		
		paint.getTextBounds(timer, 0, timer.length(), bounds);
		textHeight = bounds.height();
		canvas.drawText(checkWaveTime(touched), 10, textHeight+22, paint);
		
		gameScore = formatScore(score.gameScore);
		gameScore = "Score: "+gameScore;
		paint.getTextBounds(gameScore, 0, gameScore.length(), bounds);
		textWidth = bounds.width() + 10;
		canvas.drawText(gameScore, MainActivity.main.xmax - textWidth, 20, paint);
		
		/*levelBalls = Integer.toString(score.levelBalls)+" balls expanded";
		paint.getTextBounds(levelBalls, 0, levelBalls.length(), bounds);
		textWidth = bounds.width() + 10;
		canvas.drawText(levelBalls, MainActivity.main.xmax - textWidth, 20, paint);*/
	}
}

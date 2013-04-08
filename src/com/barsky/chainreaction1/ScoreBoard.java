package com.barsky.chainreaction1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.barsky.chainreaction1.MainActivity;

public class ScoreBoard extends Score{
	Paint paint = new Paint();
	String gameScore;
	String levelBalls;
	Rect bounds = new Rect();
	float textWidth;
	
	public ScoreBoard() {
		init();
	}
	
	public void init() {
		paint = new Paint();
		paint.setStrokeWidth(10);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(MainActivity.main.xmax / 26);
		paint.setColor(Color.WHITE);
	}
	
	public void drawScore(Canvas canvas, Score score) {
		gameScore = formatScore(score.gameScore);
		gameScore = "Score: "+gameScore;
		canvas.drawText(gameScore, 10, 20, paint);
		
		canvas.drawText(Integer.toString(MyView.touchRadius), 10, 50, paint);
		canvas.drawText(Float.toString(Circle.littleRadius), 10, 70, paint);
		levelBalls = Integer.toString(score.levelBalls)+" balls expanded";
		paint.getTextBounds(levelBalls, 0, levelBalls.length(), bounds);
		textWidth = bounds.width() + 10;
		canvas.drawText(levelBalls, MainActivity.main.xmax - textWidth, 20, paint);
		
	}
}

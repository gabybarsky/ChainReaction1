package com.barsky.chainreaction1;

import java.text.DecimalFormat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.barsky.chainreaction1.MainActivity;

public class ScoreBoard {
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
		paint.setTextSize(20);
	}
	
	public void drawScore(Canvas canvas, Score score) {
		gameScore = "Score: "+new DecimalFormat("#").format(score.gameScore);
		paint.setColor(Color.WHITE);
		canvas.drawText(gameScore, 10, 20, paint);
		
		levelBalls = Integer.toString(score.levelBalls)+" balls expanded";
		paint.getTextBounds(levelBalls, 0, levelBalls.length(), bounds);
		textWidth = bounds.width() + 10;
		canvas.drawText(levelBalls, MainActivity.xmax - textWidth, 20, paint);
		
	}
}

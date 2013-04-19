package com.barsky.chainreaction1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ArcadeCircle extends Circle {
	Paint paint = ArcadeView.paint;
	
	public ArcadeCircle() {
		//all is needed is from circle
	}
	
	@Override
	public void drawCircle(Canvas canvas) {
		Update();
		paint.setColor(color);
		canvas.drawCircle(xPos, yPos, r, paint);
		if(cleared) {
			paint.setColor(Color.WHITE);
			if(score > 0) {
				drawText(canvas);
			}
		}
	}
	
	@Override
	public void drawText(Canvas canvas) {
		String scoreStr = "+"+Score.formatScore(score);
		paint.getTextBounds(scoreStr, 0, scoreStr.length(), bounds);
		textWidth = bounds.width();
		textHeight = bounds.height();
		xText = xPos - (textWidth/2);
		yText = yPos + (textHeight/2);
		canvas.drawText(scoreStr, xText, yText, paint);
	}
	
	@Override
	public void score(Circle circle) {
		if(!circle.cleared) {
			circle.cleared = true;
			circle.time = System.currentTimeMillis();
			circle.score = score +1;
			//achievements(circle.score);
			totalScore.addToScore(circle.score);
		}
	}
}

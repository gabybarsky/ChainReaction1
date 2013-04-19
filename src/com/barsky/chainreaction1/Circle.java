package com.barsky.chainreaction1;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.barsky.chainreaction1.MainActivity;
import com.swarmconnect.SwarmAchievement;

public class Circle {
	Random rnd = new Random();
	public float xPos, xText;
	public float yPos, yText;
	public float r;
	public float xVelocity, yVelocity;
	long score, multiplier;
	public int color;
	public long time, elapsedTime;
	public long allowedTime = 2000; // 2sec
	public boolean cleared;
	boolean collision;
	public static float littleRadius = MainActivity.main.xmax / 50;
	public int xPlusMinus = rnd.nextInt(2);
	public int yPlusMinus = rnd.nextInt(2);
	public int textWidth, textHeight;
	public static Score totalScore = new Score();
	Paint paint = MyView.paint;
	Rect bounds = new Rect();
	
	public Circle() {
		this.xPos = rnd.nextInt(MainActivity.main.xmax - 15);
		this.yPos = rnd.nextInt((int) (MainActivity.main.ymax - 15));
		this.xVelocity = rnd.nextFloat()*6;
		this.yVelocity = rnd.nextFloat()*6;
		this.r = littleRadius;
		this.score = 0;
		this.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
		this.cleared = false;
	
		while (xPos-r <= 0 || yPos-r <= 0) {
			this.xPos = rnd.nextInt(MainActivity.main.xmax-15);
			this.yPos = rnd.nextInt(MainActivity.main.ymax-15);
		}
		
		while (xVelocity <= 0.6 || yVelocity <= 0.6) {
			this.xVelocity = rnd.nextFloat()*6;
			this.yVelocity = rnd.nextFloat()*6;
		}
		if (xPlusMinus==1) { xVelocity = -xVelocity; }
		if (yPlusMinus==1) { yVelocity = -yVelocity; }
	}
	
	public void Move() {
		xPos = xPos + xVelocity;
		yPos = yPos + yVelocity;
		
		//check collision walls
		if(xPos >= MainActivity.main.xmax - r || xPos <= 0 + r) {
			xVelocity = -xVelocity;
		}
		if(yPos >= MainActivity.main.ymax - r || yPos <= 0 + r) {
			yVelocity = -yVelocity;
		}
	}
	
	public void Update() {
		Move();
	}
	
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
	
	public void drawText(Canvas canvas) {
		String scoreStr = "+"+Score.formatScore(score);
		paint.getTextBounds(scoreStr, 0, scoreStr.length(), bounds);
		textWidth = bounds.width();
		textHeight = bounds.height();
		xText = xPos - (textWidth/2);
		yText = yPos + (textHeight/2);
		canvas.drawText(scoreStr, xText, yText, paint);
	}

	public boolean drawableCircle() {
		elapsedTime = System.currentTimeMillis() - time;
		if(elapsedTime >= allowedTime && r > littleRadius) {
			return false;
		}
		return true;
	}
	
	public boolean achievements(long score) {
		if (score >= 10000000) { //10,000,000 on a ball
			SwarmAchievement.unlock(12321);
			return true;
		} else if (score >= 1000000) { //1,000,000 on a ball
			SwarmAchievement.unlock(12319);
			return true;
		} else if (score >= 100000) { //100,000 on a ball
			SwarmAchievement.unlock(12317);
			return true;
		}
		return false;
	}
	
	//check collision with little circles
	public void collision(Circle circle) {
		if (r > littleRadius) {
			double xDif = circle.xPos - this.xPos;
			double yDif = circle.yPos - this.yPos;
			double dist = xDif*xDif + yDif*yDif;
			collision = dist < (this.r+circle.r) * (this.r+circle.r);
			
			if (collision) {
				circle.xVelocity = 0;
				circle.yVelocity = 0;
				circle.r = r;
				this.score(circle);
			}
		}
	}
	
	public void score(Circle circle) {
		if(!circle.cleared) {
			MyView.clear += 1;
			circle.cleared = true;
			circle.time = System.currentTimeMillis();
			multiplier = score/10;
			if(multiplier > 0) {
				circle.score = score + 10 * multiplier;
			} else {
				circle.score = score + 10;
			}
			achievements(circle.score);
			totalScore.addToScore(circle.score);
		}
	}
}

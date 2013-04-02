package com.barsky.chainreaction1;

import java.text.DecimalFormat;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;

import com.barsky.chainreaction1.MainActivity;

public class Circle {
	Random rnd = new Random();
	public float xPos;
	public float yPos;
	public float r;
	public float xVelocity, yVelocity;
	long score, multiplier;
	public int color;
	public long time, elapsedTime;
	public long allowedTime = 2000; // 2sec
	public boolean cleared;
	boolean collision;
	public static float littleRadius = 10;
	public int xPlusMinus = rnd.nextInt(2);
	public int yPlusMinus = rnd.nextInt(2);
	public static Score totalScore = new Score();
	
	public Circle() {
		this.xPos = rnd.nextInt(MainActivity.xmax - 15);
		this.yPos = rnd.nextInt(MainActivity.ymax - 15);
		this.xVelocity = rnd.nextFloat()*6;
		this.yVelocity = rnd.nextFloat()*6;
		this.r = 10;
		this.score = 0;
		this.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
		this.cleared = false;
	
		while (xPos-r <= 0 || yPos-r <= 0) {
			this.xPos = rnd.nextInt(MainActivity.xmax-15);
			this.yPos = rnd.nextInt(MainActivity.ymax-15);
		}
		if (xPlusMinus==1) { xVelocity = -xVelocity; }
		if (yPlusMinus==1) { yVelocity = -yVelocity; }
	}
	
	public void Move() {
		xPos = xPos + xVelocity;
		yPos = yPos + yVelocity;
		
		//check collision walls
		if(xPos >= MainActivity.xmax - r || xPos <= 0 + r) {
			xVelocity = -xVelocity;
		}
		if(yPos >= MainActivity.ymax - r || yPos <= 0 + r) {
			yVelocity = -yVelocity;
		}
	}
	
	public void Update() {
		Move();
	}
	
	public void drawCircle(Canvas canvas) {
		Update();
		MyView.paint.setColor(color);
		canvas.drawCircle(xPos, yPos, r, MyView.paint);
		if(cleared) {
			MyView.paint.setColor(Color.BLACK);
			String scoreStr = new DecimalFormat("#").format(score);
			canvas.drawText("+"+scoreStr, xPos, yPos, MyView.paint);
		}
	}

	public boolean drawableCircle() {
		elapsedTime = System.currentTimeMillis() - time;
		if(elapsedTime >= allowedTime && r > littleRadius) {
			return false;
		}
		return true;
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
				if(!circle.cleared) {
					MyView.clear += 1;
					circle.cleared = true;
					circle.time = System.currentTimeMillis();
					multiplier = score/100;
					if(multiplier > 0) {
						circle.score = score + 100 * multiplier;
					} else {
						circle.score = score + 100;
					}
					totalScore.addToScore(score);
				}
			}
		}
	}
}

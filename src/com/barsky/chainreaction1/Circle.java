package com.barsky.chainreaction1;

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
	public int color;
	
	public Circle() {
		this.xPos = rnd.nextInt(MainActivity.xmax - 15);
		this.yPos = rnd.nextInt(MainActivity.ymax - 15);
		this.xVelocity = rnd.nextFloat()*6;
		this.yVelocity = rnd.nextFloat()*6;
		this.r = 10;
		this.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
	
		while (xPos-r <= 0 || yPos-r <= 0) {
			this.xPos = rnd.nextInt(MainActivity.xmax-15);
			this.yPos = rnd.nextInt(MainActivity.ymax-15);
		}
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
	}
	
	
}

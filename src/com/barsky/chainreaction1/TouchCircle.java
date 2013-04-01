package com.barsky.chainreaction1;

import android.graphics.Color;

public class TouchCircle extends Circle {
	boolean collision;
	
	public TouchCircle(float xPos, float yPos) {
		this.xVelocity = 0;
		this.yVelocity = 0;
		this.r = 60;
		this.color = Color.GRAY;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	//check collision with little circles
	public void collision(Circle circle) {
		double xDif = circle.xPos - xPos;
		double yDif = yPos - circle.yPos;
		double dist = xDif*xDif + yDif*yDif;
		collision = dist < (r*circle.r) + (r*circle.r);
		
		if (collision) {
			circle.xVelocity = 0;
			circle.yVelocity = 0;
			circle.r = r;
		}
	}
}

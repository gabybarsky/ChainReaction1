package com.barsky.chainreaction1;

import android.graphics.Color;

public class TouchCircle extends Circle {

	public TouchCircle(float xPos, float yPos) {
		this.xVelocity = 0;
		this.yVelocity = 0;
		this.r = MyView.touchRadius;
		this.color = Color.GRAY;
		this.xPos = xPos;
		this.yPos = yPos;
		this.cleared = true;
	}
}

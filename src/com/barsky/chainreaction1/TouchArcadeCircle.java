package com.barsky.chainreaction1;

import android.graphics.Color;

public class TouchArcadeCircle extends ArcadeCircle {

	public TouchArcadeCircle(float xPos, float yPos) {
		this.xVelocity = 0;
		this.yVelocity = 0;
		this.r = ArcadeView.touchRadius;
		this.color = Color.GRAY;
		this.xPos = xPos;
		this.yPos = yPos;
		this.cleared = true;
		this.time = System.currentTimeMillis();
	}
}

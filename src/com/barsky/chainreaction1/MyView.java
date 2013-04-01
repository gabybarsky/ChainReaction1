package com.barsky.chainreaction1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView  extends View {
	public static Paint paint;
	Path path;
	Random rnd = new Random();
	Circle circle1;
	Circle circle2;
	Circle circle3;
	TouchCircle touchCircle;
	int level = 1;
	int clearLevel;
	int num = 6;
	int clear = 0;
	boolean startLevel = true;
	boolean touched = false;
	ArrayList<Circle> circles = new ArrayList<Circle>();
	Iterator<Circle> iterator1, iterator2;
	Circle prevCircle, newCircle;
	
	
	public MyView(Context context) {
		super(context);
		init();
	}
	
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		paint = new Paint();
		paint.setStrokeWidth(10);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(20);
	}
	
	public void createLevel() {
		
		if(startLevel)
		{
			for (int i=0; i<=num; i++) {
				circles.add(new Circle());
			}
			startLevel = false;
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(!touched) {
			if(event.getAction() == MotionEvent.ACTION_DOWN) {
				touchCircle = new TouchCircle(event.getX(), event.getY());
				touched = true;
			}
			invalidate();
		}
		return true;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		createLevel();

		iterator1 = circles.iterator();
		while(iterator1.hasNext()) {
			iterator1.next().drawCircle(canvas);
		}
		
		if(touched) {
			touchCircle.drawCircle(canvas);
			iterator1 = circles.iterator();
			while(iterator1.hasNext()) {
				prevCircle = iterator1.next();
				touchCircle.collision(prevCircle);
				iterator2 = circles.iterator();
				while(iterator2.hasNext()) {
					newCircle = iterator2.next();
					if(prevCircle != newCircle) {
						try {
							newCircle.collision(prevCircle);
						} catch(IllegalArgumentException e) {
							continue;
						} catch(NoSuchElementException e) {
							continue;
						}
					}
				}
			}
		}
		
		invalidate();
	}
}

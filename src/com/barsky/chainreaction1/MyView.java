package com.barsky.chainreaction1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
	TouchCircle touchCircle;
	public int level = 0;
	public int levelText = 1;
	public int[] num = {5,10,15,20,25,30,35,40,45,50,55,60};
	public int[] clearLevel = {1,2,4,6,10,15,18,22,30,37,48,55};
	public static int clear = 0;
	public static int touchRadius = 80;
	boolean startLevel = true;
	boolean touched, popup = false;
	ArrayList<Circle> circles = new ArrayList<Circle>();
	Iterator<Circle> iterator1, iterator2;
	Circle prevCircle, newCircle;
	AlertDialog levelPop = new AlertDialog.Builder(getContext()).create();
	String message;
	
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
	
	public void popup() {
		levelPop.setCanceledOnTouchOutside(false);
		levelPop.setCancelable(false);
		levelPop.setButton(-1, "Play", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				circles.clear();
				for (int i=0; i<=num[level]; i++) {
					circles.add(new Circle());
				}
			}
		});
		popup =  true;
	}
	public void level() {
		
		if(startLevel)
		{
			message = "Get "+clearLevel[level]+" out of "+num[level]+" balls!";
			try {
				if (!popup) { popup(); }
				levelPop.setMessage(message);
				levelPop.setTitle("Level "+levelText);
				levelPop.show();
			} catch(NullPointerException e) {
			}
			startLevel = false;
		} else {
			if(clear >= clearLevel[level]) {
				touched = false;
				startLevel = true;
				clear = 0;
				levelText += 1;
				if(level >= num.length - 1) {
					level = 0;
					touchRadius -= 10;
				} else {
					level += 1;
				}
			}
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
		level();

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

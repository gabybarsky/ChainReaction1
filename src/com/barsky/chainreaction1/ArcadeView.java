package com.barsky.chainreaction1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmLeaderboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ArcadeView extends View {
	public static Paint paint;
	long time; 
	long waveTimer;
	static int touchRadius;
	AlertDialog levelPop = new AlertDialog.Builder(getContext()).create();
	AlertDialog losePop = new AlertDialog.Builder(getContext()).create();
	ArrayList<ArcadeCircle> remove = new ArrayList<ArcadeCircle>();
	ArrayList<ArcadeCircle> circles = new ArrayList<ArcadeCircle>();
	Iterator<ArcadeCircle> iterator1, iterator2;
	ArcadeCircle prevCircle, newCircle;
	boolean popup, added, touched = false;
	TouchArcadeCircle touchCircle;
	int[] wave = {30, 25, 20, 15, 10, 5, 5, 10, 15, 20, 25, 30};
	int waveNum;
	boolean startWave = true;
	ArcadeScoreBoard scoreBoard;
	Intent arcadeHSIntent = new Intent(getContext(), HighScoreActivity.class);
	boolean submitted = false;
	boolean continuous = true;
	
	public ArcadeView(Context context) {
		super(context);
		init();
		newGame();
	}
	
	public ArcadeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		newGame();
	}
	
	public ArcadeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		newGame();
	}
	
	private void init() {
		paint = new Paint();
		paint.setStrokeWidth(10);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(MainActivity.main.xmax / 26);
	}
	
	public void newGame() {
		touchRadius = (MainActivity.main.xmax / 26)*3;
		added = false;
		touched = false;
		startWave = true;
		ArcadeCircle.totalScore.gameScore = 0;
		waveNum = 0;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(!touched) {
			if(event.getAction() == MotionEvent.ACTION_DOWN) {
				touchCircle = new TouchArcadeCircle(event.getX(), event.getY());
				touched = true;
			}
			invalidate();
			return true;
		}
		return false;
	}
	
	public void drawCircles(Canvas canvas) {
		iterator1 = circles.iterator();
		while(iterator1.hasNext()) {
			newCircle = iterator1.next();
			newCircle.drawCircle(canvas);
			circleDrawable(newCircle);
		}
		removeCircles();
		
		if(touched && !added) {
			circles.add(touchCircle);
			added = true;
		}
	}
	
	public void circleDrawable(ArcadeCircle circle) {
		if(!circle.drawableCircle()) {
			remove.add(circle);
		}
	}
	
	public void removeCircles() {
		iterator1 = remove.iterator();
		while(iterator1.hasNext()) {
			circles.remove(iterator1.next());
		}
		remove.clear();
	}
	
	public void checkCollision(Canvas canvas) {
		if(touched) {
			iterator1 = circles.iterator();
			while(iterator1.hasNext()) {
				prevCircle = iterator1.next();
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
	}
	
	public void setWave() {
		for (int i=0; i<wave[waveNum]; i++) {
			circles.add(new ArcadeCircle());
		}
	}
	
	public boolean hasLost() {
		if(touched) {
			iterator1 = circles.iterator();
			while (iterator1.hasNext()) {
				if(iterator1.next().r > ArcadeCircle.littleRadius) {
					return false;
				}
			} return true;
		} return false;
	}
	
	public void newWave(Canvas canvas) {
		if(ArcadeScoreBoard.waveTimeLeft <= 0 && ArcadeScoreBoard.wave > 0) {
			waveNum +=1;
			setWave();
		}
		if(hasLost()) {
			touched = false;
			added = false;
			continuous = false;
		}
	}
	
	public void popup() {
		levelPop.setCanceledOnTouchOutside(false);
		levelPop.setCancelable(false);
		levelPop.setButton(-1, "Play", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				time = System.currentTimeMillis() + 30000;
				scoreBoard = new ArcadeScoreBoard(time);
				setWave();
			}
		});
		popup = true;
	}
	
	public void start() {
		if(startWave == true) {
			String message = "Get as many balls as possible!";
			try {
				if (!popup) { popup(); }
				levelPop.setMessage(message);
				levelPop.setTitle("Arcade Mode");
				levelPop.show();
			} catch(NullPointerException e) {
				//Do nothing
			}
			startWave = false;
		}
	}
	
	public void drawBoard(Canvas canvas) {
		try{
			scoreBoard.drawScore(canvas, ArcadeCircle.totalScore, touched);
		}catch(NullPointerException e){}
	}
	
	public void losePopup() {
		long score = ArcadeCircle.totalScore.gameScore;
		losePop.setCanceledOnTouchOutside(false);
		losePop.setCancelable(false);
		losePop.setButton(-1, "OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				newGame();
			}
		});
		losePop.setButton(-1, "HighScores", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				arcadeHSIntent.putExtra("score", ArcadeCircle.totalScore.gameScore);
				arcadeHSIntent.putExtra("type", 1); //arcade mode
				getContext().startActivity(arcadeHSIntent);
			}
			
		});
		losePop.setTitle("Too bad, You Lose!");
		losePop.setMessage("Play Again?\nYour Score: "+Score.formatScore(score));
		losePop.show();
	}
	
	public boolean gameOver() {
		try {
			if(time == 0) {
				return false;
			} else if(time < System.currentTimeMillis()) {
				return true;
			}
		} catch(NullPointerException e) { 
			return false; 
		}
		return false;
	}
	
	public boolean achievements() {
		if(continuous && waveNum >= 2) {
			SwarmAchievement.unlock(12849);
		}
		if(ArcadeCircle.totalScore.gameScore >= 9000) {
			SwarmAchievement.unlock(12853);
		} else if(ArcadeCircle.totalScore.gameScore >= 1000) {
			SwarmAchievement.unlock(12851);
		}
		return false;
	}
	@Override
	public void onDraw(Canvas canvas) {
		start();
		drawCircles(canvas);
		checkCollision(canvas);
		drawBoard(canvas);
		newWave(canvas);
		achievements();
		
		if(gameOver()) {
			touched = false;
			if(!submitted) {
				SwarmLeaderboard.submitScore(8593, ArcadeCircle.totalScore.gameScore);
				submitted = true;
			}
			
			losePopup();
		}
		
		invalidate();
	}
}

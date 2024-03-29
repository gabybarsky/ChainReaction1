package com.barsky.chainreaction1;

import com.swarmconnect.SwarmActivity;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.Window;

public class MainActivity extends SwarmActivity {

	public int xmax, ymax;
	static MainActivity main;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		finishHS();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		Intent menuIntent = getIntent();
		int type = menuIntent.getIntExtra("type", 0);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point point = getDisplaySize(display);
		xmax = point.x;
		ymax = point.y;
		main = this;
		
		if (type == 0) {
			onBackPressed();
		} else if(type == 1) {
			setContentView(new MyView(this));
		} else if (type == 2) {
			setContentView(new ArcadeView(this));
		}
	}
	
	public boolean finishHS() {
		try {
			HighScoreActivity.hsAct.finish();
			return true;
		} catch (NullPointerException e) {return false;}
	}
	
	@SuppressWarnings("deprecation")
	private static Point getDisplaySize(final Display display) {
		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (java.lang.NoSuchMethodError ignore) {
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		return point;
	}
	
	@Override
	public void onBackPressed() {
		Intent menuIntent = new Intent(this, MenuActivity.class);
		startActivity(menuIntent);
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Handle item selection
		switch(item.getItemId()) {
			case R.id.main_menu:
				Intent intent = new Intent(this, MenuActivity.class);
				startActivity(intent);
				this.finish();
				return true;
			case R.id.new_game:
				MyView.newGame();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}*/

}

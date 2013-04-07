package com.barsky.chainreaction1;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;

public class MenuActivity extends Activity {
	AlertDialog highscore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		finishHS();
		finishGame();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		highscore = new AlertDialog.Builder(this).create();
		
		final Intent playGame = new Intent(this, MainActivity.class);
		final Intent hsIntent = new Intent(this, HighScoreActivity.class);
		
		final Button playButton = (Button) findViewById(R.id.play_menu);
		playButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(playGame);
				MyView.newGame();
				finish();
				return;
			}
		});
		
		final Button highScoreButton = (Button) findViewById(R.id.highscore_menu);
		highScoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//start high score page
				startActivity(hsIntent);
				finish();
				return;
				/*highscore.setButton(-1, "OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				highscore.setTitle("High Scores Coming Soon");
				highscore.show();*/
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	public boolean finishHS() {
		try {
			HighScoreActivity.hsAct.finish();
			return true;
		} catch (NullPointerException e) {return false;}
	}
	
	public boolean finishGame() {
		try {
			MainActivity.main.finish();
			return true;
		} catch (NullPointerException e) {return false;}
	}

}

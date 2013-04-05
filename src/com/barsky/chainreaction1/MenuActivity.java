package com.barsky.chainreaction1;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.DialogInterface;
import android.content.Intent;

public class MenuActivity extends Activity {
	AlertDialog highscore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		highscore = new AlertDialog.Builder(this).create();
		
		final Intent playGame = new Intent(this, MainActivity.class);
		final Button playButton = (Button) findViewById(R.id.play_menu);
		playButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(playGame);
				finish();
			}
		});
		
		final Button highScoreButton = (Button) findViewById(R.id.highscore_menu);
		highScoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//start highscore page
				highscore.setButton(-1, "OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// do nothing
					}
				});
				highscore.setTitle("High Scores Coming Soon");
				highscore.show();
			}
		});
		
		final Button quitButton = (Button) findViewById(R.id.quit_menu);
		quitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();			
			}
		});
	}

}

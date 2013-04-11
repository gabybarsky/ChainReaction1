package com.barsky.chainreaction1;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;
import com.swarmconnect.SwarmLeaderboard;

import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;

public class MenuActivity extends SwarmActivity {
	AlertDialog highscore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		finishHS();
		finishGame();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		Swarm.init(this, 5463, "82fa3ee1a45d51300bdb5ed15a09d90f");
		highscore = new AlertDialog.Builder(this).create();
		
		final Intent playGame = new Intent(this, MainActivity.class);
		final Intent hsIntent = new Intent(this, HighScoreActivity.class);
		
		final Button playButton = (Button) findViewById(R.id.play_menu);
		playButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(playGame);
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
			}
		});
		
		final Button leaderBoardButton = (Button) findViewById(R.id.leader_button);
		leaderBoardButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SwarmLeaderboard.showLeaderboard(8357);
			}
			
		});
		
		final Button achieveButton = (Button) findViewById(R.id.achieve_button);
		achieveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Swarm.showAchievements();
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

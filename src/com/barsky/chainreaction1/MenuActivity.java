package com.barsky.chainreaction1;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;
import com.swarmconnect.SwarmLeaderboard;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class MenuActivity extends SwarmActivity {
	AlertDialog highScoreDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		highScoreDialog = new AlertDialog.Builder(this).create();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		finishHS();
		finishGame();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		Swarm.setAllowGuests(true);
		Swarm.init(this, 5463, "82fa3ee1a45d51300bdb5ed15a09d90f");
		
		final Intent playGame = new Intent(this, MainActivity.class);
		//final Intent arcadeGame = new Intent(this, ArcadeActivity.class);
		final Intent hsIntent = new Intent(this, HighScoreActivity.class);
		
		final Button playButton = (Button) findViewById(R.id.play_menu);
		playButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				playGame.putExtra("type", 1);
				startActivity(playGame);
				finish();
				return;
			}
		});
		
		final Button arcadeButton = (Button) findViewById(R.id.arcadeButton);
		arcadeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playGame.putExtra("type", 2);
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
				popup();
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
	
	public void popup() {
		highScoreDialog.setTitle("Which LeaderBoard to View?");
		highScoreDialog.setButton(-2, "Classic Mode", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//show Classic LeaderBoard
				SwarmLeaderboard.showLeaderboard(8357);
			}
		});
		highScoreDialog.setButton(-1, "Arcade Mode", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//show Arcade LeaderBoard
				SwarmLeaderboard.showLeaderboard(8593);
			}
		});
		highScoreDialog.show();
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

package com.barsky.chainreaction1;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HighScoreActivity extends Activity {
	static AlertDialog alertName;
	public static String response;
	public static String message;
	public static HighScore highscore;
	static EditText input;
	boolean scoreAdded = false;
	static long score;
	TextView textArea;
	String highscores = "";
	static HighScoreActivity hsAct;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		finishGame();
		super.onCreate(savedInstanceState);
		hsAct = this;
		setContentView(R.layout.activity_high_score);
		alertName  = new AlertDialog.Builder(this).create();
		highscore = new HighScore(this);
		
		if(highscore.inHighscore(score) && scoreAdded == false) {
			onGameOver();
			scoreAdded = true;
		}
		
		printHS(textArea);
		
	}

	public boolean finishGame() {
		try {
			MainActivity.main.finish();
			Intent intent = getIntent();
			score = intent.getExtras().getLong("score");
			return true;
		} catch (NullPointerException e) {return false;}
	}
	
	public void onGameOver() {
		message = "Congratulations! You have set a new highscore!";
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		highscore.addScore(score);
		score = 0;
		
	}

	@Override
	public void onBackPressed() {
		Intent menuIntent = new Intent(this, MenuActivity.class);
		startActivity(menuIntent);
	}
	
	public void printHS(TextView textArea) {
		textArea = (TextView) findViewById(R.id.textArea);
		for (int i=0;i<10;i++) {
			int num = i+1;
			String scoreStr = Score.formatScore(highscore.getScore(i));
			highscores = highscores + num + ". " + scoreStr + "\n";
		}
		textArea.setText(highscores);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_score, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.clear:
				message = "All High Scores Cleared";
				Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
				highscore.clearHighScores();
				highscores = "";
				this.printHS(textArea);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}

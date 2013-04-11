package com.barsky.chainreaction1;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;

import android.os.Bundle;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HighScoreActivity extends SwarmActivity {
	public static String response;
	public static String message;
	public static HighScore highscore;
	static EditText input;
	boolean scoreAdded = false;
	static long score;
	long pastScore;
	TextView textArea;
	String highscores = "";
	static HighScoreActivity hsAct;
	String scoreStr;
	boolean clear = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		highscore = new HighScore(this);
		finishGame();
		super.onCreate(savedInstanceState);
		hsAct = this;
		setContentView(R.layout.activity_high_score);
		Swarm.init(this, 5463, "82fa3ee1a45d51300bdb5ed15a09d90f");
		
		if(highscore.inHighscore(score) && scoreAdded == false) {
			onGameOver();
			scoreAdded = true;
		}
		
		textArea = (TextView) findViewById(R.id.textArea);
		printHS(clear);
		
	}

	public boolean finishGame() {
		try {
			MainActivity.main.finish();
			Intent intent = getIntent();
			score = intent.getExtras().getLong("score");
			return true;
		} catch (NullPointerException e) {
			return false;
			}
	}
	
	public void onGameOver() {
		message = "Congratulations! You have set a new highscore of " + Score.formatScore(score) + "!";
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		highscore.addScore(score);
		pastScore = score;
		score = 0;
		
	}

	@Override
	public void onBackPressed() {
		Intent menuIntent = new Intent(this, MenuActivity.class);
		startActivity(menuIntent);
	}
	
	public void printHS(boolean clr) {
		if(clr == true) {
			textArea.setText("");
			highscore.clearHighScores();
			highscores = "";
		}
		
		for (int i=0;i<10;i++) {
			int num = i+1;
			
			if (highscore.getScore(i) == 0 || clr == true) {
				scoreStr = "---";
			} else {
				scoreStr = Score.formatScore(highscore.getScore(i));
			}
			
			if (highscore.getScore(i) == pastScore && highscore.getScore(i) != 0) {
				highscores = "<font color=#FF0000>"+ num + ". " + scoreStr + "</font>"; //make it red
				textArea.append(Html.fromHtml(highscores));
				textArea.append("\n");
			} else {
				highscores = num + ". " + scoreStr + "\n";
				textArea.append(highscores);
			}
		}
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
				clear = true;
				printHS(clear);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}

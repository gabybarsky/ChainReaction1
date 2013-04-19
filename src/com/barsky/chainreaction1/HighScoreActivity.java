package com.barsky.chainreaction1;

import com.swarmconnect.SwarmActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HighScoreActivity extends SwarmActivity {
	public static String response;
	public static String message;
	public static HighScore highscore;
	public HighScore arcadeHighscore;
	static EditText input;
	boolean scoreAdded, arcadeAdded = false;
	static long score;
	long pastScore, pastArcade;
	TextView textArea;
	String highscores = "";
	String arcadeHighscores = "";
	static HighScoreActivity hsAct;
	String scoreStr, arcadeStr;
	boolean[] clear = {false,false};
	boolean showClassic = true;
	boolean showArcade = false;
	int type;
	
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		highscore = new HighScore(this, "Highscore");
		arcadeHighscore = new HighScore(this, "ArcadeHS");
		finishGame();
		super.onCreate(savedInstanceState);
		hsAct = this;
		setContentView(R.layout.activity_high_score);
		
		textArea = (TextView) findViewById(R.id.textArea);
		switch(type) {
			case 0: classic();
			case 1: arcade();
			default: classic();
		}
		
	}
	
	public void classic() {
		if(highscore.inHighscore(score) && scoreAdded == false) {
			onCGameOver();
			scoreAdded = true;
		}
		
		showClassic = true;
		showArcade = false;
		printClassicHS(clear[type]);
	}
	
	public void arcade() {
		if(arcadeHighscore.inHighscore(score) && arcadeAdded == false) {
			onAGameOver();
			arcadeAdded = true;
		}
		
		showArcade = true;
		showClassic = false;
		printArcadeHS(clear[type]);
	}

	public boolean finishGame() {
		try {
			MainActivity.main.finish();
			Intent intent = getIntent();
			score = intent.getExtras().getLong("score");
			type = intent.getExtras().getInt("type");
			return true;
		} catch (NullPointerException e) {
			return false;
			}
	}

	public void onCGameOver() {
		message = "Congratulations! You have set a new highscore of " + Score.formatScore(score) + "!";
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		highscore.addScore(score);
		pastScore = score;
		score = 0;
	}
	
	public void onAGameOver() {
		message = "Congratulations! You have set a new highscore of " + Score.formatScore(score) + "!";
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		arcadeHighscore.addScore(score);
		pastArcade = score;
		score = 0;	
	}
	
	@Override
	public void onBackPressed() {
		Intent menuIntent = new Intent(this, MenuActivity.class);
		startActivity(menuIntent);
	}
	
	public void printClassicHS(boolean clr) {
		if(showClassic) {
			if(clr == true) {
				highscore.clearHighScores();
				highscores = "";
			}
		
			textArea.setText("");
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
				highscores = "";
			}
		}
	}
	
	public void printArcadeHS(boolean clr) {
		boolean noTie = false;
		if(showArcade) {
			if(clr == true) {
				arcadeHighscore.clearHighScores();
				arcadeHighscores = "";
			}
		
			textArea.setText("");
			for(int i=0; i<10; i++) {
				int num = i+1;
				if (arcadeHighscore.getScore(i) == 0 || clr == true) {
					arcadeStr = "---";
				} else {
					arcadeStr = Score.formatScore(arcadeHighscore.getScore(i));
				}
			
				if (arcadeHighscore.getScore(i) == pastArcade && arcadeHighscore.getScore(i) != 0 && !noTie) {
					arcadeHighscores = "<font color=#FF0000>"+ num + ". " + arcadeStr + "</font>";
					textArea.append(Html.fromHtml(arcadeHighscores));
					textArea.append("\n");
					noTie = true;
				} else {
					arcadeHighscores = num + ". " + arcadeStr + "\n";
					textArea.append(arcadeHighscores);
				}
				arcadeHighscores = "";
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
				clear[type] = true;
				if(type==0) {printClassicHS(clear[type]);}
				else if(type == 1) {printArcadeHS(clear[type]);}
				return true;
			case R.id.classicHS:
				type = 0;
				classic();
				return true;
			case R.id.arcadeHS:
				type = 1;
				arcade();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}

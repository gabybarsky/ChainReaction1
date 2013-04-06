package com.barsky.chainreaction1;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

public class HighScoreActivity extends Activity {
	static AlertDialog alertName;
	public static String response;
	public static String message;
	public static HighScore highscore;
	static EditText input;
	static long score = Circle.totalScore.gameScore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score);
		alertName  = new AlertDialog.Builder(this).create();
		highscore = new HighScore(this);
		
		if(highscore.inHighscore(score)) {
			onGameOver(this);
		}
	}

	public static void onGameOver(final Context context) {
		alertName.setCancelable(false);
		alertName.setCanceledOnTouchOutside(false);
		alertName.setTitle("Enter Your Name");
		input = new EditText(context);
		alertName.setView(input);
		//alertName.findViewById(R.id.getUserName);
		alertName.setButton(-1, "Submit", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				response = input.getText().toString();
				message = "Congratulations "+response+"! You have set a new highscore!";
				Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
				highscore.addScore(response, score);
			}
		});
		alertName.show();
	}

}

package com.barsky.chainreaction1;

import com.swarmconnect.SwarmActivity;

import android.os.Bundle;
import android.view.Menu;

public class ArcadeActivity extends SwarmActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arcade);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.arcade, menu);
		return true;
	}

}

package com.barsky.chainreaction1;

import com.swarmconnect.SwarmLeaderboard;

import android.content.Context;
import android.content.SharedPreferences;

public class HighScore {
	private SharedPreferences preferences;
    //private String names[];
    private long score[];

    public HighScore(Context context)
    {
            preferences = context.getSharedPreferences("Highscore", 0);
            //names = new String[10];
            score = new long[10];

            for (int x=0; x<10; x++)
            {
                    //names[x] = preferences.getString("name"+x, "-");
                    score[x] = preferences.getLong("score"+x, 0);
            }

    }

    /* public String getName(int x)
    {
            //get the name of the x-th position in the Highscore-List
            return names[x];
    }*/

    public long getScore(int x)
    {
            //get the score of the x-th position in the Highscore-List
            return score[x];
    }

    public boolean inHighscore(long score)
    {
            //test, if the score is in the Highscore-List
            int position;
            for (position=0; position<10 && this.score[position]>score; position++);

            if (position==10) return false;
            if (this.score[9] == 0 && score == 0) return false;
            return true;
    }

    public boolean addScore(/*String name,*/ long score)
    {
            //add the score with the name to the Highscore-List
            int position;
            for (position=0; position<10&&this.score[position]>score; position++);

            if (position==10) return false;
            if (position==0) {
            	SwarmLeaderboard.submitScore(8357, score);
            }

            for (int x=9; x>position; x--)
            {
                    //names[x]=names[x-1];
                    this.score[x]=this.score[x-1];
            }

           //this.names[position] = new String(name);
            this.score[position] = score;

            SharedPreferences.Editor editor = preferences.edit();
            for (int x=0; x<10; x++)
            {
                    //editor.putString("name"+x, this.names[x]);
                    editor.putLong("score"+x, this.score[x]);
            }
            editor.commit();
            return true;

    }
    
    public boolean clearHighScores()
    {
    	SharedPreferences.Editor editor = preferences.edit();
    	for (int x=0; x<10; x++)
        {
                //editor.putString("name"+x, this.names[x]);
                editor.putLong("score"+x, 0);
        }
        editor.commit();
    	return true;
    }

}

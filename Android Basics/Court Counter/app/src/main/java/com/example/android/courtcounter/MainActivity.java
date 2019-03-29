package com.example.android.courtcounter;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int[] Player1 = new int[]{R.id.A1, R.id.A2, R.id.A3, R.id.A4, R.id.A5};
    int[] Player2 = new int[]{R.id.B1, R.id.B2, R.id.B3, R.id.B4, R.id.B5};
    String[] Score = new String[]{"0", "15", "30", "40", "40Ad"};
    int Score1 = 0;
    int Score2 = 0;
    int match = 0;
    int Game1 = 0;
    int Game2 = 0;
    int Serve = 0;
    int Fault1 = 0;
    int Fault2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ServeReset() {
        TextView View2 = (TextView) findViewById(R.id.serving2);
        if (Serve == 0) {
            TextView View1 = (TextView) findViewById(R.id.serving1);
            View1.setText("First Serve");
            View2.setText("");
        } else if (Serve == 1) {
            View2.setText("First Serve");
            TextView View1 = (TextView) findViewById(R.id.serving1);
            View1.setText("");
        }

    }

    public void GameUpdate() {
        TextView View1 = (TextView) findViewById(Player1[match]);
        View1.setText("" + Game1);
        TextView View2 = (TextView) findViewById(Player2[match]);
        View2.setText("" + Game2);

        if (Game1 > Game2) {
            View1.setTypeface(Typeface.DEFAULT_BOLD);
            View1.setPaintFlags(View1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            View2.setPaintFlags(View2.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
            View2.setTypeface(Typeface.DEFAULT);
        } else if (Game2 > Game1) {
            View2.setTypeface(Typeface.DEFAULT_BOLD);
            View2.setPaintFlags(View2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            View1.setTypeface(Typeface.DEFAULT);
            View1.setPaintFlags(View1.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        } else if (Game2 == Game1) {
            View1.setPaintFlags(View1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            View1.setTypeface(Typeface.DEFAULT_BOLD);
            View2.setPaintFlags(View2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            View2.setTypeface(Typeface.DEFAULT_BOLD);
        }

        if ((Game1 == 6 || Game2 == 6) && match < 4) {
            match += 1;
            Game1 = 0;
            Game2 = 0;
        }

    }


    public void point1(View v) {
        Addpoint1();
    }

    public void Addpoint1() {
        if (Score1 < 3) {
            Score1 += 1;
            DisplayPoint1(Score1);
        } else if (Score2 < 3 || Score1 == 4) {
            Score1 = 0;
            Score2 = 0;
            Serve = (1 + Serve) % 2;
            AddGame1();
            DisplayPoint1(Score1);
            DisplayPoint2(Score2);
        } else if (Score2 == 3) {
            Score1 = 4;
            DisplayPoint1(Score1);
        } else if (Score2 == 4) {
            Score2 = 3;
            DisplayPoint2(Score2);
        }
        if (Serve == 0) {
            TextView View = (TextView) findViewById(R.id.serving1);
            View.setText("First Serve");
            Fault1 = 0;
        }
    }

    public void DisplayPoint1(int score) {
        TextView scoreView = (TextView) findViewById(R.id.score1);
        scoreView.setText(String.valueOf(Score[score]));
    }

    public void fault1(View v) {
        if (Serve == 0) {
            TextView View = (TextView) findViewById(R.id.serving1);
            if (Fault1 == 0) {
                View.setText("Second Serve");
                Fault1 = 1;
            } else if (Fault1 == 1) {
                Fault1 = 0;
                View.setText("First Serve");
                Addpoint2();
            }
        }
    }

    public void AddGame1() {
        if (Game1 < 9) {
            Game1 += 1;
        }
        ServeReset();
        GameUpdate();
    }


    public void point2(View v) {
        Addpoint2();
    }

    public void Addpoint2() {
        if (Score2 < 3) {
            Score2 += 1;
            DisplayPoint2(Score2);
        } else if (Score1 < 3 || Score2 == 4) {
            Score2 = 0;
            Score1 = 0;
            Serve = (1 + Serve) % 2;
            AddGame2();
            DisplayPoint1(Score1);
            DisplayPoint2(Score2);
        } else if (Score1 == 3) {
            Score2 = 4;
            DisplayPoint2(Score2);
        } else if (Score1 == 4) {
            Score1 = 3;
            DisplayPoint1(Score1);
        }
        if (Serve == 1) {
            TextView View = (TextView) findViewById(R.id.serving2);
            View.setText("First Serve");
            Fault2 = 0;
        }
    }

    public void DisplayPoint2(int score) {
        TextView scoreView = (TextView) findViewById(R.id.score2);
        scoreView.setText(String.valueOf(Score[score]));
    }

    public void fault2(View v) {
        if (Serve == 1) {
            TextView View = (TextView) findViewById(R.id.serving2);
            if (Fault2 == 0) {
                View.setText("Second Serve");
                Fault2 = 1;
            } else if (Fault2 == 1) {

                Fault2 = 0;
                View.setText("First Serve");
                Addpoint1();
            }
        }
    }

    public void AddGame2() {
        if (Game2 < 9) {
            Game2 += 1;
        }
        ServeReset();
        GameUpdate();
    }

    public void reset(View v) {
        Score1 = 0;
        Score2 = 0;
        match = 0;
        Game1 = 0;
        Game2 = 0;
        Serve = 1;
        Fault1 = 0;
        Fault2 = 0;
        ServeReset();
        GameUpdate();
        DisplayPoint1(Score1);
        DisplayPoint2(Score2);
        TextView SView1 = (TextView) findViewById(R.id.serving1);
        SView1.setText("First Serve");
        TextView SView2 = (TextView) findViewById(R.id.serving2);
        SView2.setText("");
        for (int i = 1; i <= 4; i++) {
            TextView View1 = (TextView) findViewById(Player1[i]);
            View1.setText("-");
            View1.setTypeface(Typeface.DEFAULT);
            View1.setPaintFlags(View1.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
            TextView View2 = (TextView) findViewById(Player2[i]);
            View2.setText("-");
            View2.setTypeface(Typeface.DEFAULT);
            View2.setPaintFlags(View2.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        }
    }

}

package com.example.android.whoami;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.String;
import java.lang.reflect.Method;
import java.util.Iterator;

import android.widget.Toast;

import java.util.*;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.message;
import static android.R.transition.move;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.widget.Toast.makeText;
import static java.lang.Integer.parseInt;

/**
 * in submit
 * change to next
 * reset the position of the answering pane from the alternative layout
 * messed with to avoid extremely long wrap content, long blank space before button
 * reset the image number
 * remove rule from views that have moved other views
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillView();
        findViewById(getLayer(1)[0][0]).setAlpha(0);


        String text = "Each touch of the image cost a point";
        Toast message1 = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        message1.show();
        text = "But reviews a clue";
        Toast message2 = makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        message2.show();
        findViewById(getLayer(0)[0][0]).setAlpha(1);

    }

    int QNumber = 0;
    int Score = 50;
    int ImgNumber = 0;
    boolean time = false;
    boolean Bsubmit = true;

    /* Contain a list of people in the order which they appears in the series of questions
     * used to refer to the int of the drawable resource images
     */
    String People[] = {"barack", "emma", "steve", "liam_payne", "johnny", "nelson", "kristen", "selena", "david"};

    /* All questions are populated between 2 alternating view groups
     * The list is used to refer to positions of view groups
     */
    int Layout1[][] = {
            {R.id.LayoutA1, R.id.QNA1, R.id.QA1, R.id.ImgA1, R.id.AA1, R.id.AB1, R.id.E1},
            {R.id.AA1_1, R.id.AA1_2, R.id.AA1_3, R.id.AA1_4},
            {R.id.AB1_1, R.id.AB1_2, R.id.AB1_3, R.id.AB1_4}};
    int Layout2[][] = {
            {R.id.LayoutA2, R.id.QNA2, R.id.QA2, R.id.ImgA2, R.id.AA2, R.id.AB2, R.id.E2},
            {R.id.AA2_1, R.id.AA2_2, R.id.AA2_3, R.id.AA2_4},
            {R.id.AB2_1, R.id.AB2_2, R.id.AB2_3, R.id.AB2_4}};

    // Uncheck other radio buttons when one is clicked
    public void radio(View v) {
        for (int i = 0; i < 4; i++) {
            RadioButton text = (RadioButton) findViewById(Layout1[2][i]);
            text.setChecked(false);
        }
        for (int i = 0; i < 4; i++) {
            RadioButton text = (RadioButton) findViewById(Layout2[2][i]);
            text.setChecked(false);
        }
        RadioButton text = (RadioButton) v;
        text.setChecked(true);

    }

    /* Each question consist of a image which is divided by a 4x3 grid
     * Everytime the image is clicked on, the method is called
     * It changes the source of the imageview
     *   to one which reveals one additional squares on the grid
     * The goal of the game is to answer the question of the quiz
     *   which is about the mysterious person in the image,
     *   with the least click possible
     * Every click deduces a mark
     */
    public void clickImg(View view) {
        time = true;
        if (ImgNumber < 12) {
            Score -= 1;
            ImgNumber++;
            ImageView v = (ImageView) view;
            Context context = v.getContext();
            String person = People[QNumber] + ImgNumber;
            int id = context.getResources().getIdentifier(person, "drawable", context.getPackageName());
            v.setImageResource(id);
        }
    }

    /* Returns one of the alternating layout(Viewgroup)
     *   as an array with ids to the children views
     */
    public int[][] getLayer(int number) {
        number += QNumber;
        if (number % 2 == 0) {
            return Layout2;
        } else {
            return Layout1;
        }
    }

    // Populates the top half of the layout (Question, Image)
    public void fillTop() {
        int[][] layout = getLayer(0);
        int[][] move = getLayer(1);
        View layout1 = findViewById(layout[0][0]);
        View layout2 = findViewById(move[0][0]);
        layout1.setAlpha(0);
        layout2.setVisibility(View.GONE);

        TextView questionA = (TextView) findViewById(layout[0][1]);
        int QNValue = QNumber + 1;
        String QNString = "Question" + QNValue;
        questionA.setText(QNString);

        TextView questionB = (TextView) findViewById(layout[0][2]);
        questionB.setText(questions[QNumber][1][0]);

        ImageView image = (ImageView) findViewById(layout[0][3]);
        Context context = image.getContext();
        String person = People[QNumber] + ImgNumber;
        int id = context.getResources().getIdentifier(person, "drawable", context.getPackageName());
        image.setImageResource(id);

    }

    /* Method populates the bottom half of the layout
     *   (EditText, checkButtons, or radioButtons)
     *   Determine which type of layout to fill based on the "Question" string array
     */
    public void fillView() {
        int[][] layout = getLayer(0);
        int[][] move = getLayer(1);
        fillTop();

        View imgMove = findViewById(move[0][3]);
        View imgLayout = findViewById(layout[0][3]);
        View check = findViewById(layout[0][4]);
        View radio = findViewById(layout[0][5]);
        EditText editText = (EditText) findViewById(layout[0][6]);

        check.setAlpha(0);
        radio.setAlpha(0);
        editText.setAlpha(0);
        editText.setEnabled(false);
        imgMove.setEnabled(false);
        imgLayout.setEnabled(true);

        if (questions[QNumber][0][0].equals("C")) {
            check.setAlpha(1);
            for (int i = 0; i < 4; i++) {
                CheckBox text = (CheckBox) findViewById(layout[1][i]);
                text.setText(questions[QNumber][2][i]);
                text.setEnabled(true);
                text.setChecked(false);
                editText.setVisibility(View.GONE);
                radio.setVisibility(View.GONE);
            }
            LinearLayout LL = (LinearLayout) findViewById(layout[0][5]);
        } else if (questions[QNumber][0][0].equals("R")) {
            radio.setAlpha(1);
            for (int i = 0; i < 4; i++) {
                RadioButton text = (RadioButton) findViewById(layout[2][i]);
                text.setText(questions[QNumber][2][i]);
                text.setChecked(false);
                text.setEnabled(true);
                check.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);

            }
        } else if (questions[QNumber][0][0].equals("E")) {
            editText.setAlpha(1);
            editText.setEnabled(true);
            editText.setHint(questions[QNumber][2][0]);
            editText.setText("");
            check.setVisibility(View.GONE);
            radio.setVisibility(View.GONE);

        }


    }

    /* Checks if an answer is given
     * Disallow the grading() method to be fully executed
     *   if no answer is given
     */
    public boolean breaking() {
        boolean Break = false;
        String Qtype = questions[QNumber][0][0];
        EditText editText = (EditText) findViewById(getLayer(0)[0][6]);
        if (Qtype == "E" && editText.getText().length() == 0) {
            Break = true;
        } else if (Qtype == "C") {
            Break = true;
            for (int i = 0; i < 4; i++) {
                CheckBox text = (CheckBox) findViewById(getLayer(0)[1][i]);
                if (text.isChecked()) {
                    Break = false;
                }
            }
        } else if (Qtype == "R") {
            Break = true;
            for (int i = 0; i < 4; i++) {
                RadioButton text = (RadioButton) findViewById(getLayer(0)[2][i]);
                if (text.isChecked()) {
                    Break = false;
                }
            }
        }
        return Break;
    }

    /* Shows a toast message
     *   if not answer is given when user press submit
     */
    public void checkBreaking() {
        if (breaking() && time) {
            String text = "Have You Put In An Answer?";
            Toast message1 = makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            message1.show();
        }
    }

    // Prevent user from changing input after pressing submit
    public void disableViews() {
        EditText editText = (EditText) findViewById(getLayer(0)[0][6]);
        editText.setEnabled(false);
        for (int i = 0; i < 4; i++) {
            CheckBox text = (CheckBox) findViewById(getLayer(0)[1][i]);
            text.setEnabled(false);
        }
        for (int i = 0; i < 4; i++) {
            RadioButton text = (RadioButton) findViewById(getLayer(0)[2][i]);
            text.setEnabled(false);
        }

        ImageView image = (ImageView) findViewById(getLayer(0)[0][3]);
        image.setEnabled(false);
    }

    // Show message and score when game ends
    public void finishMessage() {
        String text = "Final Score: " + Score;
        Toast message1 = makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        message1.show();
        text = "Challenge Your Friends With This Game";
        Toast message2 = makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        message2.show();
    }

    /* Make required views visible again
     * Some views are hidden when not used
     */
    public void nextArrange() {
        findViewById(getLayer(0)[0][0]).setVisibility(View.VISIBLE);
        findViewById(getLayer(0)[0][4]).setVisibility(View.VISIBLE);
        findViewById(getLayer(0)[0][5]).setVisibility(View.VISIBLE);
        findViewById(getLayer(0)[0][6]).setVisibility(View.VISIBLE);
        fillView();

    }

    // Animation for transitions between the 2 views
    public void Animate() {
        View layout = findViewById(getLayer(-1)[0][0]);
        layout.animate().alpha(0).setDuration(1000);
        View layout2 = findViewById(getLayer(0)[0][0]);
        layout2.setScaleX((float) 0.1);
        layout2.setScaleY((float) 0.1);
        layout2.animate().alpha(1).scaleX(1).scaleY(1).setDuration(1000);
    }

    // Respond when the submit/next button is clicked on
    public void submit(View v) {
        if (QNumber < questions.length + 1) {
            Button button = (Button) v;
            if (Bsubmit) {
                checkBreaking();
            }
            if (Bsubmit && !breaking()) {
                disableViews();
                grading();
                QNumber++;
                Bsubmit = false;
                ImgNumber = 0;

                if (QNumber == questions.length) {
                    finishMessage();
                    button.setEnabled(false);
                    button.setVisibility(View.INVISIBLE);

                } else if (QNumber < 8) {
                    String text = "Current Score: " + Score;
                    Toast message = makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    message.show();
                    button.setText("Next");
                    button.setBackgroundColor(getResources().getColor(R.color.submit));
                    button.setTextColor(getResources().getColor(R.color.grey));
                }
            } else if (!Bsubmit) {
                button.setText("Submit");
                Bsubmit = true;
                button.setBackgroundColor(getResources().getColor(R.color.next));
                button.setTextColor(getResources().getColor(R.color.white));

                nextArrange();
                Animate();
                time = false;
            }
        }
    }

    //Grade the answer submitted
    public void grading() {
        boolean correct = false;
        if (questions[QNumber][0][0] == "C") {
            correct = true;
            String[] answerList = questions[QNumber][3];
            List<Integer> ansNumList = new ArrayList<>();
            for (String s : answerList) {
                ansNumList.add(Integer.valueOf(s) - 1);
            }
            for (int i = 0; i < 4; i++) {
                CheckBox text = (CheckBox) findViewById(getLayer(0)[1][i]);
                boolean answer = text.isChecked();
                boolean contain = ansNumList.contains(i);
                if ((answer != contain)) {
                    correct = false;
                }
            }
        } else if (questions[QNumber][0][0] == "R") {
            String answerStr = questions[QNumber][3][0];
            int answerInt = Integer.valueOf(answerStr) - 1;
            RadioButton text = (RadioButton) findViewById(getLayer(0)[2][answerInt]);
            if (text.isChecked()) {
                correct = true;
            }
        } else if (questions[QNumber][0][0] == "E") {
            EditText text = (EditText) findViewById(getLayer(0)[0][6]);
            String answer = text.getText().toString().trim().toLowerCase();
            if (answer.equals(questions[QNumber][3][0].toLowerCase())) {
                correct = true;
            }
        }
        gradingMessage(correct);
    }

    /* Tell user if answer is correct
     * Show correct answer if wrong
     */
    public void gradingMessage(boolean correct) {
        String text = "Wrong! Answer: (" + android.text.TextUtils.join(",", questions[QNumber][3]) + ")";
        if (correct) {
            Score += 15;
            text = "Correct";
        }
        Toast message = makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        message.show();

    }

    /*Contain info for each question
     *  [Question][1] : Question type
     *  [Question][2] : Question
     *  [Question][3] : Options/Hint
     *  [Question][4] : Answer (Position for multiple choice)
     */
    String[][][] questions = {
            /**
             * type(E=editText,C=Checkbox, R=radioButton),question,options/hint,answer
            */
            {
                    {"R"},
                    {"Who is this?"},
                    {"Will Smith", "Barack Obama", "Stevie Wonder", "Bob Marley"},
                    {"2"}},
            {
                    {"C"},
                    {"Which of the following movie(s) is she in?"},
                    {"The Perks of Being a Wallflower", "My Week with Marilyn", "50 Shades of Grey", "The Amazing Spider-Man"},
                    {"1", "2"}},
            {
                    {"E"},
                    {"Which company is this person known for?"},
                    {"Company name"},
                    {"Apple"}},
            {
                    {"E"},
                    {"Who is this?"},
                    {"Full Name"},
                    {"Liam Payne"}},
            {
                    {"C"},
                    {"Which of the following movie(s) is he in?"},
                    {"Hairy Pothead and the Marijuana Stone (It's Real)", "Into the Woods ", "Miss Peregrine's Home for Peculiar Children", "Rango "},
                    {"2", "4"}},
            {
                    {"E"},
                    {"Who is this?"},
                    {"Full Name"},
                    {"Nelson Mandela"}},
            {
                    {"C"},
                    {"Which of the following movie(s) is she in?"},
                    {"Camp X-Ray", "Killer Condom (It's Real)", "The Mortal Instruments: City of Bones", "Still Alice"},
                    {"1", "4"}},
            {
                    {"R"},
                    {"Who is this?"},
                    {"Demi Lovato", "Miley Cyrus", "Selena Gomez", "Vanessa Hudgens"},
                    {"3"}},
            {
                    {"R"},
                    {"What is this man most known for?"},
                    {"Playing soccer", "Singing", "Playing tennis", "Acting"},
                    {"1"}},
    };


}

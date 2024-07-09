package com.example.quiz_app;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView totalQuestionTextView;

    TextView questionTextView;
    Button Answer_A,Answer_B,Answer_C,Answer_D;
    Button Submit;

    int Score;
    int totalQuestions = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
totalQuestionTextView = findViewById(R.id.Header);
questionTextView = findViewById(R.id.question);

Answer_A = findViewById(R.id.ans_A);
        Answer_B =  findViewById(R.id.ans_B);
                 Answer_C = findViewById(R.id.ans_C);
                         Answer_D = findViewById(R.id.ans_D);

  Submit = findViewById(R.id.submit_Button);

Answer_A.setOnClickListener(this);
        Answer_B.setOnClickListener(this);
        Answer_C.setOnClickListener(this);
        Answer_D.setOnClickListener(this);

        Submit.setOnClickListener(this);

totalQuestionTextView.setText("Total_Questions:" + totalQuestions);

loadNewQuestion();



        }

    @Override
    public void onClick(View v) {

        Answer_A.setBackgroundColor(Color.WHITE);
        Answer_B.setBackgroundColor(Color.WHITE);
        Answer_C.setBackgroundColor(Color.WHITE);
        Answer_D.setBackgroundColor(Color.WHITE);

        Button clickButton = (Button) v;
        if(clickButton.getId()==R.id.submit_Button){

            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){

                Score++;

            }

    currentQuestionIndex++;
    loadNewQuestion();
        }

        else{
selectedAnswer = clickButton.getText().toString();
clickButton.setBackgroundColor(Color.MAGENTA);

        }

    }

    private void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestions){


            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
Answer_A.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        Answer_B.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        Answer_C.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        Answer_D.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

    }

    private void finishQuiz() {
        String passStatus = "";
        if (Score > totalQuestions*0.60){

            passStatus = "Passed";
        }else {

           passStatus = "Failed";

        }

        new AlertDialog.Builder(this )
                .setTitle(passStatus)
                .setMessage("Score is: " + Score +"out of" + totalQuestions)
                .setPositiveButton("Restart", (dialodInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();


    }

    private void restartQuiz() {

        Score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();


    }

}



package com.example.avina.gameconnect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int activePlayer=0;     // 0=yellow     1=red
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningSequence={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    public void dropIn(View view)
    {

        ImageView counter=(ImageView) view;
        int tappedCounter=Integer.parseInt(counter.getTag().toString());


        if(gameState[tappedCounter]==2)
        {
            counter.setTranslationY(-1200f);

            gameState[tappedCounter]=activePlayer;
            if (activePlayer == 0)
            {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            }
            else
            {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1200f).rotation(360).setDuration(300);

            int gameStatus=0;       // 0 means NOT WON          1 means WON
            for (int[] currentSequence : winningSequence) {

                if (gameState[currentSequence[0]] == gameState[currentSequence[1]] &&
                        gameState[currentSequence[1]] == gameState[currentSequence[2]] &&
                        gameState[currentSequence[1]] != 2) {

                    gameStatus=1;
                //    LinearLayout barrierLayout = (LinearLayout) findViewById(R.id.barrierLayout);
                //    barrierLayout.setVisibility(View.VISIBLE);
                    String winner;
                    LinearLayout layout = (LinearLayout) findViewById(R.id.WinnerLayout);
                    if (gameState[currentSequence[0]] == 0) {
                        winner = "Yellow";
                        layout.setBackgroundResource(R.drawable.yellow_button);
                    } else {
                        winner = "Red";
                        layout.setBackgroundResource(R.drawable.red_button);
                    }

                    TextView winnerMessage = (TextView) findViewById(R.id.WinnerMessage);
                    winnerMessage.setText(winner + " has Won!");
                    layout.setVisibility(View.VISIBLE);
                    layout.animate().alpha(1f).setDuration(400);
                }
            }
            if(gameStatus==0) {

                int flag = 0;
                for (int state : gameState) {
                    if (state != 2)
                        continue;
                    else {
                        flag = 1;       // flag=1 means UN-PLAYED space encountered
                        break;
                    }
                }
                if (flag == 0) {
                    // It's a DRAW
                    LinearLayout layout = (LinearLayout) findViewById(R.id.WinnerLayout);
                    layout.setBackgroundResource(R.drawable.green_button);
                    TextView winnerMessage = (TextView) findViewById(R.id.WinnerMessage);
                    winnerMessage.setText("It's a Draw");
                    layout.setVisibility(View.VISIBLE);
                    layout.animate().alpha(1f).setDuration(400);
                }
            }
        }
    }

    public void playAgain(View view){

        LinearLayout layout=(LinearLayout) findViewById(R.id.WinnerLayout);
        layout.setVisibility(View.INVISIBLE);
        layout.setAlpha(0f);
        for(int i=0;i<gameState.length;i++){
            gameState[i]=2;
        }
        activePlayer=0;

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0;i<gridLayout.getChildCount();i++){

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

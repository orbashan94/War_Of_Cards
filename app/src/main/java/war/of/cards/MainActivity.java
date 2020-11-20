package war.of.cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    private TextView main_LBL_scorePlayerA;
    private TextView main_LBL_scorePlayerB;
    private ImageView main_IMG_play;
    private ImageView main_IMG_cardPlayerA;
    private ImageView main_IMG_cardPlayerB;

    private Deck deck;
    private Card playerACard;
    private Card playerBCard;
    private int scorePlayerA = 0;
    private int scorePlayerB = 0;
    private String playerWin;
    private int scoreWinner = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("pttt", "onCreate");
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //this is for hiding status bar
        setContentView(R.layout.activity_main);

        main_IMG_play = findViewById(R.id.main_IMG_play);
        main_IMG_cardPlayerA = findViewById(R.id.main_IMG_cardPlayerA);
        main_IMG_cardPlayerB = findViewById(R.id.main_IMG_cardPlayerB);
        main_LBL_scorePlayerA = findViewById(R.id.main_LBL_scorePlayerA);
        main_LBL_scorePlayerB = findViewById(R.id.main_LBL_scorePlayerB);

        deck = new Deck();
        deck.shuffle();

        main_IMG_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deck.empty() == false) {
                    playRound();
                } else {
                    if (scorePlayerA > scorePlayerB) {
                        playerWin = "Player A";
                        scoreWinner = scorePlayerA;
                    } else if (scorePlayerB > scorePlayerA) {
                        playerWin = "Player B";
                        scoreWinner = scorePlayerB;
                    } else {
                        playerWin = "duce";
                        scoreWinner = scorePlayerA;
                    }
                    openWinnerActivity(MainActivity.this);
                }

            }
        });
    }

    private void playRound() {
        playerACard = deck.takeCard();
        String cardA = "img_card_" + playerACard.getType().ordinal() + "_" + playerACard.getRank();
        int resourceId = this.getResources().getIdentifier(cardA, "drawable", this.getPackageName());
        main_IMG_cardPlayerA.setImageResource(resourceId);

        playerBCard = deck.takeCard();
        String cardB = "img_card_" + playerBCard.getType().ordinal() + "_" + playerBCard.getRank();
        resourceId = this.getResources().getIdentifier(cardB, "drawable", this.getPackageName());
        main_IMG_cardPlayerB.setImageResource(resourceId);


        if (playerACard.getRank() > playerBCard.getRank()) {
            scorePlayerA++;
            main_LBL_scorePlayerA.setText("" + scorePlayerA);

        } else if (playerBCard.getRank() > playerACard.getRank()) {
            scorePlayerB++;
            main_LBL_scorePlayerB.setText("" + scorePlayerB);

        }
    }


    private void openWinnerActivity(MainActivity activity) {
        Intent myIntent = new Intent(activity, Activity_Winner.class);
        myIntent.putExtra(Activity_Winner.EXTRA_KEY_WINNER, playerWin);
        myIntent.putExtra(Activity_Winner.EXTRA_KEY_SCORE, scoreWinner);
        startActivity(myIntent);
        finish(); //close Main_Activity
    }


    @Override
    protected void onStart() {
        Log.d("pttt", "onStart");

        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("pttt", "onResume");

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("pttt", "onPause");

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("pttt", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("pttt", "onDestroy");
        super.onDestroy();
    }


}

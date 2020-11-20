package war.of.cards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Activity_Winner extends AppCompatActivity {

    public static final String EXTRA_KEY_WINNER = "EXTRA_KEY_WINNER";
    public static final String EXTRA_KEY_SCORE = "EXTRA_KEY_SCORE";


    private TextView winner_LBL_playerWin;
    private TextView winner_LBL_score;
    private Button winner_BTN_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//this is for hiding status bar
        setContentView(R.layout.activity_winner);

        winner_LBL_playerWin = findViewById(R.id.winner_LBL_playerWin);
        winner_LBL_score = findViewById(R.id.winner_LBL_score);


        String winner = getIntent().getStringExtra(EXTRA_KEY_WINNER);
        winner_LBL_playerWin.setText("" + winner);

        int winnerScore = getIntent().getIntExtra("EXTRA_KEY_SCORE", -1);
        winner_LBL_score.setText("Score: " + winnerScore);



    }


}
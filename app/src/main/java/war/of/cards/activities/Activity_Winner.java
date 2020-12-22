package war.of.cards.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import war.of.cards.R;
import war.of.cards.objects.Record;
import war.of.cards.objects.TopTen;
import war.of.cards.utils.Constants;
import war.of.cards.utils.MySP;
import war.of.cards.utils.MySignal;

public class Activity_Winner extends Activity_Base {


    private TextView winner_LBL_playerWin;
    private TextView winner_LBL_score;
    private Button winner_BTN_menu;
    private Button winner_BTN_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("pttt", "create");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        findViews();
        initViews();
        showWinner();
    }


    private void findViews() {
        winner_LBL_playerWin = findViewById(R.id.winner_LBL_playerWin);
        winner_LBL_score = findViewById(R.id.winner_LBL_score);
        winner_BTN_menu = findViewById(R.id.winner_BTN_menu);
        winner_BTN_record = findViewById(R.id.winner_BTN_record);
    }

    private void initViews() {
        winner_BTN_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        winner_BTN_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecordActivity(Activity_Winner.this);
                finish();
            }
        });

    }

    private void showWinner() {

        String winnerName = getIntent().getStringExtra(Constants.EXTRA_KEY_WINNER);
        int winnerScore = getIntent().getIntExtra(Constants.EXTRA_KEY_SCORE, -1);
        double latitude = getIntent().getDoubleExtra(Constants.EXTRA_KEY_LATITUDE, Constants.ZERO);
        double longitude = getIntent().getDoubleExtra(Constants.EXTRA_KEY_LONGITUDE, Constants.ZERO);
        Record record = new Record(winnerName, System.currentTimeMillis(), winnerScore, latitude, longitude);

        int rank;

        if (!(winnerName.equals(Constants.DRAW_MESSAGE))) {
            winner_LBL_playerWin.setText(winnerName);

            rank = checkRecord(record);
            if (rank != Constants.NOT_IN_TOP_TEN) {
                MySignal.getInstance().toast("New Record, Rank " + rank + " in Top 10");
            }

        } else {
            winner_LBL_playerWin.setText(Constants.DRAW_MESSAGE);
        }
        winner_LBL_score.setText("Score: " + winnerScore);

    }

    private int checkRecord(Record record) {
        int rank;

        String topTenString = MySP.getInstance().getString(MySP.KEYS.TOP_TEN, "");
        TopTen topTen = topTenString.isEmpty() ? new TopTen() : new Gson().fromJson(topTenString, TopTen.class);

        rank = topTen.addNewRecord(record);
        MySP.getInstance().putString(MySP.KEYS.TOP_TEN, new Gson().toJson(topTen));

        return rank;

    }

    private void openRecordActivity(Activity_Winner activity) {
        Intent myIntent = new Intent(activity, Activity_Record.class);
        startActivity(myIntent);
        finish();
    }


}
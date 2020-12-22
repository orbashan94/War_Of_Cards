package war.of.cards.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import war.of.cards.objects.GameManager;
import war.of.cards.R;
import war.of.cards.utils.Constants;
import war.of.cards.utils.MySignal;


public class Activity_Game extends Activity_Base {

    private TextView game_LBL_scorePlayerA;
    private TextView game_LBL_scorePlayerB;
    private ImageView game_IMG_cardPlayerA;
    private ImageView game_IMG_cardPlayerB;
    private ImageView game_IMG_timer;
    private ProgressBar game_PRG_gameProgress;
    private ScheduledFuture<?> scheduledFuture;

    private GameManager gameManager;
    private int countRounds = 0;
    private boolean gameStart = false;

    private FusedLocationProviderClient client;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("pttt", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getCurrentLocation();
        findViews();

        gameManager = new GameManager();

        Glide
                .with(this)
                .load(R.drawable.img_card_0_0)
                .into(game_IMG_cardPlayerA);

        Glide
                .with(this)
                .load(R.drawable.img_card_0_0)
                .into(game_IMG_cardPlayerB);

        initViews();


    }

    private void initViews() {
        game_IMG_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameManager.getDeck().empty() == false) {
                    game_IMG_timer.setClickable(false);
                    gameStart = true;
                    start();
                }

            }
        });
    }

    private void getCurrentLocation() {
        client = LocationServices.getFusedLocationProviderClient(this);

        if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_REQUEST_CODE);

        if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            });
        }

    }

    private void findViews() {
        game_IMG_cardPlayerA = findViewById(R.id.game_IMG_cardPlayerA);
        game_IMG_cardPlayerB = findViewById(R.id.game_IMG_cardPlayerB);
        game_LBL_scorePlayerA = findViewById(R.id.game_LBL_scorePlayerA);
        game_LBL_scorePlayerB = findViewById(R.id.game_LBL_scorePlayerB);
        game_PRG_gameProgress = findViewById(R.id.game_PRG_gameProgress);
        game_IMG_timer = findViewById(R.id.game_IMG_timer);
    }


    private void playRound() {
        MySignal.getInstance().playSound(R.raw.snd_flip_card);
        countRounds++;
        game_PRG_gameProgress.setProgress(countRounds);

        gameManager.setPlayerACard(gameManager.getDeck().takeCard());
        String cardA = "img_card_" + gameManager.getPlayerACard().getType().ordinal() +
                "_" + gameManager.getPlayerACard().getRank();
        Glide
                .with(this)
                .load(getImage(cardA))
                .into(game_IMG_cardPlayerA);

        gameManager.setPlayerBCard(gameManager.getDeck().takeCard());
        String cardB = "img_card_" + gameManager.getPlayerBCard().getType().ordinal() +
                "_" + gameManager.getPlayerBCard().getRank();
        Glide
                .with(this)
                .load(getImage(cardB))
                .into(game_IMG_cardPlayerB);

        gameManager.calcScore();
        game_LBL_scorePlayerA.setText("" + gameManager.getScorePlayerA());
        game_LBL_scorePlayerB.setText("" + gameManager.getScorePlayerB());

        if (gameManager.getDeck().empty() == true) {
            stop();
            gameManager.endGame();
            gameStart = false;
            inputName();
        }

    }

    private void inputName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                gameManager.setWinner(m_Text);
                openWinnerActivity(Activity_Game.this);

            }
        });
        builder.show();
    }

    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
        return drawableResourceId;
    }

    private void openWinnerActivity(Activity_Game activity) {
        Intent myIntent = new Intent(activity, Activity_Winner.class);
        myIntent.putExtra(Constants.EXTRA_KEY_WINNER, gameManager.getWinner());
        myIntent.putExtra(Constants.EXTRA_KEY_SCORE, gameManager.getScoreWinner());
        myIntent.putExtra(Constants.EXTRA_KEY_LATITUDE, latitude);
        myIntent.putExtra(Constants.EXTRA_KEY_LONGITUDE, longitude);
        startActivity(myIntent);
        finish();
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
        if (gameStart == true) {
            start();
        }
    }

    @Override
    protected void onPause() {
        Log.d("pttt", "onPause");
        super.onPause();
        if (gameStart == true) {
            stop();
        }
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


    public void start() {
        scheduledFuture = new ScheduledThreadPoolExecutor(5).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playRound();
                    }
                });
            }
        }, 0, Constants.TIME_INTERVAL, TimeUnit.MILLISECONDS);
    }


    private void stop() {
        scheduledFuture.cancel(false);
    }
}




package war.of.cards.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import war.of.cards.R;

public class Activity_Menu extends Activity_Base {

    private Button main_BTN_play;
    private Button main_BTN_record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        initViews();


    }

    private void findViews() {
        main_BTN_play = findViewById(R.id.main_BTN_play);
        main_BTN_record = findViewById(R.id.main_BTN_record);

    }

    private void initViews() {

        main_BTN_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(Activity_Game.class);
            }
        });


        main_BTN_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(Activity_Record.class);
            }
        });

    }

    private void openActivity(Class activity) {
        Intent myIntent = new Intent(this, activity);
        startActivity(myIntent);

    }


}
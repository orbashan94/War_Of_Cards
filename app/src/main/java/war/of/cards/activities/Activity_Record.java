package war.of.cards.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import war.of.cards.R;
import war.of.cards.callbacks.CallBack_List;
import war.of.cards.fragments.Fragment_List;
import war.of.cards.fragments.Fragment_Map;
import war.of.cards.objects.Record;


public class Activity_Record extends Activity_Base {

    private Fragment_List fragment_list;
    private Fragment_Map fragment_map;
    private Button record_BTN_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        findViews();
        initViews();
        initFragments();


    }

    private void findViews() {
        record_BTN_menu = findViewById(R.id.record_BTN_menu);

    }

    private void initViews() {
        record_BTN_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private CallBack_List callBack_list = new CallBack_List() {
        @Override
        public void addMarkerToMap(double latitude, double longitude, String name) {
            fragment_map.addMarker(latitude, longitude, name);
        }
    };


    private void initFragments() {
        fragment_list = new Fragment_List();
        fragment_list.setCallBack_list(callBack_list);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.record_LAY_list, fragment_list)
                .commit();


        fragment_map = new Fragment_Map();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.record_LAY_map, fragment_map)
                .commit();
    }


}
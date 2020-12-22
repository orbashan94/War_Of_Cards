package war.of.cards.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.ArrayList;

import war.of.cards.callbacks.CallBack_List;
import war.of.cards.R;
import war.of.cards.objects.Record;
import war.of.cards.objects.TopTen;
import war.of.cards.utils.MySP;
import war.of.cards.utils.MySignal;

public class Fragment_List extends Fragment {

    private ListView list_LST_topTenList;
    private CallBack_List callBack_list;

    public void setCallBack_list(CallBack_List callBack_list) {
        this.callBack_list = callBack_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();

        displayTopTenList();
        return view;
    }

    private void findViews(View view) {
        list_LST_topTenList = view.findViewById(R.id.list_LST_topTenList);
    }

    private void initViews() {
        list_LST_topTenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Record record = (Record) parent.getItemAtPosition(position);

                if (callBack_list != null) {
                    callBack_list.addMarkerToMap(record.getLatitude(), record.getLongitude(), record.getName());
                }
            }
        });


    }

    public void displayTopTenList() {
        String topTenString = MySP.getInstance().getString(MySP.KEYS.TOP_TEN, "");
        TopTen topTen = topTenString.isEmpty() ? new TopTen() : new Gson().fromJson(topTenString, TopTen.class);

        list_LST_topTenList.setAdapter(MySignal.getInstance().getArrayAdapter(topTen));

    }

}

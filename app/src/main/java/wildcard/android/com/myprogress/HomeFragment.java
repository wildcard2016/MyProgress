package wildcard.android.com.myprogress;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TextView tasksAccomplished;
    private TextView thisMonth;
    private TextView thisWeek;
    private TextView today;
    private TextView date;
    private ProgressBar monthProgress;
    private ProgressBar weekProgress;
    private ProgressBar dailyProgress;

    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private MyAdapter adapter;
    private ArrayList<String> listViewElements;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        setupUIs(v);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewTask.class);
                startActivity(intent);
            }
        });
        return v;
    }

    private void setupUIs(View v) {
        tasksAccomplished = (TextView) v.findViewById(R.id.tasks_accomplished);
        thisMonth = (TextView) v.findViewById(R.id.this_month);
        thisWeek = (TextView) v.findViewById(R.id.this_week);
        today = (TextView) v.findViewById(R.id.today);
        date = (TextView) v.findViewById(R.id.date);
        monthProgress = (ProgressBar) v.findViewById(R.id.month_progress);
        weekProgress = (ProgressBar) v.findViewById(R.id.week_progress);
        dailyProgress = (ProgressBar) v.findViewById(R.id.daily_progress);

        monthProgress.setScaleY(2f);
        weekProgress.setScaleY(2f);
        dailyProgress.setScaleY(2f);

        listView = (ListView) v.findViewById(R.id.list_view);
        listView.setDivider(null);
        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fab);
        floatingActionButton.attachToListView(listView);

        listViewElements = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            listViewElements.add("sample schedule data " + i);
        }

        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listViewElements.size();
        }

        @Override
        public Object getItem(int i) {
            return listViewElements.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            FrameLayout frameLayout;
            TextView textView;
            View v = view;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE
                );
                v = inflater.inflate(R.layout.list_content, null);
            }
            String schedule = (String) getItem(i);
            if (schedule != null) {
                textView = (TextView) v.findViewById(R.id.text);
                textView.setText(schedule);

                frameLayout = (FrameLayout) v.findViewById(R.id.tag_container);
                if (i % 5 == 0) {
                    frameLayout.setBackgroundColor(Color.parseColor("#5c6bc0"));
                }else if (i % 5 == 1) {
                    frameLayout.setBackgroundColor(Color.parseColor("#ef5350"));
                }else if (i % 5 == 2) {
                    frameLayout.setBackgroundColor(Color.parseColor("#66bb6a"));
                }else if (i % 5 == 3) {
                    frameLayout.setBackgroundColor(Color.parseColor("#ffa726"));
                }else {
                    frameLayout.setBackgroundColor(Color.parseColor("#26a69a"));
                }
            }
            return v;
        }
    }

    private int getColor(String tag) {
        switch (tag) {
            case "work":
                return Color.parseColor("#5c6bc0");
            case "study":
                return Color.parseColor("#66bb6a");
            case "social":
                return Color.parseColor("#ffa726");
            case "read":
                return Color.parseColor("#26a69a");
            case "travel":
                return Color.parseColor("#ef5350");
            default:
                return 0;
        }
    }
}

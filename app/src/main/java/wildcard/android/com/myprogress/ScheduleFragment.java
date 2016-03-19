package wildcard.android.com.myprogress;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    private static final String TAG = "Schedule Fragment";

    public static SearchView searchView;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private MyAdapter adapter;
    private ArrayList<String> listViewElements;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        setupUIs(v);
        return v;
    }

    private void setupUIs(View v) {
        searchView = (SearchView) v.findViewById(R.id.search_view);
        android.support.v7.widget.SearchView.SearchAutoComplete searchAutoComplete = (android.support.v7.widget.SearchView.SearchAutoComplete)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        setListeners();
        listView = (ListView) v.findViewById(R.id.list_view2);
        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fab2);
        listView.setDivider(null);
        floatingActionButton.attachToListView(listView);

        listViewElements = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            listViewElements.add("Read Android Textbook" + i);
        }

        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchView.setIconified(true);
                String item = (String) listView.getItemAtPosition(i);
                Log.d(TAG, "onItemClicked: " + item);
            }
        });
    }

    private void setListeners() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange");
                return true;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
                //Click to enter keywords
            }
        });
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
            TextView taskName;
            TextView period;
            TextView progress;
            View v = view;
            ProgressBar progressBar;
            LinearLayout backgroundLight;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE
                );
                v = inflater.inflate(R.layout.list_content2, viewGroup, false);
            }
            String schedule = (String) getItem(i);
            if (schedule != null) {
                taskName = (TextView) v.findViewById(R.id.task_name);
                taskName.setText(schedule);

                period = (TextView) v.findViewById(R.id.period);
                progress = (TextView) v.findViewById(R.id.task_progress);

                progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
                progressBar.setScaleY(2f);

                backgroundLight = (LinearLayout) v.findViewById(R.id.background_light);

                frameLayout = (FrameLayout) v.findViewById(R.id.tasks_history);
                if (i % 5 == 0) {
                    setColor("work", frameLayout, taskName, period, progress, backgroundLight);
                }else if (i % 5 == 1) {
                    setColor("study", frameLayout, taskName, period, progress, backgroundLight);
                }else if (i % 5 == 2) {
                    setColor("social", frameLayout, taskName, period, progress, backgroundLight);
                }else if (i % 5 == 3) {
                    setColor("read", frameLayout, taskName, period, progress, backgroundLight);
                }else {
                    setColor("travel", frameLayout, taskName, period, progress, backgroundLight);
                }
                if (i > 10) {
                    setColor("old", frameLayout, taskName, period, progress, backgroundLight);
                }
            }
            return v;
        }

        private void setColor(String color, FrameLayout frameLayout, TextView taskName, TextView period,
                              TextView progress, LinearLayout linearLayout) {
            int colorCode = getColor(color);
            int lightColorCode = getLightColor(color);
            frameLayout.setBackgroundColor(colorCode);
            taskName.setTextColor(colorCode);
            period.setTextColor(colorCode);
            progress.setTextColor(colorCode);
            linearLayout.setBackgroundColor(lightColorCode);
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
            case "old":
                return Color.parseColor("#aca6a6");
            default:
                return 0;
        }
    }

    private int getLightColor(String tag) {
        switch (tag) {
            case "work":
                return Color.parseColor("#c5cae9");
            case "study":
                return Color.parseColor("#c8e6c9");
            case "social":
                return Color.parseColor("#ffe082");
            case "read":
                return Color.parseColor("#b2dfbb");
            case "travel":
                return Color.parseColor("#ffcdd2");
            case "old":
                return Color.parseColor("#f5f5f5");
            default:
                return 0;
        }
    }
}

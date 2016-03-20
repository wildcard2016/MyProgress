package wildcard.android.com.myprogress;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private ArrayList<String> listViewElements_tag;
    private ArrayList<String> listViewElements_start;
    private ArrayList<String> listViewElements_end;

    private ArrayList<String> items;
    private ArrayList<String> tags;
    private ArrayList<String> start;
    private ArrayList<String> end;
    private ArrayList<String> progress;

    private SharedPreferences sharedPreferences;

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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        searchView = (SearchView) v.findViewById(R.id.search_view);
        android.support.v7.widget.SearchView.SearchAutoComplete searchAutoComplete = (android.support.v7.widget.SearchView.SearchAutoComplete)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        listView = (ListView) v.findViewById(R.id.list_view2);
        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fab2);
        listView.setDivider(null);
        floatingActionButton.attachToListView(listView);

        setListeners();

        // sharedPreferences
        items = MyUtils.getArr(sharedPreferences, "Names");
        tags = MyUtils.getArr(sharedPreferences, "Tags");
        start = MyUtils.getArr(sharedPreferences, "Start");
        end = MyUtils.getArr(sharedPreferences, "End");

        resetListView();

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

    private void updateListView(String newText) {
        int length = items.size();
        listViewElements = new ArrayList<>();
        listViewElements_tag = new ArrayList<>();
        listViewElements_start = new ArrayList<>();
        listViewElements_end = new ArrayList<>();
        for (int i = 1; i < length; i++) {
            if (items.get(i).toLowerCase().contains(newText.toLowerCase())) {
                listViewElements.add(items.get(i));
                listViewElements_tag.add(tags.get(i));
                listViewElements_start.add(start.get(i));
                listViewElements_end.add(end.get(i));
            }
        }
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private void resetListView() {
        listViewElements = new ArrayList<>();
        listViewElements_tag = new ArrayList<>();
        listViewElements_start = new ArrayList<>();
        listViewElements_end = new ArrayList<>();
        for (int i = 1; i < items.size(); i++) {
            listViewElements.add(items.get(i));
            listViewElements_tag.add(tags.get(i));
            listViewElements_start.add(start.get(i));
            listViewElements_end.add(end.get(i));
        }
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
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
                if (TextUtils.isEmpty(s)) {
                    resetListView();
                } else {
                    updateListView(s);
                }
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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewTask.class);
                startActivity(intent);
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
            if (! schedule.equals("fake")) {
                taskName = (TextView) v.findViewById(R.id.task_name);

                period = (TextView) v.findViewById(R.id.period);
                progress = (TextView) v.findViewById(R.id.task_progress);

                progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
                progressBar.setScaleY(2f);

                backgroundLight = (LinearLayout) v.findViewById(R.id.background_light);

                frameLayout = (FrameLayout) v.findViewById(R.id.tasks_history);
                String tag = listViewElements_tag.get(i);
                String name = listViewElements.get(i);
                String start = listViewElements_start.get(i);
                String end = listViewElements_end.get(i);

                taskName.setText(name);
                period.setText(start + " - " + end);

                setColor(tag.toLowerCase(), frameLayout, taskName, period, progress, backgroundLight);
            }
            return v;
        }

        private void setColor(String color, FrameLayout frameLayout, TextView taskName, TextView period,
                              TextView progress, LinearLayout linearLayout) {
            int colorCode = Color.WHITE; //MyUtils.getColor(color);
            if (color.equals("old")) {
                colorCode = Color.parseColor("#9d9d9d");
            }
            int lightColorCode = MyUtils.getLColor(color);
            frameLayout.setBackgroundColor(lightColorCode);
            taskName.setTextColor(colorCode);
            period.setTextColor(colorCode);
            progress.setTextColor(colorCode);
            linearLayout.setBackgroundColor(lightColorCode);
        }
    }
}

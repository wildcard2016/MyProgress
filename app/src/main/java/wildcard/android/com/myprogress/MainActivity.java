package wildcard.android.com.myprogress;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new HomeFragment();
                    case 1:
                        return new ScheduleFragment();
                    case 2:
                        return new ChartFragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Demo Start");
                demonstrator();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);


        final String[] tabNames = {"Home", "Schedule", "Chart"};
        /*
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home_white_24dp_n));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_format_list_bulleted_white_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_pie_chart_white_24dp));
        */
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (ScheduleFragment.searchView != null) {
                    ScheduleFragment.searchView.setIconified(true);
                }
                switch (tab.getPosition()) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        toolbarTitle.setText(tabNames[0]);
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_white_24dp_n);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_format_list_bulleted_white_24dp);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_pie_chart_white_24dp);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        toolbarTitle.setText(tabNames[1]);
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_white_24dp);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_format_list_bulleted_white_24dp_n);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_pie_chart_white_24dp);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        toolbarTitle.setText(tabNames[2]);
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_white_24dp);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_format_list_bulleted_white_24dp);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_pie_chart_white_24dp_n);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        setupIcons();
        setupViewPagerListener(viewPager);

        tempSetting();
    }

    private void setupIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_white_24dp_n);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_format_list_bulleted_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_pie_chart_white_24dp);
    }

    private void setupViewPagerListener(ViewPager viewPager) {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void tempSetting() {
        ImageView imageView = (ImageView) findViewById(R.id.app_logo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Delete Preferences");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
            }
        });
    }

    private void demonstrator() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> start = new ArrayList<>();
        ArrayList<String> end = new ArrayList<>();
        ArrayList<String> tag = new ArrayList<>();
        ArrayList<String> progress = new ArrayList<>();

        int num = 20;
        Random random = new Random();
        long startTime;
        long endTime;

        for (int i = 0; i < num; i++) {
            switch (random.nextInt(100) % 9) {
                case 0:
                    tag.add("social");
                    name.add(Social());
                    break;
                case 1:
                    tag.add("work");
                    name.add("Work " + Work());
                    break;
                case 2:
                    tag.add("read");
                    name.add("Read " + Read());
                    break;
                case 3:
                    tag.add("read");
                    name.add("Read " + Read());
                    break;
                case 4:
                    tag.add("travel");
                    name.add("Go " + Go());
                    break;
                case 5:
                    tag.add("travel");
                    name.add("Go " + Go());
                    break;
                default:
                    tag.add("study");
                    name.add("Study " + Study());
            }
            startTime = (long) (System.currentTimeMillis() - random.nextDouble() * 4000000000l);
            endTime = (long) (startTime + random.nextDouble() * 1500000000);
            Date date = new Date(startTime);
            Date date2 = new Date(endTime);
            String p = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(date);
            String p2 = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(date2);
            Log.d(TAG, p + " " + p2);
            start.add(p);
            end.add(p2);
            progress.add(String.valueOf(random.nextInt(100)));
        }
        editor.putString("Names", MyUtils.ArrToStr(name));
        editor.putString("Tags", MyUtils.ArrToStr(tag));
        editor.putString("Start", MyUtils.ArrToStr(start));
        editor.putString("End", MyUtils.ArrToStr(end));
        editor.putString("Progress", MyUtils.ArrToStr(progress));
        editor.apply();
    }

    private String Read() {
        String[] s = {"Shakespeare", "Detective Conan", "Business Magazine", "Poems", "Scientific Journal", "Newspaper",
                "Dictionary"};
        return chooseOne(s);
    }

    private String Go() {
        String[] s = {"Hiking", "Swimming", "Skiing", "to Mountains", "Shopping" ,"to a Park", "to School", "to the Sea",
                "Cycling"};
        return chooseOne(s);
    }

    private String Study() {
        String[] s = {"Math", "Physics", "Java", "C++", "Informatics", "Robotics", "Programming", "Chemistry", "History",
                "English"};
        return chooseOne(s);
    }

    private String Work() {
        String[] s = {"at Company", "at School", "at Starbucks", "at Restaurant"};
        return chooseOne(s);
    }

    private String Social() {
        String[] s = {"Job Interview", "Prepare for Internship", "Volunteer Activity"};
        return chooseOne(s);
    }

    private String chooseOne(String[] s) {
        int n = s.length;
        Random random = new Random();
        return s[random.nextInt(n)];
    }
}

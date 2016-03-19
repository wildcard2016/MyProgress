package wildcard.android.com.myprogress;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
}

package wildcard.android.com.myprogress;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Shiken Zijian Xu on 2016/03/19.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> listFragments;
    public MyFragmentPagerAdapter(FragmentManager fragmentManager,
                                  List<Fragment> listFragments) {
        super(fragmentManager);
        this.listFragments = listFragments;
    }
    @Override
    public Fragment getItem(int i) {
        return listFragments.get(i);
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }
}

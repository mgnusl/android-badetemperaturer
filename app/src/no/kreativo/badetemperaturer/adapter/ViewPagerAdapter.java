package no.kreativo.badetemperaturer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Kart";
            case 1:
                return "Oversikt";
            case 2:
                return "Favoritter";
            case 3:
                return "Kaldest og varmest";
        }
        /*if (position == 0)
            return "Kart";
        if (position == 1)
            return "Oversikt";
        if (position == 2)
            return "Favoritter";
        if (position == 3)
            return "Kaldest og varmest";*/
        return null;
    }
}

package com.hmproductions.coronacalculator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.hmproductions.coronacalculator.fragments.RadiusFragment;
import com.hmproductions.coronacalculator.fragments.RatioFragment;
import com.hmproductions.coronacalculator.fragments.WeatherFragment;

public class ContentAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_FRAGMENTS = 3;

    ContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new WeatherFragment();
            case 1:
                return new RatioFragment();
            default:
                return new RadiusFragment();
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENTS;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Weather";
            case 1:
                return "HVDC";
            default:
                return "Radius";
        }
    }
}

package com.example.projektwtm;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private Context myContext;
    int totalTabs;

    public PagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SearchGroupsFragment searchGroupsFragment = new SearchGroupsFragment();
                return searchGroupsFragment;
            case 1:
                SearchAppsFragment searchAppsFragment = new SearchAppsFragment();
                return searchAppsFragment;
            case 2:
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

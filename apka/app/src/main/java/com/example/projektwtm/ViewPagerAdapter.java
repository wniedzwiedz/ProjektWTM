package com.example.projektwtm;

import android.content.res.Resources;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.projektwtm.fragmenty.SearchAppsFragment;
import com.example.projektwtm.fragmenty.SettingsFragment;
import com.example.projektwtm.fragmenty.UserGroupsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;
    SparseArray<Fragment> fragments = new SparseArray<>();

    public ViewPagerAdapter(final Resources resources, FragmentManager fm) {
        super(fm);
        this.resources = resources;

    }

    @Override
    public Fragment getItem(int pos) {
        final Fragment fragment;
        switch (pos) {
            case 0:
                fragment = new UserGroupsFragment();
                break;
            case 1:
                fragment = new SearchAppsFragment();
                break;
            case 2:
                fragment = new SettingsFragment();
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0:
                return "MANAGER";
            case 1:
                return "SEARCH";
            case 2:
                return "SETTINGS";
            default:
                return null;
        }
    }

    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return fragments.get(position);
    }

}

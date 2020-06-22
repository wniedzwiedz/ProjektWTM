//package com.example.projektwtm;
//
//import android.content.Context;
//import android.util.SparseArray;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//
//import com.example.projektwtm.fragmenty.SearchAppsFragment;
//import com.example.projektwtm.fragmenty.SettingsFragment;
//import com.example.projektwtm.fragmenty.UserGroupsFragment;
//import com.example.projektwtm.modele.User;
//
//public class PagerAdapter extends FragmentStatePagerAdapter {
//
//    private Context myContext;
//    int totalTabs;
//    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
//
//
//    public PagerAdapter(Context context, FragmentManager fm, int totalTabs) {
//        super(fm);
//        myContext = context;
//        this.totalTabs = totalTabs;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                UserGroupsFragment groupsFragment = new UserGroupsFragment();
//                return groupsFragment;
//            case 1:
//                SearchAppsFragment searchAppsFragment = new SearchAppsFragment();
//                return searchAppsFragment;
//            case 2:
//                SettingsFragment settingsFragment = new SettingsFragment();
//                return settingsFragment;
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Fragment fragment = (Fragment) super.instantiateItem(container, position);
//        registeredFragments.put(position, fragment);
//        return fragment;
//    }
//
//    @Override
//    public int getCount() {
//        return totalTabs;
//    }
//}
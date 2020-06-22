package com.example.projektwtm;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class BackPressImpl implements OnBackPressListener {
    private Fragment parent;

    public BackPressImpl(Fragment fragment) {
        this.parent = fragment;
    }

    @Override
    public boolean onBackPressed() {
        if (parent == null) {
            return false;
        }

        int child = parent.getChildFragmentManager().getBackStackEntryCount();

        if (child == 0) {
            return false;
        }
        else {
            FragmentManager fragmentManager = parent.getChildFragmentManager();
            OnBackPressListener fr = (OnBackPressListener) fragmentManager.getFragments().get(0);

            if (!fr.onBackPressed()) {
                fragmentManager.popBackStackImmediate();
            }

            return true;        }
    }
}

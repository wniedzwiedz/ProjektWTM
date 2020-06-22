package com.example.projektwtm;

import androidx.fragment.app.Fragment;

public class RootFragment extends Fragment implements OnBackPressListener {
    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }
}

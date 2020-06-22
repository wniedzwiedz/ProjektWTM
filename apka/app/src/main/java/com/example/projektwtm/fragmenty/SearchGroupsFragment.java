package com.example.projektwtm.fragmenty;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projektwtm.R;
import com.example.projektwtm.fragmenty.AddGroupFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchGroupsFragment extends Fragment {
    public static JSONObject group;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_groups, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = getView().findViewById(R.id.linearLayout4);
        FloatingActionButton button = getView().findViewById(R.id.fab);

        //gdy wyswietlamy dla grup uzytkownika - przycisk sie nie wyswietla/jest nieaktywny
        //gdy dla grup w ramach pakietu - wyswietla sie i sluzy do dodania grupy w ramach pakietu
        JSONArray groups = null;
//        try {
//            groups = SearchAppsFragment.app.getJSONArray("groups");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        if (groups != null) {
            for (int i = 0; i < groups.length(); i++) {
                TextView text = null;
                try {
                    text.setText(groups.getJSONObject(i).getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (text != null) {
                    linearLayout.addView(text);
                }
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                Fragment fragment = new AddGroupFragment();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.layout.fragment_search_groups, fragment);
                fragmentTransaction.commit();

                //                ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
//                viewPager.setCurrentItem(R.layout.fragment_search_apps);



//                Fragment newFragment = new AddGroupFragment();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//
//                transaction.replace(R.layout.fragment_search_groups,  newFragment);
//                transaction.addToBackStack(null);
//
//                transaction.commit();
            }
        });
    }
}

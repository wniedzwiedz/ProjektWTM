package com.example.projektwtm.fragmenty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.projektwtm.R;
import com.example.projektwtm.RootFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyGroupFragment extends RootFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_group, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //wyswietlenie odpowiednich danych o grupie
        TextView groupname = getView().findViewById(R.id.textView6);
        TextView packagename = getView().findViewById(R.id.textView24);
        TextView payment = getView().findViewById(R.id.textView26);
        TextView deadline = getView().findViewById(R.id.textView2);
        TextView bank = getView().findViewById(R.id.textView21);
        TextView membersCount = getView().findViewById(R.id.textView28);
        TextView info = getView().findViewById(R.id.textView23);

        ImageView image = getView().findViewById(R.id.imageView);

//        try {
//            groupname.setText(SearchGroupsFragment.group.getString("name"));
//            bank.setText(SearchGroupsFragment.group.getString("bankAccountNumber"));
//            info.setText(SearchGroupsFragment.group.getString("login"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        LinearLayout linearLayout = getView().findViewById(R.id.linearLayout6);
        JSONArray members = null;
//        try {
//            members = SearchGroupsFragment.group.getJSONArray("usersInGroups");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //dodanie czlonkow do linear layout
        if (members != null) {
            for (int i = 0; i < members.length(); i++) {
                JSONObject member = null;
                try {
                    member = members.getJSONObject(i).getJSONObject("user");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TextView textView = null;
                try {
                    textView.setText(member.getString("login"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                linearLayout.addView(textView);
            }
        }

        Button button = getView().findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //usuniecie uzytkownika z grupy
                //podmiana fragmentu na widok grup uzytkownika
            }
        });
    }

}

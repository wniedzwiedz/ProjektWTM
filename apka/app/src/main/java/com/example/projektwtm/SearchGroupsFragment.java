package com.example.projektwtm;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.core.Response;

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
            public void onClick(View v) {
                Fragment newFragment = new AddGroupFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.layout.activity_main_page, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });
    }
}

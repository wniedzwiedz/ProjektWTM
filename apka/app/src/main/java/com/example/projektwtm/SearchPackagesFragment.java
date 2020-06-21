package com.example.projektwtm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.Response;

public class SearchPackagesFragment extends Fragment {
    public static JSONObject pack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = getView().findViewById(R.id.linearLayout3);
        //dodanie do linearLayout pakietow

        //pobranie pakietow
        JSONArray packages = null;
        try {
            packages = SearchAppsFragment.app.getJSONArray("packages");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (packages != null) {
            for (int i = 0; i < packages.length(); i++) {
                TextView text = null;
                try {
                    text.setText(packages.getJSONObject(i).getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (text != null) {
                    linearLayout.addView(text);
                }
            }
        }

        //w przypadku widoku poziomego wyswietlenie na polowie ekranu apps, na drugiej - packages i odpowiednia obsluga tego  - podmiana fragmentu na widok grup w ramach pakietu

        return inflater.inflate(R.layout.fragment_search_packages, container, false);
    }
}

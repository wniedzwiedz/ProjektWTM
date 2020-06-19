package com.example.projektwtm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.Response;

public class SearchAppsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/FindCo/api/apps/");
        Response response = target.request().get();
        if (response.hasEntity()) {
            JSONArray groupsOfUser = (JSONArray) response.getEntity();
            for (int i = 0; i < groupsOfUser.length(); ++i) {
                try {
                    JSONObject obj = groupsOfUser.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //zrobienie Stringa typu app name
                //dodanie do linear layout
                //obsluga przycisniecie konkretnej grupy
            }
        }

        //w przypadku widoku poziomego wyswietlenie na polowie ekranu apps, na drugiej - packages i odpowiednia obsluga tego  - podmiana fragmentu na widok pakietow w ramach aplikacji

        return inflater.inflate(R.layout.fragment_search_apps, container, false);
    }
}

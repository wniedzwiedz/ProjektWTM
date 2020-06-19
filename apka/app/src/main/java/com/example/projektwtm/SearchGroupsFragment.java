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

public class SearchGroupsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //w przypadku, gdy uzywamy tego do wyswietlenia grup uzytkownika
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/FindCo/api/users/"); //+user.getId() + "/groups/" ???
        Response response = target.request().get();
        if (response.hasEntity()) {
            JSONArray groupsOfUser = (JSONArray) response.getEntity();
            for (int i = 0; i < groupsOfUser.length(); ++i) {
                try {
                    JSONObject obj = groupsOfUser.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //zrobienie Stringa typu app name + package
                //dodanie do linear layout
                //obsluga przycisniecie konkretnej grupy
            }
        }
        response.close();

        //w przypadku, gdy uzywamy tego do wyswietlenia dostepnych grup w ramach danego pakietu
        ResteasyClient client1 = new ResteasyClientBuilder().build();
        ResteasyWebTarget target1 = client1.target("http://localhost:8080/FindCo/api/apps/"); //+app.getId() + "/packages/" + package.getId() + "/groups/ ???
        Response response1 = target1.request().get();
        if (response.hasEntity()) {
            JSONArray groupsOfUser = (JSONArray) response.getEntity();
            for (int i = 0; i < groupsOfUser.length(); ++i) {
                try {
                    JSONObject obj = groupsOfUser.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //zrobienie Stringa typu app name + package + kwota + termin platnosci
                //dodanie do linear layout
                //nie wyswietlamy grup, ktorych lcizba uzytkownikow jest pelna
                //obsluga przycisniecia konkretnej grupy - podmiana na widok szczegolowy grupy, do ktorej sie nie nalezy
            }
        }
        response.close();


        return inflater.inflate(R.layout.fragment_search_groups, container, false);
    }
}

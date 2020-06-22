package com.example.projektwtm.fragmenty;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projektwtm.Constants;
import com.example.projektwtm.R;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.core.Response;

public class SearchPackagesFragment extends Fragment {
    public static int packID;
    LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_packages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        linearLayout = getView().findViewById(R.id.linearLayout3);

        //dodanie do linearLayout pakietow
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/packages?application=" + SearchAppsFragment.appID);
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();

                    try {

                        if (myConnection.getResponseCode() == 200) {

                            JSONArray jsonArray = null;
                            try {
                                BufferedReader br = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));

                                StringBuilder response = new StringBuilder();
                                String responseLine = null;
                                while ((responseLine = br.readLine()) != null) {
                                    response.append(responseLine.trim());
                                }
                                jsonArray = new JSONArray(response.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (jsonArray != null) {
                                final JSONArray finalJsonArray = jsonArray;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (int i = 0; i < finalJsonArray.length(); i++) {
                                            JSONObject jsonObject = null;
                                            try {
                                                jsonObject = finalJsonArray.getJSONObject(i);
                                                TextView text = new TextView(getContext());
                                                text.setText(jsonObject.getString("name"));
                                                text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);
                                                text.setTextColor(Color.BLACK);
                                                text.setGravity(Gravity.CENTER);
                                                final JSONObject finalJsonObject = jsonObject;
                                                text.setOnClickListener(new View.OnClickListener() {
                                                    public void onClick(View v) {
                                                        try {
                                                            packID = finalJsonObject.getInt("id");
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        Fragment newFragment = new SearchGroupsFragment();
                                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                        transaction.replace(((ViewGroup)getView().getParent()).getId(), newFragment);
                                                        transaction.addToBackStack(null);
                                                        transaction.commit();

                                                    }
                                                });
                                                linearLayout.addView(text);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        //w przypadku widoku poziomego wyswietlenie na polowie ekranu apps, na drugiej - packages i odpowiednia obsluga tego  - podmiana fragmentu na widok grup w ramach pakietu

    }
}

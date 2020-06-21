package com.example.projektwtm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.core.Response;

public class SearchAppsFragment extends Fragment {
    public static JSONObject app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        final LinearLayout linearLayout = getView().findViewById(R.id.linearLayout5);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/applications");
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("GET");
                    myConnection.setDoOutput(true);

                    try {
                        TextView error = getView().findViewById(R.id.textView17);

                        if (myConnection.getResponseCode() == 200) {

                            InputStream responseBody = myConnection.getInputStream();
                            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

                            JSONArray jsonArray = new JSONArray(responseBodyReader.toString());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                TextView text = null;
                                text.setText(jsonObject.getString("name"));
//                                linearLayout.addView(text);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        //w przypadku widoku poziomego wyswietlenie na polowie ekranu apps, na drugiej - packages i odpowiednia obsluga tego  - podmiana fragmentu na widok pakietow w ramach aplikacji
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            
        }

        return inflater.inflate(R.layout.fragment_search_apps, container, false);
    }
}

package com.example.projektwtm.fragmenty;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.projektwtm.Constants;
import com.example.projektwtm.R;
import com.example.projektwtm.RootFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpecificUserFragment extends RootFragment {

    TextView email;
    TextView firstname;
    TextView surname;
    public static int userID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specific_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        email = getView().findViewById(R.id.textView33);
        firstname = getView().findViewById(R.id.textView36);
        surname = getView().findViewById(R.id.textView38);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/users/" + userID);
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();

                    if (myConnection.getResponseCode() == 200) {
                        JSONArray jsonArray = null;
                        BufferedReader br = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        jsonArray = new JSONArray(response.toString());

                        if (jsonArray != null) {
                            final JSONArray finalJsonArray = jsonArray;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < finalJsonArray.length(); i++) {
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = finalJsonArray.getJSONObject(i);
                                            email.setText(jsonObject.getString("email"));
                                            firstname.setText(jsonObject.getString("firstName"));
                                            surname.setText(jsonObject.getString("lastName"));
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}

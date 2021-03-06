package com.example.projektwtm.fragmenty;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projektwtm.Constants;
import com.example.projektwtm.DBHelper;
import com.example.projektwtm.R;
import com.example.projektwtm.RootFragment;
import com.example.projektwtm.modele.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class UserGroupsFragment extends RootFragment {

    public static int groupID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_groups, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final LinearLayout linearLayout = getView().findViewById(R.id.linearLayout44);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DBHelper dbHelper = new DBHelper(getContext());
                    List<User> user = dbHelper.getUser();
                    final int id = user.get(0).getId();

                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/groups?user=" + id + "&userConfirmed=true");
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
                                            TextView text = new TextView(getContext());
                                            text.setText(jsonObject.getString("name"));
                                            text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);
                                            text.setTextColor(Color.BLACK);
                                            text.setGravity(Gravity.CENTER);
                                            final JSONObject finalJsonObject = jsonObject;
                                            text.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View v) {
                                                    try {
                                                        groupID = finalJsonObject.getInt("id");
                                                        Fragment newFragment;
                                                        if (finalJsonObject.getInt("ownerId") == id) {
                                                            newFragment = new YourGroupFragment();
                                                        } else {
                                                            newFragment = new MyGroupFragment();
                                                        }
                                                        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                                        transaction.replace(R.id.fragment_mainLayout, newFragment);
                                                        transaction.addToBackStack(null);
                                                        transaction.commit();
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
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

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}

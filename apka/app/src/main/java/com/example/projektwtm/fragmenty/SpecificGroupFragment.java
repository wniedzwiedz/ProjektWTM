package com.example.projektwtm.fragmenty;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecificGroupFragment extends RootFragment {

    TextView groupName;
    TextView payment;
    TextView maxNumberOfMembers;
    LinearLayout linearLayout;
    int usersCount = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specific_group, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        groupName = getView().findViewById(R.id.textView7);
        payment = getView().findViewById(R.id.textView26);
        maxNumberOfMembers = getView().findViewById(R.id.textView28);
        linearLayout = getView().findViewById(R.id.linearLayout6);

        ImageView image = getView().findViewById(R.id.imageView);

        Button button = getView().findViewById(R.id.button8);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                joinToGroup();
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/groups/" + SearchGroupsFragment.groupID);
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
                                            groupName.setText(jsonObject.getString("name"));
                                            payment.setText(jsonObject.getString("paymentInfo"));
                                            maxNumberOfMembers.setText(jsonObject.getString("maxNumberOfMembers"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });

                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //get users
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/users?group=" + SearchGroupsFragment.groupID);
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
                                            final TextView text = new TextView(getContext());
                                            String txt = jsonObject.getString("firstName") + " " + jsonObject.getString("lastName");
                                            text.setText(txt);
                                            text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                            text.setTextColor(Color.BLACK);
                                            text.setGravity(Gravity.CENTER);
                                            linearLayout.addView(text);
                                            usersCount++;
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });

                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void joinToGroup() {
        int max = Integer.parseInt((String) maxNumberOfMembers.getText());
        if (max > usersCount) {
            AsyncTask.execute(new Runnable() {
                @SuppressLint("ResourceType")
                @Override
                public void run() {
                    try {
                        DBHelper dbHelper = new DBHelper(getContext());
                        List<User> users = new ArrayList<>();
                        users.addAll(dbHelper.getUser());
                        int id = users.get(0).getId();

                        URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/groups/" + SearchGroupsFragment.groupID + "/" + id);
                        HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                        myConnection.setRequestMethod("POST");
                        myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                        myConnection.setRequestProperty("Accept", "application/json");
                        myConnection.setDoOutput(true);

                        if (myConnection.getResponseCode() == 200) {
                            Fragment newFragment = new SearchAppsFragment();
                            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                            transaction.addToBackStack(null);
                            transaction.replace(R.id.fragment_mainLayout, newFragment);
                            transaction.commit();

                        }


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }


}

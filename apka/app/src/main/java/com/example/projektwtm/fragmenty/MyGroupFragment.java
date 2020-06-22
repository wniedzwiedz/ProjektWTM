package com.example.projektwtm.fragmenty;

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

public class MyGroupFragment extends RootFragment {

    TextView groupName;
    TextView payment;
    TextView bankAccountNumber;
    TextView maxNumberOfMembers;
    TextView information;
    LinearLayout linearLayout;
    ImageView image;
    int packageId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_group, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //wyswietlenie odpowiednich danych o grupie
        groupName = getView().findViewById(R.id.textView7);
        payment = getView().findViewById(R.id.textView2);
        bankAccountNumber = getView().findViewById(R.id.textView21);
        maxNumberOfMembers = getView().findViewById(R.id.textView28);
        information = getView().findViewById(R.id.textView23);
        linearLayout = getView().findViewById(R.id.linearLayout6);

        image = getView().findViewById(R.id.imageView);

        Button button = getView().findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                deleteUser();
            }
        });


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/groups/" + UserGroupsFragment.groupID);
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
                                            information.setText(jsonObject.getString("information"));
                                            maxNumberOfMembers.setText(jsonObject.getString("maxNumberOfMembers"));
                                            bankAccountNumber.setText(jsonObject.getString("bankAccountNumber"));
                                            packageId = jsonObject.getInt("packageId");
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
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/users?group=" + UserGroupsFragment.groupID);
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
                                            final JSONObject finalJsonObject = jsonObject;
                                            text.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View v) {
                                                    try {
                                                        SpecificUserFragment.userID = finalJsonObject.getInt("id");
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Fragment newFragment = new SpecificUserFragment();
                                                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                                    transaction.addToBackStack(null);
                                                    transaction.replace(R.id.fragment_mainLayout , newFragment);
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

    private void deleteUser() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DBHelper dbHelper = new DBHelper(getContext());
                    final List<User> users = new ArrayList<>();
                    try {
                        users.addAll(dbHelper.getUser());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    User user = users.get(0);

                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/groups/" + UserGroupsFragment.groupID + "/" + user.getId());
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("DELETE");

                    if (myConnection.getResponseCode() == 200) {
                        Fragment newFragment = new UserGroupsFragment();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                        transaction.replace(R.id.fragment_mainLayout, newFragment);
                        transaction.addToBackStack(null);

                        transaction.commit();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

package com.example.projektwtm.fragmenty;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projektwtm.Constants;
import com.example.projektwtm.R;
import com.example.projektwtm.fragmenty.AddGroupFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchGroupsFragment extends Fragment {
    public static int groupID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_groups, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final LinearLayout linearLayout = getView().findViewById(R.id.linearLayout4);
        FloatingActionButton button = getView().findViewById(R.id.fab);

        //gdy wyswietlamy dla grup uzytkownika - przycisk sie nie wyswietla/jest nieaktywny
        //gdy dla grup w ramach pakietu - wyswietla sie i sluzy do dodania grupy w ramach pakietu

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddGroupFragment();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.layout.fragment_search_groups, fragment);
                fragmentTransaction.commit();
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/groups?package=" + SearchPackagesFragment.packID);
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
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Fragment newFragment = new SearchGroupsFragment();
                                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                    transaction.replace(((ViewGroup) getView().getParent()).getId(), newFragment);
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

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

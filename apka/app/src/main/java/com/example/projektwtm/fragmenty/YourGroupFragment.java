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
import com.example.projektwtm.R;
import com.example.projektwtm.RootFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class YourGroupFragment extends RootFragment {

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
        return inflater.inflate(R.layout.fragment_your_group, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //uzupelnienie danych grupy
        groupName = getView().findViewById(R.id.textView242);
        payment = getView().findViewById(R.id.editText18);
        bankAccountNumber = getView().findViewById(R.id.editText19);
        maxNumberOfMembers = getView().findViewById(R.id.textView28);
        information = getView().findViewById(R.id.editText20);
        linearLayout = getView().findViewById(R.id.linearLayout2);
        image = getView().findViewById(R.id.imageView);

        Button button = getView().findViewById(R.id.button12);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String paymentString = payment.getText().toString();
                final String bankAccountNumberString = bankAccountNumber.getText().toString();
                final String informationString = information.getText().toString();

                changeGroup(paymentString, bankAccountNumberString, informationString);
                TextView error = getView().findViewById(R.id.textView50);
                error.setText("");

            }
        });

        Button button1 = getView().findViewById(R.id.button5);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteGroup();
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
                                                    transaction.replace(R.id.fragment_mainLayout, newFragment);
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


    public void changeGroup(final String paymentDeadline, final String accountNumber,
                            final String info) {
        TextView error = getView().findViewById(R.id.textView50);

        if (paymentDeadline.equals("") || accountNumber.equals("") || info.equals("")) {
            error.setText("Empty data");
            return;
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/groups/" + UserGroupsFragment.groupID);
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("PUT");
                    myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                    myConnection.setRequestProperty("Accept", "application/json");
                    myConnection.setDoOutput(true);

                    String jsonInputString = "{\"bankAccountNumber\" : \"" + accountNumber + "\", " +
                            "\"paymentInfo\" : \"" + paymentDeadline + "\" ," +
                            "\"information\" : \"" + info + "\" }";


                    OutputStream os = myConnection.getOutputStream();
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                    os.close();


                    BufferedReader bufferedReader = null;

                    TextView error = getView().findViewById(R.id.textView50);

                    if (myConnection.getResponseCode() == 200) {
                        bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = bufferedReader.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        error.setText("Data changed.");

                    } else if (myConnection.getResponseCode() == 400) {
                        error.setText("Wrong number format.");
                    } else {
                        error.setText("Server error.");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void deleteGroup() {
        TextView error = getView().findViewById(R.id.textView50);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/groups/" + UserGroupsFragment.groupID);
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("DELETE");
                    myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                    myConnection.setRequestProperty("Accept", "application/json");
                    myConnection.setDoOutput(true);

                    BufferedReader bufferedReader = null;
                    try {
                        TextView error = getView().findViewById(R.id.textView50);

                        if (myConnection.getResponseCode() == 200) {
                            bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = bufferedReader.readLine()) != null) {
                                response.append(responseLine.trim());
                            }
                            System.out.println(response.toString());

                            Fragment newFragment = new UserGroupsFragment();
                            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_mainLayout, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

                            error.setText("Data changed.");
                        } else if (myConnection.getResponseCode() == 400) {
                            error.setText("Wrong number format.");
                        } else {
                            error.setText("Server error.");
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

    }


}

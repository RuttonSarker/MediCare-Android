package com.redbox.medicare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.redbox.medicare.UtilsService.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.NumericShaper;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DoctorProfile extends AppCompatActivity {

    private EditText contact,specialist,degree,medical,fees,gender;
    private TextView id,name,bmdc;
    private ImageView addImage,edit;
    private String token;
    private SharedPreference sharedPreference;
    private String myID,myName,myContact,myBmdc,myDegree,myMedical,myFees,myGender,doctorSpecialist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        id = findViewById(R.id.doctorID);
        name = findViewById(R.id.doctorName);
        bmdc = findViewById(R.id.bmdcID);
        contact = findViewById(R.id.contact);
        specialist = findViewById(R.id.specialist);
        degree = findViewById(R.id.degree);
        medical = findViewById(R.id.medical);
        fees = findViewById(R.id.consultantFees);
        gender = findViewById(R.id.gender);
        edit = findViewById(R.id.editButton);

        sharedPreference = new SharedPreference(this);
        token = sharedPreference.getValueString("token");

        retrieveProfile();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(DoctorProfile.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_edit_doctor_dialog,null);

                TextView id = (TextView)mView.findViewById(R.id.doctorID);
                EditText editName = (EditText)mView.findViewById(R.id.editName);
                EditText editDegree = (EditText)mView.findViewById(R.id.editDegree);
                EditText editMedical = (EditText)mView.findViewById(R.id.editMedical);
                EditText editFees = (EditText)mView.findViewById(R.id.editVisit);


                id.setText(myID);
                editName.setText(myName);
                editDegree.setText(myDegree);
                editMedical.setText(myMedical);
                editFees.setText(myFees);


                Button cancel = (Button)mView.findViewById(R.id.cancel);
                Button save = (Button)mView.findViewById(R.id.save);
                alert.setView(mView);

                final AlertDialog alertDialog = alert.create();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String updateID = id.getText().toString();
                        String updateName = editName.getText().toString();
                        String updateDegree = editDegree.getText().toString();
                        String updateMedical = editMedical.getText().toString();
                        String updateFees = editFees.getText().toString();
                        updateDoctor(updateID,updateName,updateDegree,updateMedical,updateFees);

                    }

                    private void updateDoctor(String updateID, String updateName, String updateDegree, String updateMedical, String updateFees) {

                        final HashMap<String, String> body = new HashMap<>();

                       body.put("username", updateName);
                       body.put("degree",updateDegree);
                       body.put("medical",updateMedical);
                       body.put("visit", updateFees);

                        String apiKey = "http://192.168.31.122:3000/api/medicare/auth/update/"+ updateID;

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                                apiKey, new JSONObject(body), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getBoolean("success")) {

                                        retrieveProfile();
                                        Toast.makeText(DoctorProfile.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                        JSONObject obj = new JSONObject(res);
                                        Toast.makeText(DoctorProfile.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

                                    } catch (JSONException | UnsupportedEncodingException je) {
                                        je.printStackTrace();


                                    }
                                }
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<>();
                                headers.put("Content-Type", "application/json");
                                headers.put("Authorization", token);
                                return headers;
                            }
                        };

                        // set retry policy
                        int socketTime = 3000;
                        RetryPolicy policy = new DefaultRetryPolicy(socketTime,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        jsonObjectRequest.setRetryPolicy(policy);

                        // request add
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(jsonObjectRequest);

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }

        });

    }

    private void retrieveProfile() {

        String Url = "http://192.168.31.122:3000/api/medicare/auth/user";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getBoolean("success")) {

                        JSONObject jsonObject = response.getJSONObject("user");

                        myID = jsonObject.getString("_id");
                        myName = jsonObject.getString("username");
                        myContact = jsonObject.getString("mobile");
                        myBmdc = jsonObject.getString("bmdcNo");
                        doctorSpecialist = jsonObject.getString("department");
                        myDegree = jsonObject.getString("degree");
                        myMedical = jsonObject.getString("medical");
                        myFees = jsonObject.getString("visit");
                        myGender = jsonObject.getString("gender");

                        id.setText(myID);
                        name.setText(myName);
                        contact.setText(myContact);
                        bmdc.setText(myBmdc);
                        specialist.setText(doctorSpecialist);
                        degree.setText(myDegree);
                        medical.setText(myMedical);
                        fees.setText(myFees);
                        gender.setText(myGender);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(DoctorProfile.this, error.toString(), Toast.LENGTH_SHORT).show();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {

                    try {

                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Toast.makeText(DoctorProfile.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

                    } catch (JSONException | UnsupportedEncodingException je) {
                        je.printStackTrace();

                    }
                }
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };

        // set retry policy
        int socketTime = 3000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        // request add
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }



}

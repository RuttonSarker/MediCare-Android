package com.redbox.medicare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PatientProfile extends AppCompatActivity {

    private ImageView addImage,edit;
    private EditText name,mobile,age,gender;
    private TextView id;
    private String token;
    private SharedPreference sharedPreference;
    private String myID,myName,myAge,myGender,myContact = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        id = findViewById(R.id.id);
        addImage = findViewById(R.id.user);
        edit = findViewById(R.id.edit);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);

        sharedPreference = new SharedPreference(this);
        token = sharedPreference.getValueString("token");

        retrieveUser();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(PatientProfile.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_edit_patient_dialog,null);

                EditText editName = (EditText)mView.findViewById(R.id.editName);
                EditText editAge = (EditText)mView.findViewById(R.id.editAge);
                EditText editGender = (EditText)mView.findViewById(R.id.editGender);
                TextView id = (TextView)mView.findViewById(R.id.patientID);

                editName.setText(myName);
                editAge.setText(myAge);
                editGender.setText(myGender);
                id.setText(myID);

                Button cancel = (Button)mView.findViewById(R.id.cancel);
                Button save = (Button)mView.findViewById(R.id.save);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String updateName = editName.getText().toString();
                        String updateAge = editAge.getText().toString();
                        String updateID = id.getText().toString();
                        String updateGender = editGender.getText().toString();


                        updateDoctor(updateID,updateName,updateAge,updateGender);

                    }

                    private void updateDoctor(String updateID, String updateName, String updateFees, String updateGender) {

                        final HashMap<String, String> body = new HashMap<>();

                        body.put("username", updateName);
                        body.put("age", updateFees);
                        body.put("gender",updateGender);

                        String apiKey = "http://192.168.31.122:3000/api/medicare/auth/update/"+ updateID;

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                                apiKey, new JSONObject(body), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getBoolean("success")) {

                                        retrieveUser();
                                        Toast.makeText(PatientProfile.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(PatientProfile.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

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

    private void retrieveUser() {

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
                        myGender = jsonObject.getString("gender");
                        myAge = jsonObject.getString("age");

                        id.setText(myID);
                        name.setText(myName);
                        mobile.setText(myContact);
                        age.setText(myAge);
                        gender.setText(myGender);

                        Intent patient = new Intent(PatientProfile.this,MainActivity.class);
                        patient.putExtra("username",myName);
                        patient.putExtra("mobile",myContact);
                        patient.putExtra("age",myAge);

                        }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PatientProfile.this, error.toString(), Toast.LENGTH_SHORT).show();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {

                    try {

                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Toast.makeText(PatientProfile.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

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

package com.redbox.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Spinner;
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
import com.redbox.medicare.UtilsService.UtilService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText mobileNumber, password;
    private Button login;
    private TextView signup;
    private Spinner user;
    UtilService utilService;
    private String mobile, pass, userType;
    CharSequence[] UserType;
    SharedPreference sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobileNumber = findViewById(R.id.mobileNumber);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        user = findViewById(R.id.user);
        utilService = new UtilService();
        sharedPreference = new SharedPreference(this);

        UserType = getResources().getStringArray(R.array.User_Type);
        ArrayAdapter myUserType = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, UserType);
        myUserType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user.setAdapter(myUserType);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilService.hideKeyboard(view, LoginActivity.this);
                mobile = mobileNumber.getText().toString();
                pass = password.getText().toString();
                userType = user.getSelectedItem().toString();
                if (validate(view)) {
                    loginUser(view);
                }

            }
        });
    }

    private void loginUser(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();

        final HashMap<String, String> params = new HashMap<>();
        params.put("usertype", userType);
        params.put("mobile", mobile);
        params.put("password", pass);


        String apiKey = "http://192.168.31.122:3000/api/medicare/auth/login";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                apiKey, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        String token = response.getString("token");
                        sharedPreference.setValueString("token", token);
                        String userType = response.getString("usertype");
                        sharedPreference.setUserString("usertype", userType);

                        if (userType.equals("Doctor")) {
                            Intent intent = new Intent(LoginActivity.this, DoctorActivity.class);
                            startActivity(intent);

                        } else if (userType.equals("Patient")) {
                            startActivity(new Intent(LoginActivity.this, PatientActivity.class));

                        }
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
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
                        Toast.makeText(LoginActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } catch (JSONException | UnsupportedEncodingException je) {
                        je.printStackTrace();
                        progressDialog.dismiss();

                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return params;
            }
        };

        // set retry policy
        int socketTime = 3000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        // request add
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    public boolean validate(View view) {
        boolean isValid;

        if (!TextUtils.isEmpty(mobile)) {

            if (!TextUtils.isEmpty(pass)) {
                isValid = true;
            } else {
                utilService.showSnackBar(view, "Password Empty! Enter Password");
                isValid = false;
            }
        } else {
            utilService.showSnackBar(view, "Mobile No. Empty! Enter Mobile No.");
            isValid = false;
        }

        return isValid;
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences medicare = getSharedPreferences("medicare_user", MODE_PRIVATE);
        String user = sharedPreference.getUserString("usertype");

        if (medicare.contains("token")) {

            if (user.equals("Doctor")) {
                startActivity(new Intent(LoginActivity.this, DoctorActivity.class));
                finish();

            }else if(user.equals("Patient"))
                startActivity(new Intent(LoginActivity.this, PatientActivity.class));
                finish();
        }

    }

}



package com.redbox.medicare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.redbox.medicare.UtilsService.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindDoctor extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView empty;
    ProgressBar progressBar;
    String token;
    DoctorAdapter doctorAdapter;
    SharedPreference sharedPreference;
    ArrayList<Doctor> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        recyclerView = findViewById(R.id.recycle_view);
        empty = findViewById(R.id.empty);
        progressBar = findViewById(R.id.progressBar);
        sharedPreference = new SharedPreference(this);
        token = sharedPreference.getValueString("token");
        retrieveDoctor();

    }

    private void retrieveDoctor() {
        Intent dep = getIntent();
        String department = dep.getStringExtra("department");
        arrayList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        String Url = "http://192.168.31.122:3000/api/medicare/auth/" + department;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {

                        JSONArray jsonArray = response.getJSONArray("users");

                        for (int i = 0; i < jsonArray.length(); i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Doctor doctor = new Doctor(
                                    jsonObject.getString("_id"),
                                    jsonObject.getString("username"),
                                    jsonObject.getString("department"),
                                    jsonObject.getString("degree"),
                                    jsonObject.getString("medical"),
                                    jsonObject.getString("visit")

                            );

                            arrayList.add(doctor);
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setHasFixedSize(true);
                        doctorAdapter = new DoctorAdapter(getApplicationContext(),arrayList);
                        recyclerView.setAdapter(doctorAdapter);

                    }
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FindDoctor.this, error.toString(), Toast.LENGTH_SHORT).show();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Toast.makeText(FindDoctor.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    } catch (JSONException | UnsupportedEncodingException je) {
                        je.printStackTrace();
                        progressBar.setVisibility(View.GONE);

                    }
                }
                    progressBar.setVisibility(View.GONE);
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
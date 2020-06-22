package com.projectrani.productapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://sfa-api.pti-cosmetics.com/product?limit=20";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ProductList> productLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productLists = new ArrayList<>();

        loadRecyclerViewData();

        Button btnQuiz = findViewById(R.id.btn_quiz);
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }

    private void startQuiz() {
        Intent intent = new Intent(MainActivity.this, StartQuizActivity.class);
        startActivity(intent);
    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(("Loading data.."));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {

                            JSONArray array = new JSONArray(s);

                            for(int i = 0; i<array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                ProductList product = new ProductList(
                                        o.getString("name"),
                                        o.getString("brand"),
                                        o.getString("default_code"),
                                        o.getString("product_id"),
                                        o.getString("gt_price"),
                                        o.getString("mt_price"),
                                        o.getString("gt_batam"),
                                        o.getString("mt_batam"),
                                        o.getString("barcode"),
                                        o.getString("unit"),
                                        o.getString("product_rule")
                                        );
                                productLists.add(product);
                            }

                            adapter = new MyAdapter(productLists, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
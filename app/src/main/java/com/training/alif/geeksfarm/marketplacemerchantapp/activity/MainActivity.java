package com.training.alif.geeksfarm.marketplacemerchantapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.training.alif.geeksfarm.marketplacemerchantapp.BuildConfig;
import com.training.alif.geeksfarm.marketplacemerchantapp.R;
import com.training.alif.geeksfarm.marketplacemerchantapp.adapter.MainAdapter;
import com.training.alif.geeksfarm.marketplacemerchantapp.entity.Product;
import com.training.alif.geeksfarm.marketplacemerchantapp.entity.daftarProduk;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvMerchant;
    private MainAdapter mainAdapter;
    private RequestQueue rq;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton floatingActionButton;
    String basePoint = BuildConfig.URL;//Ini merupakan method GET dalam web
    String endPoint = BuildConfig.PRODUCTS;
    String url = basePoint+endPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        volley();
        setFloatingActionButton();

        setSwipeRefreshLayout();
    }
    private void init(){
        rvMerchant = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.Swipe_refresh);
        floatingActionButton = findViewById(R.id.fab);
        mainAdapter = new MainAdapter();
        rvMerchant.setAdapter(mainAdapter);
        rvMerchant.setLayoutManager(new GridLayoutManager(this,2));
        rq = Volley.newRequestQueue(getApplicationContext());
    }
    private void volley(){
        StringRequest listProductReq;
        listProductReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson GSONData = new Gson();
                daftarProduk lp = GSONData.fromJson(response, daftarProduk.class);
                mainAdapter.setProducts(lp.getProducts());
                //Toast.makeText(MainActivity.this,mainAdapter.getItemCount(),Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error",error.getMessage());
            }
        });
        rq.add(listProductReq);
        swipeRefreshLayout.setRefreshing(false);
    }
    private void setSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                volley();
            }
        });
    }
    private void setFloatingActionButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addDataVolley();
            }
        });
    }
    private void addDataVolley(){
        Intent i = new Intent(getApplicationContext(), AddActivity.class);
        startActivity(i);
    }
}

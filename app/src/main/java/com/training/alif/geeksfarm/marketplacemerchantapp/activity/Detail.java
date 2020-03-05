package com.training.alif.geeksfarm.marketplacemerchantapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.training.alif.geeksfarm.marketplacemerchantapp.BuildConfig;
import com.training.alif.geeksfarm.marketplacemerchantapp.R;
import com.training.alif.geeksfarm.marketplacemerchantapp.entity.Product;

public class Detail extends AppCompatActivity {
    public static String EXTRA_DATA = "EXTRA PRODUCT DATA";
    public static String EXTRA_ID = "Update Data";
    private ImageView image;
    private TextView name, qty, category, merchant,price,desc;
    private ProgressBar pb;
    private Button buttonEdit,buttonDelete;
    private String baseUrl = BuildConfig.URL;
    private String endpoint = BuildConfig.STORAGE;
    private RequestQueue requestQueue;
    Product product ;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        product = getIntent().getParcelableExtra(EXTRA_DATA);
        init();
        requestQueue = Volley.newRequestQueue(this);
        if(product != null){
            Toast.makeText(this, "ada data", Toast.LENGTH_SHORT).show();
            String _desc = product.getDesc();
            String names = product.getName();
            int Qty = product.getQty();
            String _qty = Integer.toString(Qty);
            String cat = product.getCat().getName();
            String _merchant = product.getMerch().getName();
            int _price = product.getPrice();
            String harga = Integer.toString(_price);
            String url = baseUrl+endpoint+product.getImages();

            Toast.makeText(Detail.this,product.getImages(),Toast.LENGTH_SHORT).show();
            Glide.with(this)
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(Detail.this,"gagal memuat gambar",Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(Detail.this,"berhasil memuat gambar",Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                    .into(image);

            name.setText(names);
            qty.setText(_qty);
            category.setText(cat);
            merchant.setText(_merchant);
            price.setText("Rp. "+ harga);
            desc.setText(_desc);
            OnClickDelete();
            OnEditData();
        }
    }

    public void init(){
        image = findViewById(R.id.img_detail);
        name = findViewById(R.id.tv_name);
        qty = findViewById(R.id.tv_qty);
        category = findViewById(R.id.tv_prod);
        merchant = findViewById(R.id.tv_merch);
        price = findViewById(R.id.price_detail);
        pb = findViewById(R.id.progressbar);
        desc = findViewById(R.id.tv_desc_detail);
        buttonEdit = findViewById(R.id.button_edit);
        buttonDelete = findViewById(R.id.button_delete);
    }
    private void OnClickDelete(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, baseUrl + "/api/product/" + product.getId() + "/delete", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Detail.this,"Data has been deleted",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(Detail.this,"Data isn't deleted yet",Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequest);
            }
        });
    }
    private void OnEditData(){
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddActivity.class);
                i.putExtra(EXTRA_ID,product);
                startActivity(i);
            }
        });
    }
}

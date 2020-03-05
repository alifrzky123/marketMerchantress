package com.training.alif.geeksfarm.marketplacemerchantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.training.alif.geeksfarm.marketplacemerchantapp.activity.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btn_sign_in)
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_sign_up)
    public void gotoRegisterAct(){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}

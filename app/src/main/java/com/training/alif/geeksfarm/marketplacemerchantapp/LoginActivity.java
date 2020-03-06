package com.training.alif.geeksfarm.marketplacemerchantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.training.alif.geeksfarm.marketplacemerchantapp.Utils.TokenManager;
import com.training.alif.geeksfarm.marketplacemerchantapp.Utils.network.VolleySingleton;
import com.training.alif.geeksfarm.marketplacemerchantapp.activity.RegisterActivity;
import com.training.alif.geeksfarm.marketplacemerchantapp.entity.AccessToken;
import com.training.alif.geeksfarm.marketplacemerchantapp.entity.RegisterErrorResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.training.alif.geeksfarm.marketplacemerchantapp.Utils.TokenManager.getInstance;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_log_email) EditText editTextEmail;
    @BindView(R.id.et_log_password) EditText editTextPassword;

    @BindView(R.id.btn_sign_in)
    Button btnLogin;
    private Context context;
    final String EMAIL = "email";
    final String PASSWORD = "password";
    //final String IS_MERCHANT = "is_merchant";

    AccessToken accessToken;

    String email, password;
    //int isMerchant = 1;

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

    @OnClick(R.id.btn_sign_in)
    public void register(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                if(isValidInput()){
                    Volley();
                }
                // connected to wifi
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to mobile data
                if(isValidInput()){
                    Volley();
                }
            }
        } else {
            // not connected to the internet
            Toast.makeText(getApplicationContext(),"Not Connected To Network",Toast.LENGTH_LONG).show();
        }

    }

    public void Volley(){
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        String url = "http://210.210.154.65:4444/api/auth/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            accessToken = new Gson().fromJson(response, AccessToken.class);
            TokenManager.getInstance(getSharedPreferences("pref", MODE_PRIVATE)).getToken(accessToken);
            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),"Login Berhasil",Toast.LENGTH_SHORT).show();
        }, error -> {
            String statuscode = String.valueOf(error.networkResponse.statusCode);
            String body;
            try {
                body = new String(error.networkResponse.data, "UTF-8");
                System.out.println(body);
                JSONObject res = new JSONObject(body);

                RegisterErrorResponse errorResponse = new Gson().fromJson(res.getJSONObject("error").toString(),RegisterErrorResponse.class);

                if(errorResponse.getEmailError().size() > 0){
                    if(errorResponse.getEmailError().get(0) != null){
                        editTextEmail.setError(errorResponse.getEmailError().get(0));
                    }
                }
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),statuscode,Toast.LENGTH_SHORT).show();
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new Hashtable<>();

                params.put(EMAIL,email);
                params.put(PASSWORD,password);


                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    private boolean isValidInput(){
        boolean isValid = true;

        if(editTextEmail.getText().toString().isEmpty()){
            editTextEmail.setError("email name cannot be empty");
            isValid = false;
        }else if(!editTextEmail.getText().toString().contains("@")){
            editTextEmail.setError("must be a valid email");
            isValid = false;
        }

        if(editTextPassword.getText().toString().isEmpty()){
            editTextPassword.setError("Password cannot be empty");
            isValid = false;
        }
        else if(editTextPassword.getText().toString().length() < 8){
            editTextPassword.setError("Password must be 8 or more character");
            isValid = false;
        }

        return isValid;
    }
}


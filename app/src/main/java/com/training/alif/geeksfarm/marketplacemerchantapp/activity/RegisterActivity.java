package com.training.alif.geeksfarm.marketplacemerchantapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.training.alif.geeksfarm.marketplacemerchantapp.R;
import com.training.alif.geeksfarm.marketplacemerchantapp.Utils.TokenManager;
import com.training.alif.geeksfarm.marketplacemerchantapp.entity.AccessToken;
import com.training.alif.geeksfarm.marketplacemerchantapp.entity.RegisterErrorResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    private EditText etFirstName,etEmail,etPass,etConfPass,etMerchName,etLastName;
    private Button btnReg;
    private TextView tvAlreadyLog;
    RequestQueue requestQueue;
    AccessToken accessToken;
    final String FIRST_NAME = "first_name";
    final String LAST_NAME = "last_name";
    final String EMAIL = "email";
    final String PASSWORD = "password";
    final String CPASSWORD = "confirm_password";
    final String MERCHANT_NAME = "merchant_name";
    String firstName, lastName, email, password, confirmPassword, merchantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        requestQueue = Volley.newRequestQueue(this);
        init();
        Volley();
        isValidInput();

    }
    @OnClick(R.id.tv_already_login)
    public void txtAlrdLogin(View view) {
//        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
//        startActivity(i);
    }

    public void register(){
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidInput() == true){
                    Volley();
                }
            }
        });
    }



    private void init(){
        etFirstName = findViewById(R.id.et_reg_name);
        etLastName = findViewById(R.id.et_reg_last_name);
        etEmail = findViewById(R.id.et_reg_email);
        etPass = findViewById(R.id.et_reg_password);
        etConfPass = findViewById(R.id.et_reg_confpassword);
        etMerchName = findViewById(R.id.et_reg_merch);
        btnReg = findViewById(R.id.btn_register);
        tvAlreadyLog = findViewById(R.id.tv_already_login);
    }

    public void Volley(){
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        email = etEmail.getText().toString();
        password = etPass.getText().toString();
        confirmPassword = etConfPass.getText().toString();
        //isMerchant = 1;
        merchantName = etMerchName.getText().toString();

        String url = "http://210.210.154.65:4444/api/auth/signup";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                accessToken = new Gson().fromJson(response,AccessToken.class);
                TokenManager.getInstance(getSharedPreferences("pref",MODE_PRIVATE)).saveToken(accessToken);
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                String body = "";
                try{
                    body = new String(error.networkResponse.data,"UTF-8");
                    JSONObject res = new JSONObject(body);
                    RegisterErrorResponse errorResponse = new Gson()
                            .fromJson(res.getJSONObject("error")
                                    .toString()
                                    ,RegisterErrorResponse.class);
                    if(errorResponse.getEmailError().size()>0){
                        if(errorResponse.getEmailError().get(0)!= null){
                            etEmail.setError(errorResponse.getEmailError().get(0));
                        }
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

        })

        {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String,String> headers = new Hashtable<>();
                headers.put("Accept","application/json");
                headers.put("Content-type","application/x-www-form-urlencoded");
                return headers;
            }
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new Hashtable<>();
                params.put(FIRST_NAME,firstName);
                params.put(LAST_NAME,lastName);
                params.put(EMAIL,email);
                params.put(PASSWORD,password);
                params.put(CPASSWORD,confirmPassword);
                //params.put(IS_MERCHANT,String.valueOf(isMerchant));
                params.put(MERCHANT_NAME,merchantName);

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
    private boolean isValidInput(){

        boolean isValid = true;

        if(etFirstName.getText().toString().isEmpty()){
            etFirstName.setError("First name cannot be empty");
            isValid = false;
        }

        if(etLastName.getText().toString().isEmpty()){
            etLastName.setError("Last name cannot be empty");
            isValid = false;
        }

        if(etEmail.getText().toString().isEmpty()){
            etEmail.setError("email name cannot be empty");
            isValid = false;
        }else if(!etEmail.getText().toString().contains("@")){
            etEmail.setError("must be a valid email");
            isValid = false;
        }

        if(etPass.getText().toString().isEmpty()){
            etPass.setError("Password cannot be empty");
            isValid = false;
        }
        else if(etPass.getText().toString().length() < 8){
            etPass.setError("Password must be 8 or more character");
            isValid = false;
        }

        if(etConfPass.getText().toString().isEmpty()){
            etConfPass.setError("Confirm password cannot be empty");
            isValid = false;
        }
        else if(!(etConfPass.getText().toString().equals(etPass.getText().toString()))){
            etConfPass.setError("Password did not match");
            isValid = false;
        }

//        if(editTextMerchantName.getText().toString().isEmpty()){
//            editTextMerchantName.setError("Merchant Name cannot be empty");
//            isValid = false;
//        }

        return isValid;
    }
}

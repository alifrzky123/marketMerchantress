package com.training.alif.geeksfarm.marketplacemerchantapp.Utils.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton INSTANCE;
    private static RequestQueue requestQueue;
    private static Context context;

    public VolleySingleton(Context context){
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new VolleySingleton(context);
        }
        return INSTANCE;
    }


    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}

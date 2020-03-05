package com.training.alif.geeksfarm.marketplacemerchantapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.training.alif.geeksfarm.marketplacemerchantapp.BuildConfig;
import com.training.alif.geeksfarm.marketplacemerchantapp.R;
import com.training.alif.geeksfarm.marketplacemerchantapp.activity.Detail;
import com.training.alif.geeksfarm.marketplacemerchantapp.entity.Product;

import java.util.ArrayList;
import java.util.List;

import static com.training.alif.geeksfarm.marketplacemerchantapp.activity.Detail.EXTRA_DATA;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
    private List<Product> lp = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public MainAdapter.MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MainHolder(v);
    }
    public void setProducts(List<Product> products){
        this.lp = products;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainHolder holder, int position) {
        Product pro = lp.get(position);
        String baseUrl = BuildConfig.URL;
        String endPoint = BuildConfig.STORAGE;
        String url = baseUrl+endPoint;
        holder.onBind(pro);
            Picasso.get()
                    .load(url+lp.get(position).getImages())
                    .error(R.drawable.ic_launcher_background)
                    .fit()
                    .centerCrop()
                    .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return lp != null ? lp.size() : 0;
    }

    public class MainHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private Context context;
        private TextView productName, merchantName, prices;
        private Product product;
        public MainHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_product);
            productName = itemView.findViewById(R.id.tv_product_name);
            merchantName = itemView.findViewById(R.id.tv_merchant_name);
            prices = itemView.findViewById(R.id.tv_price);
            context = itemView.getContext();
            itemView.setOnClickListener(listener);
        }

        public void onBind(Product product) {
            this.product = product;
            productName.setText(product.getName());
            merchantName.setText(product.getMerch().getName());
            prices.setText("Rp. "+product.getPrice());
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, product.getImages(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent (context, Detail.class);
                i.putExtra(EXTRA_DATA,product);
                context.startActivity(i);
            }
        };
    }
}

package com.training.alif.geeksfarm.marketplacemerchantapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {
    @SerializedName("productId")
    private int id;
    @SerializedName("productQty")
    private int qty;
    @SerializedName("productPrice")
    private int price;
    @SerializedName("productName")
    private String name;
    @SerializedName("productSlug")
    private String slug;
    @SerializedName("productImage")
    private String images;
    @SerializedName("productDesc")
    private String desc;
    @SerializedName("category")
    private Category cat;
    @SerializedName("merchant")
    private Merchant merch;

    public int getId() {
        return id;
    }

    public int getQty() {
        return qty;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getImages() {
        return images;
    }

    public String getDesc() {
        return desc;
    }

    public Category getCat() {
        return cat;
    }

    public Merchant getMerch() {
        return merch;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.qty);
        dest.writeInt(this.price);
        dest.writeString(this.name);
        dest.writeString(this.slug);
        dest.writeString(this.images);
        dest.writeString(this.desc);
        dest.writeParcelable(this.cat, flags);
        dest.writeParcelable(this.merch, flags);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.id = in.readInt();
        this.qty = in.readInt();
        this.price = in.readInt();
        this.name = in.readString();
        this.slug = in.readString();
        this.images = in.readString();
        this.desc = in.readString();
        this.cat = in.readParcelable(Category.class.getClassLoader());
        this.merch = in.readParcelable(Merchant.class.getClassLoader());
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}

package com.training.alif.geeksfarm.marketplacemerchantapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Merchant implements Parcelable {
    @SerializedName("merchantId")
    int id;
    @SerializedName("merchantName")
    String name;
    @SerializedName("merchantSlug")
    String slug;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.slug);
    }

    public Merchant() {
    }

    protected Merchant(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.slug = in.readString();
    }

    public static final Parcelable.Creator<Merchant> CREATOR = new Parcelable.Creator<Merchant>() {
        @Override
        public Merchant createFromParcel(Parcel source) {
            return new Merchant(source);
        }

        @Override
        public Merchant[] newArray(int size) {
            return new Merchant[size];
        }
    };
}

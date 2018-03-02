package com.example.intent;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thierrycouilleault on 05/02/2018.
 */

public class Contact implements Parcelable {

    private String mNom;
    private String mPrenom;
    private int mNumero;

    public Contact(String mNom, String mPrenom, int mNumero) {
        this.mNom = mNom;
        this.mPrenom = mPrenom;
        this.mNumero = mNumero;
    }

    protected Contact(Parcel in) {
        mNom = in.readString();
        mPrenom = in.readString();
        mNumero = in.readInt();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNom);
        dest.writeString(mPrenom);
        dest.writeInt(mNumero);
    }

    public String getmNom() {
        return mNom;
    }

    public void setmNom(String mNom) {
        this.mNom = mNom;
    }

    public String getmPrenom() {
        return mPrenom;
    }

    public void setmPrenom(String mPrenom) {
        this.mPrenom = mPrenom;
    }

    public int getmNumero() {
        return mNumero;
    }

    public void setmNumero(int mNumero) {
        this.mNumero = mNumero;
    }
}

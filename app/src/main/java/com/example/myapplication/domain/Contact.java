package com.example.myapplication.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Contact implements Parcelable, Groupable<String> {
    private String name;
    private List<String> phone;
    private List<String> email;
    private String organization;
    private String id;

    public Contact(String name, List<String> phone, List<String> email, String organization) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeStringList(this.phone);
        dest.writeStringList(this.email);
        dest.writeString(this.organization);
    }

    public Contact(Parcel in) {
        this.name = in.readString();
        this.phone = in.createStringArrayList();
        this.email = in.createStringArrayList();
        this.organization = in.readString();
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public String getItemId() {
        return id;
    }

    @Override
    public int getItemHash() {
        return hashCode();
    }

    @Override
    public String getCompareField() {
        return name;
    }
}

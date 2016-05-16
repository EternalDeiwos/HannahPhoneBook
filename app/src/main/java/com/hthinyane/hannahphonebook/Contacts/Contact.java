package com.hthinyane.hannahphonebook.Contacts;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by h.thinyane on 2016-05-13.
 */
public class Contact implements Parcelable {

    public String id;
    public String name;
    public String phone;
    public String email;
    public String details;

    public Contact () {
        this.id = "-1";
    }

    public Contact (String id, String name, String phone, String email, String details) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.details = details;
    }

    public String toString() {
        return "Name: " + name + "/n" +
                "Phone: " + phone + "/n" +
                "Email: " + email + "/n" +
                "Details: " + details + "/n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(details);
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    // deparcel
    public Contact(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        details = in.readString();
    }
}

package com.hthinyane.hannahphonebook.Contacts;

import android.os.Parcel;
import android.os.Parcelable;

import com.hthinyane.hannahphonebook.Persist.WaspDB;
import com.hthinyane.hannahphonebook.Persist.WaspDBWrapper;

import net.rehacktive.waspdb.WaspHash;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by h.thinyane on 2016-05-13.
 */
public class Contact implements Parcelable {

    private static String HASH_NAME = "contacts";
    private static WaspDBWrapper db = null;
    private static int MAX_CONTACTS = 100;

    public static void setDBPath(String path) {
        db = new WaspDBWrapper(WaspDB.getInstance(path));
    }

    public String id = null;
    public String name;
    public String phone;
    public String email;
    public String details;

    public Contact () {

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

    public static Contact getContact(String id) {
        return db.getTable(HASH_NAME).get(id);
    }

    public static ArrayList<Contact> getContacts() {
        List<Contact> tmp = db.getTable(HASH_NAME).getAllValues();
        ArrayList<Contact> contacts = new ArrayList<>(MAX_CONTACTS);
        contacts.addAll(tmp);
        return contacts;
    }

    public boolean deleteContact() {
        return db.getTable(HASH_NAME).remove(this.id);
    }

    public boolean saveContact() {
        WaspHash hash = db.getTable(HASH_NAME);
        Contact inDB;
        if ((inDB = hash.get(this.id)) != null)
            if (!inDB.deleteContact())
                return false;
        hash.put(this.id, this);
        return true;
    }
}

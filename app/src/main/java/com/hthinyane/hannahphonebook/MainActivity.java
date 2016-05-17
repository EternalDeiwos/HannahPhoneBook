package com.hthinyane.hannahphonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.hthinyane.hannahphonebook.Contacts.Contact;
import com.hthinyane.hannahphonebook.Persist.WaspDB;
import com.hthinyane.hannahphonebook.Persist.WaspDBWrapper;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    PhoneBookArrayAdapter arrayAdapter;

    public static final int REMOVE_CONTACT_TAG = 0;
    public static final int DISPLAY_CONTACT = 1;
    public static final int ADD_CONTACT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Contact.setDBPath(getFilesDir().getPath()); // for persistence

        listView = (ListView) findViewById(R.id.listView);

        ArrayList<Contact> contacts = Contact.getContacts();
//        Contact [] contacts = new Contact[3];
//        contacts[0] = new Contact("0", "Hannah Thinyane", "0466038640", "h.thinyane@ru.ac.za", "this is me");
//        contacts[1] = new Contact("1", "Busi Mzangwa", "0466038247", "b.mzangwa@ru.ac.za", "this is the secretary");
//        contacts[2] = new Contact("2", "Ingrid Sieborger", "0466038623", "i.sieborger@ru.ac.za", "this is the research person");

        arrayAdapter = new PhoneBookArrayAdapter(this, contacts);
        if (contacts.size() == 0) {
            arrayAdapter.addContact(new Contact("0", "Hannah Thinyane", "0466038640", "h.thinyane@ru.ac.za", "this is me"));
            arrayAdapter.addContact(new Contact("1", "Busi Mzangwa", "0466038247", "b.mzangwa@ru.ac.za", "this is the secretary"));
            arrayAdapter.addContact(new Contact("2", "Ingrid Sieborger", "0466038623", "i.sieborger@ru.ac.za", "this is the research person"));
        } else {
            Log.d("Contacts", "\n\n\n\n\n\n" + contacts.size() + "\n\n\n\n\n\n");
        }
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object tag;
                if (!((tag = view.getTag()) != null && tag.equals(REMOVE_CONTACT_TAG))) {
                    Toast.makeText(getApplicationContext(),
                            "Clicked on ListItem Number " + position,
                            Toast.LENGTH_LONG).show();

                    Intent detailsIntent = new Intent(getApplicationContext(), ContactActivity.class);
                    detailsIntent.putExtra(Contact.class.toString(), arrayAdapter.getContact(position));
                    startActivityForResult(detailsIntent, DISPLAY_CONTACT);
                }
            }
        });
    }

    public void addContact(View view) {
        Intent detailsIntent = new Intent(getApplicationContext(), ContactActivity.class);
        detailsIntent.putExtra(Contact.class.toString(), new Contact());
        startActivityForResult(detailsIntent, ADD_CONTACT);
    }

    public void removeContact(View view) {
        int position = listView.getPositionForView((View) view.getParent().getParent());
        arrayAdapter.remove(position);
    }

    public void clearDB(View view) {
        new WaspDBWrapper(WaspDB.getInstance(getFilesDir().getPath())).deleteDatabase();
        this.finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if(requestCode == DISPLAY_CONTACT) {
            Contact updated = data.getParcelableExtra(Contact.class.toString());
            // update an existing one
            arrayAdapter.updateContact(updated);
        } else if (requestCode == ADD_CONTACT) {
            Contact newEntry = data.getParcelableExtra(Contact.class.toString());
            arrayAdapter.addContact(newEntry);
        }
    }
}

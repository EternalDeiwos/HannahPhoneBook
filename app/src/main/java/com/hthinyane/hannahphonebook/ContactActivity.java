package com.hthinyane.hannahphonebook;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hthinyane.hannahphonebook.Contacts.Contact;

public class ContactActivity extends AppCompatActivity {

    Contact info;
    private EditText name, phone, email, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        info = (Contact) getIntent().getParcelableExtra(Contact.class.toString());

        name = (EditText)findViewById(R.id.nameText);
        name.setText(info.name);

        phone = (EditText)findViewById(R.id.phoneText);
        phone.setText(info.phone);

        email = (EditText)findViewById(R.id.emailText);
        email.setText(info.email);

        details = (EditText)findViewById(R.id.detailText);
        details.setText(info.details);

    }

    public void callContact(View view) {
        Intent callIntent = new Intent (Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse("tel:"+phone.getText()));

        if (callIntent.resolveActivity(getPackageManager())!= null) {
            startActivity(callIntent);
        }
    }

    public void smsContact(View view) {
        Intent smsIntent = new Intent (Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("sms:"+phone.getText()));

        if (smsIntent.resolveActivity(getPackageManager())!= null) {
            startActivity(smsIntent);
        }
    }

    public void onBackPressed(View view) {
        Intent intent = new Intent();
        info.name = name.getText().toString();
        info.phone = phone.getText().toString();
        info.email = email.getText().toString();
        info.details = details.getText().toString();
        intent.putExtra(Contact.class.toString(), info);
        setResult(RESULT_OK, intent);
        finish();
    }
}

package com.android.flickerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.flickerapp.dao.Contact;
import com.android.flickerapp.dao.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DatabaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private DatabaseHelper databaseHelper ;

    @BindView(R.id.contact_spinner)
    public Spinner contactSpinner;

    @BindView(R.id.txt_result)
    public TextView txt_result;

    ArrayAdapter<String> dataAdapter ;

    ArrayList<String> contactList ;
    ArrayList<Contact> contactArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        ButterKnife.bind(this);

        databaseHelper = new DatabaseHelper(this);
        contactList = new ArrayList<>();

        contactSpinner.setOnItemSelectedListener(this);

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, contactList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        contactSpinner.setAdapter(dataAdapter);


        contactArrayList = databaseHelper.getAllContacts();

        if(contactArrayList != null){
            int length = contactArrayList.size();

            for(int i = 0 ; i < length ; i++ ){
                Contact contact = contactArrayList.get(i);
                contactList.add(contact.getContactId());
            }
        }

        dataAdapter.notifyDataSetChanged();

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String item = adapterView.getItemAtPosition(position).toString();

       Contact contact =  databaseHelper.getContactInfo(item);

       if(contact != null){
           String result =
                    "stagingId : "+contact.getStagingId()
                   +"\ncontext : "+contact.getContext()
                   +"\nstatus  : "+contact.getStatus()
                   +"\nuserId  : "+contact.getUserID();

           txt_result.setText(result);
       }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

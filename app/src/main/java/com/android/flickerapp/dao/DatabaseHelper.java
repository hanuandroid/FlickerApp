package com.android.flickerapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


import static com.android.flickerapp.dao.Constants.COLUMN_CONTACTID;
import static com.android.flickerapp.dao.Constants.COLUMN_CONTEXT;
import static com.android.flickerapp.dao.Constants.COLUMN_PHONECONTACTID;
import static com.android.flickerapp.dao.Constants.COLUMN_STAGINGID;
import static com.android.flickerapp.dao.Constants.COLUMN_STATUS;
import static com.android.flickerapp.dao.Constants.COLUMN_USERID;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "task_contacts_db";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Constants.CREATE_TABLE_CONTACTS);
        db.execSQL(Constants.CREATE_TABLE_EXTENSIONS);
        db.execSQL(Constants.CREATE_TABLE_ACCOUNTS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_EXTENSIONS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_ACCOUNTS);

        // Create tables again
        onCreate(db);
    }

    public long insertContact(String contactId , String stagingId,String context) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_CONTACTID, contactId);
        values.put(COLUMN_STAGINGID,stagingId);

        // insert row
        long id = db.insert(Constants.TABLE_CONTACTS, null, values);

        // close db connection
        db.close();

        insertExtension(context,id);
        // return newly inserted row id
        return id;
    }

    public long insertExtension(String context , long phoneContactId ) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_CONTEXT, context);
        values.put(COLUMN_PHONECONTACTID,phoneContactId);

        // insert row
        long id = db.insert(Constants.TABLE_EXTENSIONS, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertAccount(int status , String userID , String context) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_USERID,userID);
        values.put(COLUMN_CONTEXT,context);

        // insert row
        long id = db.insert(Constants.TABLE_ACCOUNTS, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public ArrayList<Contact> getAllContacts() {

        ArrayList<Contact> contactList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Constants.TABLE_CONTACTS ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex(Constants._ID)));
                contact.setContactId(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_CONTACTID)));
                contact.setStagingId(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_STAGINGID)));

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return contactList;
    }


    public Contact getContactInfo(String contactId) {

        // Select All Query
        String selectQuery = "SELECT ts_contacts.* , ts_extensions.context, ts_accounts.status,ts_accounts.userID FROM ts_contacts , ts_extensions , ts_accounts " +
                "where ts_contacts._id = ts_extensions.phoneContactId and ts_extensions.context = ts_accounts.context and ts_contacts.contactId = '"+contactId+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Contact contact = null;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

                contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex(Constants._ID)));
                contact.setContactId(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_CONTACTID)));
                contact.setStagingId(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_STAGINGID)));
                contact.setContext(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_CONTEXT)));
                contact.setStatus(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_STATUS)));
                contact.setUserID(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_USERID)));


        }

        // close db connection
        db.close();

        // return notes list
        return contact ;
    }

}

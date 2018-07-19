package com.android.flickerapp.dao;

/**
 * @author hthumma
 * @Created Date    19-07-2018.
 * @Project FlickerApp
 * @Purpose
 */
public class Constants {

    public static final String TABLE_CONTACTS = "ts_contacts";
    public static final String TABLE_EXTENSIONS = "ts_extensions";
    public static final String TABLE_ACCOUNTS = "ts_accounts";

    public static final String _ID = "_id";
    public static final String COLUMN_CONTACTID = "contactId";
    public static final String COLUMN_STAGINGID = "stagingId";

    public static final String COLUMN_CONTEXT = "context";
    public static final String COLUMN_PHONECONTACTID = "phoneContactId";

    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_USERID = "userID";



    public static final String CREATE_TABLE_CONTACTS =
            "CREATE TABLE " + TABLE_CONTACTS + "("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CONTACTID + " TEXT,"
                    + COLUMN_STAGINGID + " TEXT "
                    + ")";

    public static final String CREATE_TABLE_EXTENSIONS =
            "CREATE TABLE " + TABLE_EXTENSIONS + "("
                    + COLUMN_CONTEXT + " TEXT,"
                    + COLUMN_PHONECONTACTID + " INTEGER "
                    + ")";

    public static final String CREATE_TABLE_ACCOUNTS =
            "CREATE TABLE " + TABLE_ACCOUNTS + "("
                    + COLUMN_STATUS + " INTEGER,"
                    + COLUMN_USERID + " TEXT,"
                    + COLUMN_CONTEXT + " TEXT "
                    + ")";
}

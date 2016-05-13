package com.example.kiran.mybuddy.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kiran on 25-04-2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASENAME = "MyBuddy.db";
    public static final String USER_TABLE = "USER_CREATION";
    public static final String CONTACTS_TABLE = "CONTCTS";
    public static final String COLUMN_FNAME = "FIRST_NAME";
    public static final String COLUMN_LNAME = "LAST_NAME";
    public static final String COLUMN_EMAIL = "EMAIL_ID";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_ID = "USERID";
    public static final String COLUMN_CNAME = "NAME";
    public static final String COLUMN_CAGE = "AGE";
    public static final String COLUMN_CPHONENUMBER = "PHONE_NUMBER";
    public static final String COLUMN_CEMAIL = "EMAIL";
    public static final String COLUMN_CPRFESION = "PROFESION";
    public static final String COLUMN_CBLOODGROUP = "BLOOD_GROUP";
    public static final String COLUMN_CWORKING = "WORKING";
    public static final String COLUMN_CCITY = "CITY";
    public static final String COLUMN_CSTATE = "STATE";
    public static final String COLUMN_CCOUNTRY = "COUNTTRY";
    public static final String COLUMN_CIMGPATH = "IMGPATH";
    public static final String COLUMN_CFAVOURIT = "FAVOURIT";
    public static final String COLUMN_CUSERNAME = "USERNAME";
    public static final String COLUMN_CID = "ID";
    public static final String COLUMN_INT_TYPE = "INTEGER";
    public static final String COLUMN_VARCHAR_TYPE = "VARCHAR";
    private SQLiteDatabase db;
    private Cursor cursor;
    private static final int DB_VERSION = 1;
    private static final String TAG = DataBaseHelper.class.getSimpleName();
    private int rowUpdate;

    public DataBaseHelper(Context context) {
        super(context, DATABASENAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate: ");
        String createtable = createTable();
        String createContactTable = createContactTable();
        db.execSQL(createtable);
        db.execSQL(createContactTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
        onCreate(db);*/
    }

    private String createTable() {
        String createtable = "CREATE TABLE " + USER_TABLE + " ( " + COLUMN_FNAME + " " + COLUMN_VARCHAR_TYPE
                + ","
                + COLUMN_LNAME + " " + COLUMN_VARCHAR_TYPE
                + ","
                + COLUMN_EMAIL + " " + COLUMN_VARCHAR_TYPE
                + ","
                + COLUMN_PASSWORD + " " + COLUMN_VARCHAR_TYPE
                + ","
                + COLUMN_ID + " " + COLUMN_INT_TYPE + " " + "PRIMARY KEY" + " " + "AUTOINCREMENT"
                + ");";
        return createtable;
    }

    private String createContactTable() {
        String createContactTable = "CREATE TABLE " + CONTACTS_TABLE + " ( " + COLUMN_CNAME + " " + COLUMN_VARCHAR_TYPE
                + ","
                + COLUMN_CAGE + " " + COLUMN_INT_TYPE + ","
                + COLUMN_CBLOODGROUP + " " + COLUMN_VARCHAR_TYPE + ","
                + COLUMN_CPHONENUMBER + " " + COLUMN_INT_TYPE + "(10)" + ","
                + COLUMN_CEMAIL + " " + COLUMN_VARCHAR_TYPE + ","
                + COLUMN_CPRFESION + " " + COLUMN_VARCHAR_TYPE + ","
                + COLUMN_CWORKING + " " + COLUMN_VARCHAR_TYPE + ","
                + COLUMN_CCITY + " " + COLUMN_VARCHAR_TYPE + ","
                + COLUMN_CSTATE + " " + COLUMN_VARCHAR_TYPE + ","
                + COLUMN_CCOUNTRY + " " + COLUMN_VARCHAR_TYPE + ","
                + COLUMN_CIMGPATH + " " + COLUMN_VARCHAR_TYPE + ","
                + COLUMN_CUSERNAME + " " + COLUMN_VARCHAR_TYPE + ","
                + COLUMN_CFAVOURIT + " " + COLUMN_VARCHAR_TYPE + " Default 'NF'" + ","
                + COLUMN_CID + " " + COLUMN_INT_TYPE + " " + "PRIMARY KEY" + " " + "AUTOINCREMENT"
                + ");";
        return createContactTable;
    }

    public long insertQuery(ContentValues contentValues) {
        db = getWritableDatabase();
        long rowId = db.insert(USER_TABLE, null, contentValues);
        return rowId;

    }

    public Cursor selectLogin(String loginEmail, String loginPassword) {

        db = getReadableDatabase();
        String selectEmailPassword = "select " + COLUMN_EMAIL + "," + COLUMN_PASSWORD + "," + COLUMN_ID
                + " FROM " + USER_TABLE
                + " WHERE " + COLUMN_EMAIL + "='" + loginEmail + "' AND "
                + COLUMN_PASSWORD + "='" + loginPassword + "'";
        cursor = db.rawQuery(selectEmailPassword, null);
        return cursor;
    }

    public long insertContact(ContentValues contentValues) {
        db = getWritableDatabase();
        long rowId = db.insert(CONTACTS_TABLE, null, contentValues);
        return rowId;

    }

    public Cursor selectContacts() {
        db = getReadableDatabase();
        String selectContacts = "select " + COLUMN_CIMGPATH + "," + COLUMN_CNAME + "," + COLUMN_CPHONENUMBER
                + "," + COLUMN_CAGE
                + "," + COLUMN_CPHONENUMBER
                + "," + COLUMN_CBLOODGROUP
                + "," + COLUMN_CCITY
                + "," + COLUMN_CCOUNTRY
                + "," + COLUMN_CPRFESION
                + "," + COLUMN_CWORKING
                + "," + COLUMN_CEMAIL
                + "," + COLUMN_CSTATE
                + "," + COLUMN_CFAVOURIT
                + "," + COLUMN_CID
                + " FROM " + CONTACTS_TABLE;
        cursor = db.rawQuery(selectContacts, null);
        return cursor;
    }

    public int updateFavouritContact(int position) {
        db = getWritableDatabase();
        ContentValues cvUpdateFavourit = new ContentValues();
        cvUpdateFavourit.put(COLUMN_CFAVOURIT, "F");
        int noRowsUpdated = db.update(CONTACTS_TABLE, cvUpdateFavourit, COLUMN_CID + "=" + position, null);
        Log.e(TAG, "updateFavouritContact: update position->" + position);
        return noRowsUpdated;
//        Toast.makeText(DataBaseHelper.this, "positon update->"+position, Toast.LENGTH_SHORT).show();
    }

    public Cursor seleFavouritContact() {
        db = getReadableDatabase();
        String selectFavouritContacts = "select " + COLUMN_CIMGPATH + "," + COLUMN_CNAME + "," + COLUMN_CPHONENUMBER
                + "," + COLUMN_CAGE
                + "," + COLUMN_CPHONENUMBER
                + "," + COLUMN_CBLOODGROUP
                + "," + COLUMN_CCITY
                + "," + COLUMN_CCOUNTRY
                + "," + COLUMN_CPRFESION
                + "," + COLUMN_CWORKING
                + "," + COLUMN_CEMAIL
                + "," + COLUMN_CSTATE
                + "," + COLUMN_CFAVOURIT
                + "," + COLUMN_CID
                + " FROM " + CONTACTS_TABLE
                + " "
                + "where " + COLUMN_CFAVOURIT + "='F'";
        cursor = db.rawQuery(selectFavouritContacts, null);
        return cursor;
    }

    public Cursor selectProfileFromDB(int id) {
        db = getReadableDatabase();
        String selectProfile = "select " + COLUMN_FNAME + "," + COLUMN_LNAME + "," + COLUMN_EMAIL
                + " FROM " + USER_TABLE
                + " WHERE " + COLUMN_ID + " = " + id;
        cursor = db.rawQuery(selectProfile, null);
        return cursor;
    }

    public int upDateUserDetails(String id, String fname, String lname) {
        db = getWritableDatabase();
        ContentValues cvUpdateProfile = new ContentValues();
        cvUpdateProfile.put(DataBaseHelper.COLUMN_FNAME, fname);
        cvUpdateProfile.put(DataBaseHelper.COLUMN_LNAME, lname);

        rowUpdate = db.update(USER_TABLE, cvUpdateProfile, COLUMN_ID + "=" + id, null);
        return rowUpdate;
    }

    public int upDatePassword(String newPassword, String id) {
        db = getWritableDatabase();
        ContentValues cvUpdatePassword = new ContentValues();
        cvUpdatePassword.put(DataBaseHelper.COLUMN_PASSWORD, newPassword);
        Log.e(TAG, "upDatePassword: id->changePassword->" + id);
        rowUpdate = db.update(USER_TABLE, cvUpdatePassword, COLUMN_ID + "=" + id, null);
        return rowUpdate;
//        int rowUpdate
    }
    public Cursor selectContactInfo(String id)
    {
        db = getReadableDatabase();
        String selectFavouritContacts = "select " + COLUMN_CIMGPATH + "," + COLUMN_CNAME + "," + COLUMN_CPHONENUMBER
                + "," + COLUMN_CAGE
                + "," + COLUMN_CPHONENUMBER
                + "," + COLUMN_CBLOODGROUP
                + "," + COLUMN_CCITY
                + "," + COLUMN_CCOUNTRY
                + "," + COLUMN_CPRFESION
                + "," + COLUMN_CWORKING
                + "," + COLUMN_CEMAIL
                + "," + COLUMN_CSTATE
                + "," + COLUMN_CFAVOURIT
                + "," + COLUMN_CID
                + " FROM " + CONTACTS_TABLE
                + " "
                + "where " +COLUMN_CID+" = "+id;
        cursor=db.rawQuery(selectFavouritContacts,null);
        return(cursor);
    }

    public int unFavouritContact(String contactFavouriId) {
        db=getWritableDatabase();
        ContentValues cvUnFavouritContact=new ContentValues();
        cvUnFavouritContact.put(DataBaseHelper.COLUMN_CFAVOURIT,"NF");
        int noRowupdate=db.update(CONTACTS_TABLE,cvUnFavouritContact,DataBaseHelper.COLUMN_CID+"="+contactFavouriId,null);
        return noRowupdate;
    }

    public int deletContact(String contactId) {
        db=getReadableDatabase();
        int noRowsDeleted=db.delete(CONTACTS_TABLE,DataBaseHelper.COLUMN_CID+"="+contactId,null);
        return noRowsDeleted;
    }
}

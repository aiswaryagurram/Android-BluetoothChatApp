package edu.niu.cs.z1807688.androidbluetoothchat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "chatting.db";

    private static final String TABLE_CHATTING = "tbl_chatting";
    private static final String COLUMN_MESSAGE_ID = "id";
    private static final String COLUMN_MESSAGE = "message";
     private static final String COLUMN_SERVER = "server";

        // created chatting table to store message and device side identity it may either user device or remote device

    private String CREATE_CHATTING_TABLE = "CREATE TABLE " + TABLE_CHATTING + "(" + COLUMN_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_SERVER + " TEXT," + COLUMN_MESSAGE + " TEXT)";


    private String DROP_CHATTING_TABLE = "DROP TABLE IF EXISTS " + TABLE_CHATTING;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CHATTING_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_CHATTING_TABLE);
        onCreate(db);

    }

    public void delete_record(String table ,String[] id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, "id" + " = ?",id);
        db.close();
    }

    // this below method for storing chat messages for device

    public Boolean insert_message(String message,String server) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_SERVER, server);

        long result=  db.insert(TABLE_CHATTING, null, values);

        if (result == -1)

            return false;
        else
            return true;

    }


    // this below method for getting all messages associted with specific device

    public ArrayList<String> get_messages(String server) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> _profile = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT * FROM " + "tbl_chatting  where server ='"+server+"'" , null);
        String temp = null;
        if (c.moveToFirst()) {
            do {
                _profile.add(c.getString(c.getColumnIndex(COLUMN_MESSAGE)));
            } while (c.moveToNext());
        }
        c.close();

        return _profile;
    }


}
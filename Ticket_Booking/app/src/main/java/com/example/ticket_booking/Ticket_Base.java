package com.example.ticket_booking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Ticket_Base extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ticketBooking.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tickets";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public Ticket_Base(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTicket(Ticket_Model ticket) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, ticket.getUsername());
        values.put(COLUMN_PASSWORD, ticket.getPassword());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Ticket_Model getTicket(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_USERNAME, COLUMN_PASSWORD},
                COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Ticket_Model ticket = new Ticket_Model(cursor.getString(0), cursor.getString(1));
            cursor.close();
            return ticket;
        }
        return null;
    }

    public int updateTicket(Ticket_Model ticket) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, ticket.getPassword());
        return db.update(TABLE_NAME, values, COLUMN_USERNAME + " = ?",
                new String[]{ticket.getUsername()});
    }

    public void deleteTicket(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_USERNAME + " = ?", new String[]{username});
        db.close();
    }

    public boolean isUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}

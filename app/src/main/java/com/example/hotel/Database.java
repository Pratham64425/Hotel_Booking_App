package com.example.hotel;

import static android.app.DownloadManager.COLUMN_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private static final String TABLE_ADMIN = "admin";
    private static final String COLUMN_ID = "admin_id";
    private static final String COLUMN_PASSWORD = "password";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Bookings", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Users Table
        String qry1 = "CREATE TABLE users (username TEXT, email TEXT, password TEXT)";
        sqLiteDatabase.execSQL(qry1);



        // Hotel Bookings Table
        String qry3 = "CREATE TABLE Bookings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "phone TEXT, " +
                "idProofType TEXT, " +
                "idProofNumber TEXT, " +
                "roomType TEXT, " +
                "checkIn TEXT, " +
                "checkOut TEXT)";
        sqLiteDatabase.execSQL(qry3);

        //Admin table
        String qry4 = "CREATE TABLE " + TABLE_ADMIN + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT NOT NULL)";
        sqLiteDatabase.execSQL(qry4);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop old tables if they exist
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cart");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Bookings");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_ADMIN);
        onCreate(sqLiteDatabase);
    }

    // User Registration
    public void register(String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    // User Login
    public int login(String username, String password) {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        if (c.moveToFirst()) {
            result = 1;
        }
        c.close();
        db.close();
        return result;
    }

    // Insert Booking Data
    public boolean insertBooking(String name, String phone, String idProofType, String idProofNumber, String roomType, String checkIn, String checkOut) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("idProofType", idProofType);
        values.put("idProofNumber", idProofNumber);
        values.put("roomType", roomType);
        values.put("checkIn", checkIn);
        values.put("checkOut", checkOut);

        long result = db.insert("Bookings", null, values);
        db.close();
        return result != -1;  // Returns true if inserted successfully
    }

    // Fetch All Bookings
    public Cursor ViewData() {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT id AS _id, name, phone, idProofType, idProofNumber, roomType, checkIn, checkOut FROM Bookings", null);
        return cursor;
    }

    // Delete a Booking
    public boolean deleteBooking(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Bookings", "id=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
    public boolean registerAdmin(String adminId, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, adminId);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_ADMIN, null, values);
        return result != -1;
    }

    public boolean loginAdmin(String adminId, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ADMIN, new String[]{COLUMN_ID},
                COLUMN_ID + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{adminId, password}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}

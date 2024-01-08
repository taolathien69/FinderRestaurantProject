package com.example.nhom4_cuoiki;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserRepository {

    private DatabaseHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm người dùng mới vào cơ sở dữ liệu
    public long addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FULL_NAME, user.getFullName());
        values.put(DatabaseHelper.COLUMN_EMAIL, user.getEmail());
        values.put(DatabaseHelper.COLUMN_PASSWORD, user.getPassword());

        long id = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        db.close();

        return id;
    }

    // Kiểm tra sự tồn tại của người dùng dựa trên email và mật khẩu
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {DatabaseHelper.COLUMN_ID};
        String selection = DatabaseHelper.COLUMN_EMAIL + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }
}


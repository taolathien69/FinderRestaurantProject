package com.example.nhom4_cuoiki;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_IS_EMAIL_VERIFIED = "isEmailVerified";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Lưu thông tin đăng nhập khi người dùng đăng nhập thành công
    public void createLoginSession(String userId) {
        editor.putString(KEY_USER_ID, userId);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    // Kiểm tra xem người dùng có đang đăng nhập hay không
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
                && sharedPreferences.getBoolean(KEY_IS_EMAIL_VERIFIED, false);
    }


    // Truy xuất ID người dùng đã lưu trữ
    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    // Xóa thông tin đăng nhập khi người dùng đăng xuất
    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}

package com.example.nhom4_cuoiki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button btnMap, btnNhaHang, btnLogout;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        btnMap = findViewById(R.id.buttonMap);
        btnNhaHang =findViewById(R.id.buttonNhaHang);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến MapActivity khi nhấn nút "Xem vị trí"
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        btnNhaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến MapActivity khi nhấn nút "Xem vị trí"
                Intent intent = new Intent(HomeActivity.this, ListRestaurantActivity.class);
                startActivity(intent);
            }
        });
        // Khai báo và ánh xạ Button đăng xuất
        btnLogout = findViewById(R.id.buttonLogout);

        // Thiết lập sự kiện click cho Button đăng xuất
        sessionManager = new SessionManager(this);

        // Đăng xuất khi người dùng nhấn nút "Đăng xuất"
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
                // Chuyển về màn hình đăng nhập sau khi đăng xuất
                Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish(); // Kết thúc hoạt động hiện tại để người dùng không thể quay lại màn hình trang cá nhân
            }
        });
    }
    private void logout() {
        // Thực hiện các bước cần thiết để đăng xuất, ví dụ: xóa thông tin đăng nhập, chuyển đến màn hình đăng nhập, v.v.
        // Ví dụ đơn giản: Chuyển đến màn hình đăng nhập (LoginActivity)
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Đóng MainActivity để ngăn người dùng quay lại khi nhấn nút Back
    }
}
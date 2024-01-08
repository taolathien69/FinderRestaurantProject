package com.example.nhom4_cuoiki;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EmailVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        // Lấy thông tin từ liên kết xác nhận email, ví dụ: userId
        String userId = getIntent().getStringExtra("userId");

        // Xác nhận email bằng cách cập nhật trạng thái isEmailVerified trong cơ sở dữ liệu
        if (confirmEmail(userId)) {
            // Hiển thị thông báo xác nhận thành công
            Toast.makeText(this, "Email đã được xác nhận thành công!", Toast.LENGTH_SHORT).show();
        } else {
            // Hiển thị thông báo xác nhận thất bại
            Toast.makeText(this, "Xác nhận email thất bại.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean confirmEmail(String userId) {
        // Cập nhật trạng thái isEmailVerified trong cơ sở dữ liệu
        // Gọi phương thức trong UserRepository hoặc DatabaseHelper để cập nhật trạng thái

        // Trong UserRepository có thể thực hiện một phương thức như:
        // userRepository.confirmEmail(userId);

        // Trong DatabaseHelper, bạn cần thực hiện UPDATE SQL để cập nhật trạng thái
        // Ví dụ: UPDATE users SET is_email_verified = 1 WHERE id = userId;

        return true; // Trả về true nếu xác nhận thành công, ngược lại là false
    }
}


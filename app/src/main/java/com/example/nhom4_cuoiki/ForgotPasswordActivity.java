package com.example.nhom4_cuoiki;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmail);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    // Gửi yêu cầu đặt lại mật khẩu tới email
                    sendPasswordResetEmail(email);
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập địa chỉ email.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendPasswordResetEmail(String email) {
        // Thực hiện logic gửi yêu cầu đặt lại mật khẩu tới email
        // Có thể sử dụng Firebase Authentication, Retrofit, hoặc thư viện HTTP khác để kết nối với server

        // Sau khi gửi yêu cầu thành công, hiển thị thông báo cho người dùng
        Toast.makeText(this, "Một email đặt lại mật khẩu đã được gửi tới địa chỉ email của bạn.", Toast.LENGTH_SHORT).show();

        // Chuyển về màn hình đăng nhập sau khi gửi yêu cầu
        Intent loginIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish(); // Kết thúc hoạt động hiện tại để người dùng không thể quay lại màn hình quên mật khẩu
    }
}

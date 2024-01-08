package com.example.nhom4_cuoiki;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonRegister;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        TextView textViewDangKy = findViewById(R.id.textView3);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        textViewDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignActivity();
            }
        });
        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý đăng ký ở đây
                registerUser();
            }
        });
    }

    public void openSignActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void registerUser() {
        String fullName = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thực hiện kiểm tra tồn tại email và thêm người dùng mới vào cơ sở dữ liệu
        if (!databaseHelper.checkUserByEmail(email)) {
            // Thêm người dùng mới vào cơ sở dữ liệu
            long userId = databaseHelper.addUser(fullName, email, password);

            if (userId != -1) {
                // Đăng ký thành công, chuyển đến màn hình đăng nhập
                Toast.makeText(this, "Đăng ký thành công. Bạn có thể đăng nhập ngay bây giờ.", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish(); // Kết thúc hoạt động hiện tại để người dùng không thể quay lại màn hình đăng ký
            } else {
                Toast.makeText(this, "Đăng ký thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Email đã được đăng ký. Vui lòng chọn email khác.", Toast.LENGTH_SHORT).show();
        }
    }
}

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

public class LoginActivity extends AppCompatActivity {

    TextView textViewDangKy;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private LoginManager loginManager;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginManager = new LoginManager(this);
        sessionManager = new SessionManager(this);

        textViewDangKy=findViewById(R.id.textViewRegister);
        editTextEmail = findViewById(R.id.editTextEmailLogin);
        editTextPassword = findViewById(R.id.editTextPasswordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (loginManager.loginUser(email, password)) {
            // Đăng nhập thành công, lưu thông tin đăng nhập vào session
            sessionManager.createLoginSession(email);

            // Chuyển đến màn hình trang cá nhân hoặc màn hình chính (tùy thuộc vào logic ứng dụng)
            Intent profileIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(profileIntent);
            finish(); // Kết thúc hoạt động đăng nhập để người dùng không thể quay lại
        } else {
            Toast.makeText(this, "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin đăng nhập.", Toast.LENGTH_SHORT).show();
        }
    }
}


package com.example.nhom4_cuoiki;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

public class LoginManager {

    private UserRepository userRepository;

    public LoginManager(Context context) {
        userRepository = new UserRepository(context);
    }

    public boolean loginUser(String email, String password) {
        // Kiểm tra tính hợp lệ của dữ liệu đầu vào
        if (isValidLoginData(email, password)) {
            // Thực hiện đăng nhập bằng cách tương tác với cơ sở dữ liệu
            return userRepository.checkUser(email, password);
        }
        return false;
    }

    public boolean registerUser(String fullName, String email, String password, String confirmPassword) {
        // Kiểm tra tính hợp lệ của dữ liệu đầu vào
        if (isValidRegistrationData(fullName, email, password, confirmPassword)) {
            // Thực hiện đăng ký bằng cách tương tác với cơ sở dữ liệu
            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(password);

            long userId = userRepository.addUser(user);
            return userId != -1; // Trả về true nếu đăng ký thành công
        }
        return false;
    }

    private boolean isValidLoginData(String email, String password) {
        // Kiểm tra tính hợp lệ của dữ liệu đăng nhập
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(password);
    }

    private boolean isValidRegistrationData(String fullName, String email, String password, String confirmPassword) {
        // Kiểm tra tính hợp lệ của dữ liệu đăng ký
        return !TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && !TextUtils.isEmpty(password) && password.equals(confirmPassword);
    }
}


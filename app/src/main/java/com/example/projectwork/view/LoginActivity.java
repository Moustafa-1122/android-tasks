package com.example.projectwork.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectwork.R;
import com.google.android.material.snackbar.Snackbar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    // UI Elements
    private EditText etUsername;
    private EditText etPassword;
    private CheckBox cbRememberMe;
    private Button btnLogin;

    // SharedPreferences
    private SharedPreferences sharedPreferences;

    // Valid Credentials
    private Map<String, String> validUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        validUsers = new HashMap<>();
        validUsers.put("user1", "123456");
        validUsers.put("admin", "admin123");

        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);

        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // ✅ فقط إذا Remember Me مفعل والمسجل دخول
        if (rememberMe && isLoggedIn) {
            navigateToMain();
            return;
        }

        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        btnLogin = findViewById(R.id.btnLogin);

        // استرجاع اسم المستخدم إذا Remember Me مفعل
        if (rememberMe) {
            cbRememberMe.setChecked(true);
            etUsername.setText(sharedPreferences.getString("username", ""));
        }

        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // التحقق من الحقول الفارغة
        if (username.isEmpty() || password.isEmpty()) {
            Snackbar.make(
                    findViewById(android.R.id.content),
                    "الرجاء إدخال اسم المستخدم وكلمة المرور",
                    Snackbar.LENGTH_SHORT
            ).show();
            return;
        }

        // التحقق من صحة البيانات
        if (validUsers.containsKey(username) && validUsers.get(username).equals(password)) {
            // تسجيل دخول ناجح
            saveLoginState(username);
            Toast.makeText(this, "مرحباً " + username + "!", Toast.LENGTH_SHORT).show();
            navigateToMain();
        } else {
            // بيانات خاطئة
            Snackbar.make(
                    findViewById(android.R.id.content),
                    "❌ اسم المستخدم أو كلمة المرور غير صحيحة",
                    Snackbar.LENGTH_LONG
            ).show();
            etPassword.setText("");
        }
    }

    private void saveLoginState(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (cbRememberMe.isChecked()) {
            editor.putBoolean("rememberMe", true);
            editor.putBoolean("isLoggedIn", true);
            editor.putString("username", username);
            editor.putString("currentUser", username);
        } else {
            editor.putBoolean("rememberMe", false);
            editor.putBoolean("isLoggedIn", false); // ⚠️ هنا نضع false
            editor.remove("username");
            editor.putString("currentUser", username); // optional
        }

        editor.apply();
    }


    private void navigateToMain() {
        Intent intent = new Intent(this, Main1Activity.class);
        startActivity(intent);
        finish(); // منع الرجوع لشاشة Login
    }
}
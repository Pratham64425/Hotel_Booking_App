package com.example.hotel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {
    private EditText adminIdInput, passwordInput;
    TextView register;
    private Button loginButton;
    private Database dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminIdInput = findViewById(R.id.adminIdInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        register=findViewById(R.id.register);
        dbHelper = new Database(getApplicationContext(),"healthcare",null,1);

        loginButton.setOnClickListener(v -> {
            String adminId = adminIdInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            if (dbHelper.loginAdmin(adminId, password)) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AdminDashboardActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminLoginActivity.this,AdminRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

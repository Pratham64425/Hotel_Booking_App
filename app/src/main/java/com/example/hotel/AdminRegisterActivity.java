package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.UUID;

public class AdminRegisterActivity extends AppCompatActivity {
    private TextView adminIdText;
    private EditText passwordInput;
    private Button registerButton;
    private Database dbHelper;
    private String generatedAdminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        adminIdText = findViewById(R.id.adminIdText);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);
        dbHelper = new Database(getApplicationContext(),"healthcare",null,1);

        generatedAdminId = String.valueOf(new Random().nextInt(90000) + 10000); // Generates a 5-digit random number
        adminIdText.setText(generatedAdminId);

        registerButton.setOnClickListener(v -> {
            String password = passwordInput.getText().toString().trim();
            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dbHelper.registerAdmin(generatedAdminId, password)) {
                Toast.makeText(this, "Admin Registered Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AdminLoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

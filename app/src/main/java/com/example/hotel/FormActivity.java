package com.example.hotel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel.Database;
import com.example.hotel.R;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    EditText etName, etPhone, etIdProofNumber;
    Spinner spinnerRoomType, spinnerIdProofType;
    TextView tvCheckIn, tvCheckOut;
    Button btnSubmit,btnformHome;
    Database database;

    String[] roomTypes = {"Single", "Double", "Suite", "Deluxe"};
    String[] idProofTypes = {"Aadhar", "Passport", "Driving License", "Voter ID"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etIdProofNumber = findViewById(R.id.etIdProofNumber);
        spinnerRoomType = findViewById(R.id.spinnerRoomType);
        spinnerIdProofType = findViewById(R.id.spinnerIdProofType);
        tvCheckIn = findViewById(R.id.tvCheckIn);
        tvCheckOut = findViewById(R.id.tvCheckOut);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnformHome=findViewById(R.id.btnformHome);
        btnformHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormActivity.this,HomeActivity.class));
            }
        });

        // Initialize Database

        database = new Database(this,"Bookings",null,1);
        // Setup Spinners
        spinnerRoomType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roomTypes));
        spinnerIdProofType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idProofTypes));

        // Date Pickers
        tvCheckIn.setOnClickListener(v -> showDatePicker(tvCheckIn));
        tvCheckOut.setOnClickListener(v -> showDatePicker(tvCheckOut));

        // Save Booking
        btnSubmit.setOnClickListener(v -> saveBooking());
    }

    private void showDatePicker(TextView textView) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, (view, year1, month1, dayOfMonth) ->
                textView.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1), year, month, day).show();
    }

    private void saveBooking() {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String idProofNumber = etIdProofNumber.getText().toString();
        String idProofType = spinnerIdProofType.getSelectedItem().toString();
        String roomType = spinnerRoomType.getSelectedItem().toString();
        String checkIn = tvCheckIn.getText().toString();
        String checkOut = tvCheckOut.getText().toString();

        if (name.isEmpty() || phone.isEmpty() || idProofNumber.isEmpty() || checkIn.equals("Select Check-in Date") || checkOut.equals("Select Check-out Date")) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean inserted = database.insertBooking(name, phone, idProofType, idProofNumber, roomType, checkIn, checkOut);

        if (inserted) {
            Toast.makeText(this, "Booking Saved!", Toast.LENGTH_SHORT).show();
            etName.setText("");
            etPhone.setText("");
            etIdProofNumber.setText("");
            tvCheckIn.setText("Select Check-in Date");
            tvCheckOut.setText("Select Check-out Date");
        } else {
            Toast.makeText(this, "Error Saving Booking", Toast.LENGTH_SHORT).show();
        }
    }
}

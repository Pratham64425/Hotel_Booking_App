package com.example.hotel;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel.R;
import com.example.hotel.ReceiptPrintAdapter;

public class HistoryActivity extends AppCompatActivity {
    Database dbHelper;
    TableLayout tableLayout;
    ImageButton btnPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new Database(this,"Bookings",null,1);
        tableLayout = findViewById(R.id.tableLayout);
        btnPrint = findViewById(R.id.btnPrint);

        loadBookings();

        btnPrint.setOnClickListener(v -> printReceipt());
    }

    private void loadBookings() {
        Cursor cursor = dbHelper.ViewData();
        if (cursor != null && cursor.getCount() > 0) {
            addTableHeader();
            while (cursor.moveToNext()) {
                addTableRow(cursor);
            }
            cursor.close();
        }
    }

    private void addTableHeader() {
        TableRow headerRow = new TableRow(this);
        String[] headers = {"Name", "Phone", "ID Type", "ID No.", "Room-Type", "Check-in"};

        for (String header : headers) {
            TextView textView = createTextView(header, true);
            headerRow.addView(textView);
        }
        tableLayout.addView(headerRow);
    }

    private void addTableRow(Cursor cursor) {
        TableRow row = new TableRow(this);

        for (int i = 1; i <= 6; i++) {
            String value = cursor.getString(i);
            TextView textView = createTextView(value, false);
            row.addView(textView);
        }
        tableLayout.addView(row);
    }

    private TextView createTextView(String text, boolean isHeader) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(16, 16, 16, 16);
        textView.setBackgroundColor(isHeader ? Color.BLACK : Color.WHITE);
        textView.setTextColor(isHeader ? Color.WHITE : Color.BLACK);
        textView.setTextSize(16);
        return textView;
    }

    private void printReceipt() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = new ReceiptPrintAdapter(this, "Hotel Booking Receipt", "Total Price: Rs. 3000");
        printManager.print("Booking Receipt", printAdapter, new PrintAttributes.Builder().build());
    }
}

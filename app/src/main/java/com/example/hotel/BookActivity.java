package com.example.hotel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel.R;

public class BookActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Button TownhouseAlpha=findViewById(R.id.TownhouseAlpha);
        Button Ibis=findViewById(R.id.Ibis);
        Button HotelParcEstique=findViewById(R.id.HotelParcEstique);
        Button Sayaji=findViewById(R.id.Sayaji);
        Button StLaurn=findViewById(R.id.StLaurn);

        TownhouseAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookActivity.this, TownhouseAlphaActivity.class));            }
        });
        Ibis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookActivity.this, IbisActivity.class));            }
        });
        HotelParcEstique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookActivity.this, HotelParcEstiqueActivity.class));
            }
        });
        Sayaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookActivity.this, SayajiActivity.class));
            }
        });
        StLaurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookActivity.this, StLaurnActivity.class));
            }
        });
    }
    // Handle Book Now button click

}

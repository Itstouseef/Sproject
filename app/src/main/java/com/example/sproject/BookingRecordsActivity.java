package com.example.sproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookingRecordsActivity extends AppCompatActivity {

    private TextView tvTourNameRecord;
    private TextView tvTourDateRecord;
    private TextView tvPaymentMethodRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_records);

        tvTourNameRecord = findViewById(R.id.tvTourNameRecord);
        tvTourDateRecord = findViewById(R.id.tvTourDateRecord);
        tvPaymentMethodRecord = findViewById(R.id.tvPaymentMethodRecord);

        // Get data from the Intent
        Intent intent = getIntent();
        String tourName = intent.getStringExtra("tourName");
        String tourDate = intent.getStringExtra("tourDate");
        String paymentMethod = intent.getStringExtra("paymentMethod");

        // Set data to views
        tvTourNameRecord.setText("Tour Name: " + tourName);
        tvTourDateRecord.setText("Tour Date: " + tourDate);
        tvPaymentMethodRecord.setText("Payment Method: " + paymentMethod);
    }
}

package com.example.sproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TourDetailActivity extends AppCompatActivity {

    private ImageView ivTourImage;
    private TextView tvTourName;
    private TextView tvTourDescription;
    private FloatingActionButton fabBookTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        ivTourImage = findViewById(R.id.ivTourImage);
        tvTourName = findViewById(R.id.tvTourName);
        tvTourDescription = findViewById(R.id.tvTourDescription);
        fabBookTour = findViewById(R.id.fabBookTour);

        // Get data from the Intent
        Intent intent = getIntent();
        String tourName = intent.getStringExtra("tourName");
        String tourDescription = intent.getStringExtra("tourDescription");
        int tourImageResId = intent.getIntExtra("tourImageResId", R.drawable.ic_launcher_background);

        // Set data to views
        tvTourName.setText(tourName);
        tvTourDescription.setText(tourDescription);
        ivTourImage.setImageResource(tourImageResId);

        // Set up FAB click listener
        fabBookTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookTourDialog();
            }
        });
    }

    private void showBookTourDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Book Tour");
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_book_tour, null);
        dialogBuilder.setView(dialogView);

        EditText etTourName = dialogView.findViewById(R.id.etTourName);
        EditText etTourDate = dialogView.findViewById(R.id.etTourDate);

        dialogBuilder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tourName = etTourName.getText().toString().trim();
                String tourDate = etTourDate.getText().toString().trim();

                if (tourName.isEmpty() || tourDate.isEmpty()) {
                    Toast.makeText(TourDetailActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                showPaymentMethodDialog(tourName, tourDate); // Pass the booking details to the next dialog
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilder.show();
    }

    private void showPaymentMethodDialog(String tourName, String tourDate) {
        AlertDialog.Builder paymentDialogBuilder = new AlertDialog.Builder(this);
        paymentDialogBuilder.setTitle("Select Payment Method");
        View paymentDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_payment_method, null);
        paymentDialogBuilder.setView(paymentDialogView);

        CheckBox cbCashOnArrival = paymentDialogView.findViewById(R.id.cbCashOnArrival);
        CheckBox cbDebitCard = paymentDialogView.findViewById(R.id.cbDebitCard);
        CheckBox cbPayPal = paymentDialogView.findViewById(R.id.cbPayPal);

        // Set up single selection logic
        cbCashOnArrival.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cbDebitCard.setChecked(false);
                cbPayPal.setChecked(false);
            }
        });

        cbDebitCard.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cbCashOnArrival.setChecked(false);
                cbPayPal.setChecked(false);
            }
        });

        cbPayPal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cbCashOnArrival.setChecked(false);
                cbDebitCard.setChecked(false);
            }
        });

        paymentDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle payment method selection
                String selectedPaymentMethod = "";
                if (cbCashOnArrival.isChecked()) {
                    selectedPaymentMethod = "Cash on Arrival";
                } else if (cbDebitCard.isChecked()) {
                    selectedPaymentMethod = "Debit Card";
                } else if (cbPayPal.isChecked()) {
                    selectedPaymentMethod = "PayPal";
                }

                if (selectedPaymentMethod.isEmpty()) {
                    Toast.makeText(TourDetailActivity.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Pass booking and payment method data to the new activity
                Intent recordIntent = new Intent(TourDetailActivity.this, BookingRecordsActivity.class);
                recordIntent.putExtra("tourName", tourName);
                recordIntent.putExtra("tourDate", tourDate);
                recordIntent.putExtra("paymentMethod", selectedPaymentMethod);
                startActivity(recordIntent);
            }
        });

        paymentDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        paymentDialogBuilder.show();
    }
}

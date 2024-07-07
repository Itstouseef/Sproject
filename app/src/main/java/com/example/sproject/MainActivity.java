package com.example.sproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView popularRecyclerView;
    private RecyclerView categoryRecyclerView;
    private MyAdapter popularAdapter;
    private MyAdapter categoryAdapter;
    private List<Item> popularItems;
    private List<Item> categoryItems;

    private RecyclerView rvTours;
    private LinearLayoutManager layoutManager;
    private TourAdapter adapter;
    private List<Tour> tours;

    private FloatingActionButton fabBookTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdView adView = new AdView( this);

        adView = findViewById(R.id.adViewBottom);

        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);

        // Initialize RecyclerViews
        popularRecyclerView = findViewById(R.id.view_pop);
        categoryRecyclerView = findViewById(R.id.view_cat);

        // Sample data
        popularItems = Arrays.asList(
                new Item("Miami Beach", R.drawable.p1),
                new Item("Beauty", R.drawable.p2),
                new Item("Night", R.drawable.p3)
        );

        categoryItems = Arrays.asList(
                new Item("Beaches", R.drawable.cat1),
                new Item("Camps", R.drawable.cat2),
                new Item("Forests", R.drawable.cat3)
        );

        // Set up adapters
        popularAdapter = new MyAdapter(this, popularItems); // Pass context
        categoryAdapter = new MyAdapter(this, categoryItems); // Pass context

        // Set up RecyclerViews
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        popularRecyclerView.setAdapter(popularAdapter);

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(categoryAdapter);

        init();

        fabBookTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTourDialog();
            }
        });
    }

    private void init() {
        rvTours = findViewById(R.id.rvTours);
        fabBookTour = findViewById(R.id.fabBookTour);

        tours = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        adapter = new TourAdapter(this, tours);
        rvTours.setLayoutManager(layoutManager);
        rvTours.setAdapter(adapter);
    }

    private void showAddTourDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Book a New Tour");
        View dialogView = LayoutInflater.from(this).inflate(R.layout.form_to_book_tour, null);
        dialogBuilder.setView(dialogView);

        EditText etTourName = dialogView.findViewById(R.id.etTourName);
        EditText etTourLocation = dialogView.findViewById(R.id.etTourLocation);

        dialogBuilder.setPositiveButton("Book", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tourName = etTourName.getText().toString().trim();
                String tourLocation = etTourLocation.getText().toString().trim();
                addTour(tourName, tourLocation);
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

    private void addTour(String tourName, String tourLocation) {
        Tour tour = new Tour(tourName, tourLocation);
        tours.add(tour);
        adapter.notifyDataSetChanged();
    }
}

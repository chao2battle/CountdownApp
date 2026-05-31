package com.example.countdownapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        List<CountdownItem> countdownList = new ArrayList<>();
        LocalDate test1 = LocalDate.now();
        countdownList.add(new CountdownItem("Test1", test1));
        LocalDate test2 = LocalDate.of(2028, 8, 30);
        countdownList.add(new CountdownItem("test2", test2));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CountdownAdapter adapter = new CountdownAdapter(countdownList);
        recyclerView.setAdapter(adapter);
    }
}
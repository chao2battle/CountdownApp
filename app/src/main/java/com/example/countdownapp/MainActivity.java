package com.example.countdownapp;

import static com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR;
import static com.google.android.material.datepicker.MaterialDatePicker.thisMonthInUtcMilliseconds;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CountdownAdapter adapter;
    private List<CountdownItem> countdownList;
    private FloatingActionButton addCount;
    private Button pickDate;
    private int selectDay;
    private int selectYear;
    private int selectMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countdownList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        LocalDate test1 = LocalDate.now();
        countdownList.add(new CountdownItem("Test1", test1));
        LocalDate test2 = LocalDate.of(2028, 8, 30);
        countdownList.add(new CountdownItem("test2", test2));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CountdownAdapter(this, countdownList);
        recyclerView.setAdapter(adapter);


        addCount = findViewById(R.id.fab);
        addCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountdownDialog();
            }
        });


        adapter.setOnItemClickListener(new CountdownAdapter.OnItemClickListener() {

            @Override
            public void onEditClick(int position) {
                showEditNoteDialog(position);
            }
        });



    }

    private void showEditNoteDialog(int position) {
        CountdownItem countdown = countdownList.get(position);
        View view = getLayoutInflater().inflate(R.layout.countdown_add_dialog, null);
        final EditText etTitle = view.findViewById(R.id.etTitle);
        final CalendarView datePick = view.findViewById(R.id.pickDate);

        etTitle.setText(countdown.getName());
        Calendar calendar = Calendar.getInstance();
        calendar.set(countdown.getYear(), countdown.getMonth()-1, countdown.getDay(), 0, 0, 0);
        datePick.setDate(calendar.getTimeInMillis());

        datePick.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                selectDay = day;
                selectMonth = month;
                selectYear = year;
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Countdown");
        builder.setView(view);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = etTitle.getText().toString().trim();
                if(!title.isEmpty()) {
                    CountdownItem editCountdown = new CountdownItem(title, selectMonth, selectDay, selectYear);
                    countdownList.set(position, editCountdown);
                    adapter.notifyItemInserted(position);
                    Toast.makeText(MainActivity.this, "Countdown has been changed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countdownList.remove(position);
                Toast.makeText(MainActivity.this, "Countdown has been deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();

    }

    private void showCountdownDialog() {
        View view = getLayoutInflater().inflate(R.layout.countdown_add_dialog, null);
        final EditText etTitle = view.findViewById(R.id.etTitle);
        final CalendarView datePick = view.findViewById(R.id.pickDate);



        datePick.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                selectDay = day;
                selectMonth = month;
                selectYear = year;
            }
        });

        /*selectDay = 5;
        selectMonth = 6;
        selectYear = 2026;*/


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new Countdown");
        builder.setView(view);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = etTitle.getText().toString().trim();
                if(!title.isEmpty()) {
                    CountdownItem newCountdown = new CountdownItem(title, selectMonth, selectDay, selectYear);
                    countdownList.add(newCountdown);
                    adapter.notifyItemInserted(countdownList.size()-1);
                    recyclerView.smoothScrollToPosition(countdownList.size()-1);
                    Toast.makeText(MainActivity.this, "Countdown has been created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

}
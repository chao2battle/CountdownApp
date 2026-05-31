package com.example.countdownapp;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CountdownItem {
    private String name;
    private LocalDate date;
    private long timeLeft;

    public CountdownItem(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date.getMonthValue() + "/" + date.getDayOfMonth() + "/" + date.getYear();
    }

    public String getTimeLeft() {
        LocalDate timeNow = LocalDate.now();
        timeLeft = timeNow.until(date, ChronoUnit.DAYS);
        return timeLeft + " Days";
    }
}

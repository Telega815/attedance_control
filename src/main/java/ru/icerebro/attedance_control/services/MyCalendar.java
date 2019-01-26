package ru.icerebro.attedance_control.services;

public class MyCalendar {
    private static final int[] maxDays365 = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] maxDays366 = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int day, month, year;

    public static int getActualDaysMaximum(int month, int year){
        if (year%4 == 0){
            return maxDays366[month];
        }else {
            return maxDays365[month];
        }
    }

    public MyCalendar(int day, int month, int year) {
        this.setYear(year);
        this.setMonth(month);
        this.setDay(day);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (day > this.actualDaysMaximum())
            day %= actualDaysMaximum();
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (month > 11)
            month %=12;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int actualDaysMaximum(){
        if (year%4 == 0){
            return maxDays366[month];
        }else {
            return maxDays365[month];
        }
    }

    public void incrementDay(){
        day++;
        if (day > actualDaysMaximum()){
            day = 1;
            month++;
            if (month > 11){
                month = 0;
                year++;
            }
        }
    }

    public boolean eq(MyCalendar otherCalendar){
        return this.day == otherCalendar.getDay() && this.month == otherCalendar.getMonth() && this.year == otherCalendar.getYear();
    }

    public int compare(MyCalendar otherCalendar){
        if (year<otherCalendar.getYear()) return -1;
        if (year>otherCalendar.getYear()) return 1;

        if (month < otherCalendar.getMonth()) return -1;
        if (month > otherCalendar.getMonth()) return 1;

        if (day < otherCalendar.getDay()) return -1;
        if (day > otherCalendar.getDay()) return 1;

        return 0;
    }
}

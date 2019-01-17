package ru.icerebro.attedance_control.JSON;

public class RespInfo {

    private String respHtml;
    private int dayInMonth;
    private int month;
    private int year;

    public String getRespHtml() {
        return respHtml;
    }

    public void setRespHtml(String respHtml) {
        this.respHtml = respHtml;
    }

    public int getDayInMonth() {
        return dayInMonth;
    }

    public void setDayInMonth(int dayInMonth) {
        this.dayInMonth = dayInMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

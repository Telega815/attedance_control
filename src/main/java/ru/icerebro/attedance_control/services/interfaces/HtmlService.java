package ru.icerebro.attedance_control.services.interfaces;

public interface HtmlService {

    String getDepNames();
    String getDepartment( int depId , int month, int year);
    String getSelectDate();
}
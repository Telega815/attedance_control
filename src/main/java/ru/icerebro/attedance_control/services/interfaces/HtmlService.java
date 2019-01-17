package ru.icerebro.attedance_control.services.interfaces;

import ru.icerebro.attedance_control.JSON.RespInfo;

public interface HtmlService {

    String getDepNames();
    RespInfo getDepartment(int depId , int month, int year);
    String getSelectDate();
    RespInfo getEmployeeTable(int empId, int minDay, int maxDay, int minMonth, int maxMonth, int minYear, int maxYear);
}

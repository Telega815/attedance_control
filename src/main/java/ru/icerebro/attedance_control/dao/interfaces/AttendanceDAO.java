package ru.icerebro.attedance_control.dao.interfaces;

import ru.icerebro.attedance_control.entities.Attendance;
import ru.icerebro.attedance_control.entities.Employee;

import java.util.List;

public interface AttendanceDAO {
    Attendance getAttendance(long id);

    List<Attendance> getAttendance(Employee employee);
    List<Attendance> getAttendance(Employee employee, int month, int year);
    List<Attendance> getAttendance(Employee employee, int month, int year, int day);

    long saveAttendance(Attendance attendance);
    void deleteAttendance(Attendance attendance);
    void updateAttendance(Attendance attendance);
}

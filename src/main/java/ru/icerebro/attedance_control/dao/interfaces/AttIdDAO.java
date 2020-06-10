package ru.icerebro.attedance_control.dao.interfaces;


import ru.icerebro.attedance_control.entities.AttendanceIds;

public interface AttIdDAO {

    AttendanceIds getAttendanceIds(int id);

    void updateAttendance(AttendanceIds attendanceIds);

}

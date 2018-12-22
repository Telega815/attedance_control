package ru.icerebro.attedance_control.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.icerebro.attedance_control.dao.interfaces.AttendanceDAO;
import ru.icerebro.attedance_control.entities.Attendance;
import ru.icerebro.attedance_control.entities.Employee;

import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class AttendanceDAOImpl implements AttendanceDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public AttendanceDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Attendance getAttendance(long id) {
        return sessionFactory.getCurrentSession().get(Attendance.class, id);
    }

    @Transactional
    public List<Attendance> getAttendance(Employee employee) {
        return sessionFactory.getCurrentSession().createQuery("from Attendance where eId = :employee").setParameter("employee", employee.getId()).list();
    }

    @Transactional
    public List<Attendance> getAttendance(Employee employee, int month, int year) {
        List<Attendance> list = sessionFactory.getCurrentSession().createQuery("from Attendance where eId = :employee and month = :month and aYear = :year")
                .setParameter("employee", employee.getId())
                .setParameter("month", month)
                .setParameter("year", year)
                .list();
        return list;
    }

    @Transactional
    public List<Attendance> getAttendance(Employee employee, int month, int year, int day) {
        List<Attendance> list = sessionFactory.getCurrentSession().createQuery("from Attendance where eId = :employee and month = :month and aYear = :year and day = :day")
                .setParameter("employee", employee.getId())
                .setParameter("month", month)
                .setParameter("year", year)
                .setParameter("day", day)
                .list();
        return list;
    }


    @Transactional
    public long saveAttendance(Attendance attendance) {
        return (Long)sessionFactory.getCurrentSession().save(attendance);
    }

    @Transactional
    public void deleteAttendance(Attendance attendance) {
        sessionFactory.getCurrentSession().delete(attendance);
    }

    @Transactional
    public void updateAttendance(Attendance attendance) {
        sessionFactory.getCurrentSession().update(attendance);
    }
}

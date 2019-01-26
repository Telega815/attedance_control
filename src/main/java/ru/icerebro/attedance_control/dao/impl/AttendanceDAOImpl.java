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
    public List<Attendance> getAttendance(Employee employee, int fromDay, int toDay, int fromMonth, int toMonth, int fromYear, int toYear) {
        List<Attendance> list;
        if (toYear == fromYear){
            if (toMonth == fromMonth){
                list =  sessionFactory.getCurrentSession().createQuery(
                        "from Attendance where eId = :employee " +
                                "and month = :toMonth and aYear = :toYear " +
                                "and day <= :toDay and day >= :fromDay")

                        .setParameter("employee", employee.getId())
                        .setParameter("toMonth", toMonth)
                        .setParameter("toYear", toYear)
                        .setParameter("toDay", toDay)
                        .setParameter("fromDay", fromDay)
                        .list();
            }else {
                list = yearsEqual(employee, fromDay, toDay, fromMonth, toMonth, fromYear, toYear);
            }
        }else {
            list = yearsNotEqual(employee, fromDay, toDay, fromMonth, toMonth, fromYear, toYear);
        }


        return list;
    }

    @Transactional
    List<Attendance> yearsNotEqual(Employee employee, int fromDay, int toDay, int fromMonth, int toMonth, int fromYear, int toYear){
        List<Attendance> list;
        list =  yearsEqual(employee, fromDay, toDay, fromMonth , toMonth, fromYear, fromYear);

        for (int i = fromYear+1; i < toYear; i++) {
            list.addAll(yearsEqual(employee, fromDay, toDay, fromMonth , toMonth, i, i));
        }

        list.addAll(yearsEqual(employee, fromDay, toDay, fromMonth , toMonth, toYear, toYear));

        return list;
    }

    @Transactional
    List<Attendance> yearsEqual(Employee employee, int fromDay, int toDay, int fromMonth, int toMonth, int fromYear, int toYear){
        List<Attendance> list;
        list =  sessionFactory.getCurrentSession().createQuery(
                "from Attendance where eId = :employee " +
                        "and month = :fromMonth " +
                        "and aYear = :fromYear " +
                        "and day >= :fromDay")
                .setParameter("employee", employee.getId())
                .setParameter("fromMonth", fromMonth)
                .setParameter("fromYear", fromYear)
                .setParameter("fromDay", fromDay)
                .list();
        for (int i = fromMonth+1; i < toMonth; i++) {
            list.addAll(sessionFactory.getCurrentSession().createQuery(
                    "from Attendance where eId = :employee " +
                            "and month = :fromMonth and aYear = :fromYear ")
                    .setParameter("employee", employee.getId())
                    .setParameter("fromMonth", i)
                    .setParameter("fromYear", fromYear)
                    .list());
        }

        list.addAll(sessionFactory.getCurrentSession().createQuery(
                "from Attendance where eId = :employee " +
                        "and month = :toMonth and aYear = :fromYear " +
                        "and day <= :toDay")
                .setParameter("employee", employee.getId())
                .setParameter("toMonth", toMonth)
                .setParameter("fromYear", fromYear)
                .setParameter("toDay", toDay)
                .list());
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

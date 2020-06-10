package ru.icerebro.attedance_control.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.icerebro.attedance_control.dao.interfaces.AttIdDAO;
import ru.icerebro.attedance_control.entities.AttendanceIds;

@Component
public class AttendanceIdDAOImpl implements AttIdDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public AttendanceIdDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public AttendanceIds getAttendanceIds(int id) {
        return sessionFactory.getCurrentSession().get(AttendanceIds.class, id);
    }

    @Override
    @Transactional
    public void updateAttendance(AttendanceIds attendanceIds) {
        sessionFactory.getCurrentSession().update(attendanceIds);
    }
}

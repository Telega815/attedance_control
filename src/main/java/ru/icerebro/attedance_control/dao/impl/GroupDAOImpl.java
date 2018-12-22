package ru.icerebro.attedance_control.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.icerebro.attedance_control.dao.interfaces.GroupDAO;
import ru.icerebro.attedance_control.entities.Group;

import java.util.List;

@Component
public class GroupDAOImpl implements GroupDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public Group getGroup(String name) {
        List list = sessionFactory.getCurrentSession()
                .createQuery("from Group where name = :name")
                .setParameter("name", name, StandardBasicTypes.STRING).list();
        if (list.isEmpty()){
            return null;
        }else {
            return (Group) list.get(0);
        }
    }

    @Transactional
    public int saveGroup(Group group) {
        return (Integer) sessionFactory.getCurrentSession().save(group);
    }

    @Transactional
    public void deleteGroup(Group group) {
        sessionFactory.getCurrentSession().delete(group);
    }

    @Transactional
    public void updateGroup(Group group) {
        sessionFactory.getCurrentSession().update(group);
    }
}

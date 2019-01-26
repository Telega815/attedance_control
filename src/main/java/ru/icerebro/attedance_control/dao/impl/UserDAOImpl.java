package ru.icerebro.attedance_control.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.icerebro.attedance_control.dao.interfaces.UserDAO;
import ru.icerebro.attedance_control.entities.User;

import java.util.List;

@Component
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public User getUser(String username) {
        Session session = sessionFactory.getCurrentSession();
        List<User> list = session
                .createQuery("from User where username = :username")
                .setParameter("username", username.toLowerCase())
                .list();
        if (list.isEmpty())
            return null;

        return list.get(0);
    }

    @Transactional
    public int saveUser(User user) {
        return (Integer) sessionFactory.getCurrentSession().save(user);
    }

    @Transactional
    public void deleteUser(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Transactional
    public void updateUser(User user) {
        sessionFactory.getCurrentSession().update(user);
    }
}

package ru.icerebro.attedance_control.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.icerebro.attedance_control.dao.interfaces.UserDAO;
import ru.icerebro.attedance_control.entities.User;

@Component
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public User getUser(String username) {
        return (User)sessionFactory.getCurrentSession()
                .createQuery("from User where username = :username")
                .setParameter("username", username)
                .list()
                .get(0);
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

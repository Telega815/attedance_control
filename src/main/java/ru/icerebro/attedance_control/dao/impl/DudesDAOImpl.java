package ru.icerebro.attedance_control.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.icerebro.attedance_control.dao.interfaces.DudesDAO;
import ru.icerebro.attedance_control.entities.Dude;

import java.util.List;

@Component
public class DudesDAOImpl implements DudesDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public DudesDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Dude getDude(int id) {
        return sessionFactory.getCurrentSession().get(Dude.class, id);
    }

    @Override
    @Transactional
    public Dude getDudeByKey(int key) {
        List<Dude> dudes = (List<Dude>) sessionFactory.getCurrentSession()
                .createQuery("from Dude where key = :eKey")
                .setInteger("eKey", key)
                .list();

        if (dudes.isEmpty())
            return null;

        return dudes.get(0);
    }

    @Override
    @Transactional
    public List<Dude> getDudes() {
        return sessionFactory.getCurrentSession().createQuery("from Dude ").list();
    }

    @Override
    @Transactional
    public int saveDude(Dude dude) {
        return (int) sessionFactory.getCurrentSession().save(dude);
    }

    @Override
    @Transactional
    public void deleteDude(Dude dude) {
        sessionFactory.getCurrentSession().delete(dude);
    }

    @Override
    @Transactional
    public void updateDude(Dude dude) {
        sessionFactory.getCurrentSession().update(dude);
    }
}

package ru.icerebro.attedance_control.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.icerebro.attedance_control.dao.interfaces.EmployeesDAO;
import ru.icerebro.attedance_control.entities.Department;
import ru.icerebro.attedance_control.entities.Employee;

import java.util.List;

@Component
public class EmployeesDAOImpl implements EmployeesDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public EmployeesDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Employee getEmployee(int id) {
        return sessionFactory.getCurrentSession().get(Employee.class, id);
    }

    @Transactional
    public Employee getEmployeeByKey(int key) {
        List<Employee> employees = (List<Employee>) sessionFactory.getCurrentSession()
                .createQuery("from Employee where key = :eKey")
                .setInteger("eKey", key)
                .list();

        if (employees.isEmpty())
            return null;

        return employees.get(0);
    }

    @Transactional
    public List<Employee> getEmployees(String surname) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Employee where surname = :surname")
                .setString("surname", surname).list();
    }

    @Transactional
    public List<Employee> getEmployees(String name, String surname) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Employee where surname = :surname and name = :name")
                .setString("surname", surname)
                .setString("name", name)
                .list();
    }

    @Transactional
    public List<Employee> getEmployees(String name, String surname, String patronymic) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Employee where surname = :surname and name = :name and patronymic = :patronymic")
                .setString("surname", surname)
                .setString("name", name)
                .setString("patronymic", patronymic)
                .list();
    }


    @Transactional
    public List<Employee> getEmployees(Department department) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Employee where department = :department")
                .setParameter("department", department)
                .list();
    }

    @Transactional
    public int saveEmployee(Employee employee) {
        return (Integer) sessionFactory.getCurrentSession().save(employee);
    }

    @Transactional
    public void deleteEmployee(Employee employee) {
        sessionFactory.getCurrentSession().delete(employee);
    }

    @Transactional
    public void updateEmployee(Employee employee) {
        sessionFactory.getCurrentSession().update(employee);
    }
}

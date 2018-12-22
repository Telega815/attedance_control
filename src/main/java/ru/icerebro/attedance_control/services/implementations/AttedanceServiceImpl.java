package ru.icerebro.attedance_control.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.icerebro.attedance_control.dao.interfaces.DepartmentsDAO;
import ru.icerebro.attedance_control.dao.interfaces.EmployeesDAO;
import ru.icerebro.attedance_control.entities.Department;
import ru.icerebro.attedance_control.entities.Employee;
import ru.icerebro.attedance_control.services.interfaces.AttedanceService;

import java.util.List;

public class AttedanceServiceImpl implements AttedanceService {

    private final DepartmentsDAO departmentsDAO;
    private final EmployeesDAO employeesDAO;

    @Autowired
    public AttedanceServiceImpl(DepartmentsDAO departmentsDAO, EmployeesDAO employeesDAO) {
        this.departmentsDAO = departmentsDAO;
        this.employeesDAO = employeesDAO;
    }


    @Override
    public List<Department> getDepartments() {
        return departmentsDAO.getDepartments();
    }

    @Override
    public Department getDepartment(String depName) {
        return departmentsDAO.getDepartment(depName);
    }

    @Override
    public List<Employee> getEmployees(Department department) {
        return employeesDAO.getEmployees(department);
    }

    @Override
    public List<Employee> getEmployees(String surname) {
        return employeesDAO.getEmployees(surname);
    }

    @Override
    public List<Employee> getEmployees(String name, String surname) {
        return employeesDAO.getEmployees(name, surname);
    }

    @Override
    public List<Employee> getEmployees(String name, String surname, String patronymic) {
        return employeesDAO.getEmployees(name, surname, patronymic);
    }
}

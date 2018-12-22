package ru.icerebro.attedance_control.services.interfaces;

import org.springframework.stereotype.Service;
import ru.icerebro.attedance_control.entities.Department;
import ru.icerebro.attedance_control.entities.Employee;

import java.util.List;

public interface AttedanceService {

    List<Department> getDepartments();
    Department getDepartment(String depName);


    List<Employee> getEmployees(Department department);
    List<Employee> getEmployees(String surname);
    List<Employee> getEmployees(String name, String surname);
    List<Employee> getEmployees(String name, String surname, String patronymic);
}

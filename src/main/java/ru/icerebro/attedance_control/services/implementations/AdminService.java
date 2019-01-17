package ru.icerebro.attedance_control.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.icerebro.attedance_control.JSON.AdminReqInfo;
import ru.icerebro.attedance_control.JSON.AdminRespInfo;
import ru.icerebro.attedance_control.dao.interfaces.DepartmentsDAO;
import ru.icerebro.attedance_control.dao.interfaces.EmployeesDAO;
import ru.icerebro.attedance_control.entities.Department;
import ru.icerebro.attedance_control.entities.Employee;

import java.util.*;

@Service
public class AdminService {

    private final DepartmentsDAO departmentsDAO;

    private final EmployeesDAO employeesDAO;


    @Autowired
    public AdminService(DepartmentsDAO departmentsDAO, EmployeesDAO employeesDAO) {
        this.departmentsDAO = departmentsDAO;
        this.employeesDAO = employeesDAO;
    }

    public int createDepartment(String depName){
        Department department = new Department();
        department.setDepName(depName);
        return departmentsDAO.saveDepartment(department);
    }

    public int createEmployee(AdminReqInfo info){
        Employee employee = new Employee();
        Department department = departmentsDAO.getDepartment(info.getDepId());
        employee.setDepartment(department);
        employee.setSurname(info.getSurname());
        employee.setName(info.getName());
        employee.setPatronymic(info.getPatronymic());
        employee.setKey(info.getKey());
        return employeesDAO.saveEmployee(employee);
    }

    public AdminRespInfo getDepartments(){
        List<Department> list = departmentsDAO.getDepartments();
        Map<Integer, String> map = new HashMap<>();

        AdminRespInfo adminRespInfo = new AdminRespInfo();
        for (Department dep:list) {
            map.put(dep.getId(), dep.getDepName());
        }
        adminRespInfo.setDeps(map);

        return adminRespInfo;
    }

    public AdminRespInfo getEmployees(AdminReqInfo info){
        Department department = departmentsDAO.getDepartment(info.getDepId());
        Collection<Employee> list = department.getEmployeesById();
        Map<Integer, String> map = new HashMap<>();

        AdminRespInfo adminRespInfo = new AdminRespInfo();
        for (Employee employee:list) {
            map.put(employee.getId(), employee.getSurname() + " " + employee.getName() + " " + employee.getPatronymic());
        }
        adminRespInfo.setDeps(map);

        return adminRespInfo;
    }
}

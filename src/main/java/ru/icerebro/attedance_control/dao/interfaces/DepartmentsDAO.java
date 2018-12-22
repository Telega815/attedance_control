package ru.icerebro.attedance_control.dao.interfaces;

import ru.icerebro.attedance_control.entities.Department;

import java.util.List;

public interface DepartmentsDAO {
    Department getDepartment(int id);

    Department getDepartment(String depName);

    List<Department> getDepartments();

    int saveDepartment(Department department);
    void deleteDepartment(Department department);
    void updateDepartment(Department department);
}

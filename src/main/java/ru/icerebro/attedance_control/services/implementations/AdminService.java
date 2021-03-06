package ru.icerebro.attedance_control.services.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.icerebro.attedance_control.JSON.AdminReqAttendance;
import ru.icerebro.attedance_control.JSON.AdminReqInfo;
import ru.icerebro.attedance_control.JSON.AdminRespInfo;
import ru.icerebro.attedance_control.JSON.ReqInfo;
import ru.icerebro.attedance_control.dao.interfaces.AttIdDAO;
import ru.icerebro.attedance_control.dao.interfaces.AttendanceDAO;
import ru.icerebro.attedance_control.dao.interfaces.DepartmentsDAO;
import ru.icerebro.attedance_control.dao.interfaces.EmployeesDAO;
import ru.icerebro.attedance_control.entities.Attendance;
import ru.icerebro.attedance_control.entities.AttendanceIds;
import ru.icerebro.attedance_control.entities.Department;
import ru.icerebro.attedance_control.entities.Employee;

import java.sql.Time;
import java.util.*;

@Service
public class AdminService {
    private Logger logger = LoggerFactory.getLogger(AdminService.class);

    private final DepartmentsDAO departmentsDAO;

    private final EmployeesDAO employeesDAO;

    private final AttendanceDAO attendanceDAO;

    private final AttIdDAO attIdDAO;


    private int tryNumber = 0;

    @Autowired
    public AdminService(DepartmentsDAO departmentsDAO, EmployeesDAO employeesDAO, AttendanceDAO attendanceDAO, AttIdDAO attIdDAO) {
        this.departmentsDAO = departmentsDAO;
        this.employeesDAO = employeesDAO;
        this.attendanceDAO = attendanceDAO;
        this.attIdDAO = attIdDAO;
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
        employee.setE_hidden(false);
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

    public AdminRespInfo getAttendanceOfDay(ReqInfo info){
        Map<Long, String> map = new HashMap<>();

        AdminRespInfo adminRespInfo = new AdminRespInfo();

        Employee employee = employeesDAO.getEmployee(info.getEmpId());
        
        List<Attendance> list = attendanceDAO.getAttendance(employee, info.getMinMonth(), info.getMinYear(), info.getMinDay());

        for (Attendance a : list) {
            map.put(a.getId(), a.getTime().toLocalTime().toString());
        }

        adminRespInfo.setAttendance(map);

        return adminRespInfo;
    }

    public void deleteAttendanceOfDay(ReqInfo info){
        attendanceDAO.deleteAttendance(attendanceDAO.getAttendance(info.getEmpId()));
    }

    public int getEmpKey(ReqInfo info){
        return employeesDAO.getEmployee(info.getEmpId()).getKey();
    }

    public void setEmpKey(ReqInfo info){
        Employee employee =  employeesDAO.getEmployee(info.getEmpId());
        employee.setKey(info.getEmpKey());
        employeesDAO.updateEmployee(employee);
    }

    public long writeAttendance(AdminReqAttendance info) throws Exception {
        Employee employee = employeesDAO.getEmployee(info.getEmplId());
        Attendance attendance = new Attendance();
        attendance.seteId(employee.getId());

        Calendar calendar = new GregorianCalendar(info.getYear(), info.getMonth(), info.getDay(), info.getHour(), info.getMinute());

        attendance.setTime(new Time(calendar.getTime().getTime()));
        attendance.setaYear(info.getYear());
        attendance.setMonth(info.getMonth());
        attendance.setDay(info.getDay());


        return this.saveAttendance(attendance);
    }

    private long saveAttendance(Attendance attendance) throws Exception {
        long result = -1;
        int tryNum = 1;

        while (tryNum < 150){
            try {
                AttendanceIds attendanceIds = attIdDAO.getAttendanceIds(1);

                attendanceIds.setLastwrittenid(attendanceIds.getLastwrittenid()+tryNum);
                attendance.setId(attendanceIds.getLastwrittenid());

                result = attendanceDAO.saveAttendance(attendance);

                attIdDAO.updateAttendance(attendanceIds);
                break;
            }catch (Exception e){
                tryNum++;
            }
        }

        if (result == -1){
            throw new Exception("Ids error");
        }

        return result;

//        AttendanceIds attendanceIds = attIdDAO.getAttendanceIds(1);
//
//        attendanceIds.setLastwrittenid(attendanceIds.getLastwrittenid()+1);
//        attendance.setId(attendanceIds.getLastwrittenid());
//
//        long result = attendanceDAO.saveAttendance(attendance);
//
//        attIdDAO.updateAttendance(attendanceIds);
//
//        return result;
    }

    public void setEmpDep(ReqInfo info) {
        Employee employee = employeesDAO.getEmployee(info.getEmpId());
        Department department = departmentsDAO.getDepartment(info.getDepId());

        employee.setDepartment(department);

        employeesDAO.updateEmployee(employee);
    }
}

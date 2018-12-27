package ru.icerebro.attedance_control.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import ru.icerebro.attedance_control.dao.interfaces.AttendanceDAO;
import ru.icerebro.attedance_control.dao.interfaces.DepartmentsDAO;
import ru.icerebro.attedance_control.dao.interfaces.EmployeesDAO;
import ru.icerebro.attedance_control.entities.Attendance;
import ru.icerebro.attedance_control.entities.Department;
import ru.icerebro.attedance_control.entities.Employee;
import ru.icerebro.attedance_control.services.interfaces.HtmlGenerator;
import ru.icerebro.attedance_control.services.interfaces.HtmlService;

import java.text.DateFormatSymbols;
import java.util.*;

public class HtmlServiceImpl implements HtmlService{


    private final EmployeesDAO employeesDAO;
    private final DepartmentsDAO departmentsDAO;
    private final AttendanceDAO attendanceDAO;


    @Autowired
    public HtmlServiceImpl(EmployeesDAO employeesDAO, DepartmentsDAO departmentsDAO, AttendanceDAO attendanceDAO) {
        this.employeesDAO = employeesDAO;
        this.departmentsDAO = departmentsDAO;
        this.attendanceDAO = attendanceDAO;
    }

//    <table id="tableEmployeeAttendance"><caption align="top">Якубов</caption>
//        <thead>
//        <tr>
//            <th>Дата</th>
//            <th>Приход</th>
//            <th>Уход</th>
//            <th>Часы</th>
//        </tr>
//        </thead>
//        <tbody>
//        <tr>
//            <td>1.12.2018</td>
//            <td>09:00</td>
//            <td>18:00</td>
//            <td>9 ч. 0. мин</td>
//        </tr>
//        <tr>
//            <td>2.12.2018</td>
//            <td>09:00</td>
//            <td>18:00</td>
//            <td>9 ч. 0. мин</td>
//        </tr>
//        </tbody>
//    </table>

    public String getEmployeeTable(int e_id, int fromDay, int toDay, int fromMonth, int toMonth, int fromYear, int toYear){
        Employee employee = employeesDAO.getEmployee(e_id);
        String name = "Нет такого динозавра";
        StringBuilder str = new StringBuilder();
        if (employee != null){
            name = employee.getSurname();
            str.append("<tr>");

            List<Attendance> list = attendanceDAO.getAttendance(employee, fromDay, toDay, fromMonth, toMonth, fromYear, toYear);

            for (Attendance a : list) {
                str.append(getEmployeesAttendance(a));
            }
            str.append("</tr>");
        }

        String stringBegin = "<table id=\"tableEmployeeAttendance\"><caption align=\"top\">"+name+"</caption>\n" +
                "        <thead>\n" +
                "        <tr>\n" +
                "            <th>Дата</th>\n" +
                "            <th>Приход</th>\n" +
                "            <th>Уход</th>\n" +
                "            <th>Часы</th>\n" +
                "        </tr>\n" +
                "        </thead>\n" +
                "        <tbody>";
        String stringEnd = "</tbody>\n" +
                "    </table>";

        return stringBegin + str.toString() + stringEnd;
    }

    private String getEmployeesAttendance(Attendance attendance){
//        <td>2.12.2018</td>
//            <td>09:00</td>
//            <td>18:00</td>
//            <td>9 ч. 0. мин</td>;
                //TODO rework this; Need to split method 1 for all attendance of the day, second for first and last attendance of day
        HtmlGenerator date = new HtmlGeneratorImpl("td");
        date.setInnerText(attendance.getDay()+ "." + (attendance.getMonth()+1) + "." +attendance.getaYear());

        HtmlGenerator enter = new HtmlGeneratorImpl("td");
        enter.setInnerText(attendance.getTime().toLocalTime().getHour()+ "." + attendance.getTime().toLocalTime().getMinute());

        HtmlGenerator leave = new HtmlGeneratorImpl("td");
        leave.setInnerText(attendance.getDay()+ "." + (attendance.getMonth()+1) + "." +attendance.getaYear());

        HtmlGenerator time = new HtmlGeneratorImpl("td");
        time.setInnerText(attendance.getDay()+ "." + (attendance.getMonth()+1) + "." +attendance.getaYear());

        return null;
    }

    public String getDepNames(){
        List<Department> list = departmentsDAO.getDepartments();
        StringBuilder str = new StringBuilder();
        for (Department department: list) {
            str.append(getDepName(department));
        }
        return str.toString();
    }

    public String getDepName(Department department){
        HtmlGenerator depLi = new HtmlGeneratorImpl("li");
        depLi.addAttribute("class", "departmentLi");
        depLi.addAttribute("id", "departmentLi_"+department.getId());
        depLi.addAttribute("onclick", "selectDepartment(event)");

        StringBuilder str = new StringBuilder();
        str.append(department.getDepName().substring(0, 1).toUpperCase())
                .append(department.getDepName().substring(1));

        if (!department.getEmployeesById().isEmpty()){
            str.append("<ul class=\"employees\" id=\"employees_")
                    .append(department.getId())
                    .append("\" style=\"display: none;\">");
            for (Employee e: department.getEmployeesById()) {
                str.append(getEmployeeLi(e));
            }
            str.append("</ul>");
            depLi.setInnerText(str.toString());
        }else {
            depLi.setFullTag(true);
        }

        return depLi.toString();
    }

    private String getEmployeeLi(Employee employee){
        HtmlGenerator emplLi = new HtmlGeneratorImpl("li");
        emplLi.addAttribute("class", "employeeLi employeeLi_" + employee.getDepartment().getId());
        emplLi.addAttribute("onclick", "selectEmployee( event )");
        emplLi.addAttribute("onmouseover", "overEmployee( event )");
        emplLi.addAttribute("onmouseout", "outEmployee( event )");
        emplLi.setInnerText(employee.getSurname() + " " + employee.getName());
        return emplLi.toString();
    }

    @Override
    public String getSelectDate() {
        HtmlGenerator selectMonth = new HtmlGeneratorImpl("select");
        selectMonth.addAttribute("id", "selectMonth");
        HtmlGenerator selectYear = new HtmlGeneratorImpl("select");
        selectYear.addAttribute("id", "selectYear");

        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);

        StringBuilder string = new StringBuilder();

        String[] months = new DateFormatSymbols().getShortMonths();

        for (int i = 0; i < 12; i++){
            if (i == calendar.get(Calendar.MONTH)){
                string.append("<option selected=\"selected\" id=\"month_");
            }else {
                string.append("<option id=\"month_");
            }
            string.append(i).append("\">")
                  .append(months[i].toUpperCase())
                    .append("</option>");
        }
        selectMonth.setInnerText(string.toString());

        string = new StringBuilder();

        for (int i = 2018; i <= year; i++) {
            if (i == calendar.get(Calendar.YEAR)){
                string.append("<option selected=\"selected\">");
            }else {
                string.append("<option>");
            }
            string.append(i)
                  .append("</option>");
        }

        selectYear.setInnerText(string.toString());

        return selectMonth.toString() + selectYear.toString();
    }

    public String getDepartment( int depId, int month, int year){
        Department department = departmentsDAO.getDepartment(depId);
        Calendar calendar = new GregorianCalendar(year, month, 1);

        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        StringBuilder string = new StringBuilder();
        for (int i = 1; i <= days; i++) {
            string.append("<td>").append(i).append("</td>\n");
        }

        HtmlGenerator depDays = new HtmlGeneratorImpl("div");
        depDays.addAttribute("class","days");
        depDays.setInnerText("<span>День</span>\n" +
                "                                <table>\n" +
                "                                    <tr>\n" +
                string +
                "                                    </tr>\n" +
                "                                </table>");

        StringBuilder str = new StringBuilder();
        for (Employee e:department.getEmployeesById()) {
            str.append(getEmployee(e, calendar));
        }

        return depDays.toString() + str;
    }

    private String getEmployee(Employee employee, Calendar calendar){
        StringBuilder string = new StringBuilder();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);


        //attendanceList.sort(Comparator.comparingLong(o -> o.getTime().getTime()));

        for (int i = 1; i <= days; i++) {
            List<Attendance> attendanceList = attendanceDAO.getAttendance(employee, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), i);
            attendanceList.sort(Comparator.comparingLong(o -> o.getTime().getTime()));

            float jobTime = 0;
            if (!attendanceList.isEmpty()){
                jobTime = (attendanceList.get(attendanceList.size()-1).getTime().getTime() - attendanceList.get(0).getTime().getTime())/1000/60/60;
            }
            if (jobTime == 0){
                string.append("<td>")
                        .append("</td>\n");
            }else if (jobTime < 8){
                string.append("<td class=\"failure\">")
                        .append(String.format("%.1f", jobTime))
                        .append("</td>\n");
            }else {
                string.append("<td class=\"success\">")
                        .append(String.format("%.1f", jobTime))
                        .append("</td>\n");
            }
        }


        return "<div class=\"worker\">\n" +
                "                                <span>" + employee.getSurname() + "</span>\n" +
                "                                <table>\n" +
                "                                    <tr>\n" +
                string +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </div>";
    }
}

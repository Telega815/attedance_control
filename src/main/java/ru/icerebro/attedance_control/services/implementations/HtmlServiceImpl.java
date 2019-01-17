package ru.icerebro.attedance_control.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import ru.icerebro.attedance_control.JSON.RespInfo;
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

    public RespInfo getEmployeeTable(int empId, int minDay, int maxDay, int minMonth, int maxMonth, int minYear, int maxYear){
        RespInfo respInfo = new RespInfo();
        respInfo.setMonth(minMonth);
        respInfo.setYear(minYear);
        Calendar calendar = new GregorianCalendar(minYear, minMonth, 1);
        respInfo.setDayInMonth(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        Employee employee = employeesDAO.getEmployee(empId);
        String name = "Нет такого динозавра";
        StringBuilder str = new StringBuilder();
        if (employee != null){
            name = employee.getSurname();

            List<Attendance> list = attendanceDAO.getAttendance(employee, minDay, maxDay, minMonth, maxMonth, minYear, maxYear);

            list.sort(Comparator.comparingLong(o -> o.getTime().getTime()));


            int day = -1;
            int month = -1;
            int year = -1;

            List<Attendance> tempList = new ArrayList<>();

            for (Attendance a: list) {
                if (day == a.getDay() && month == a.getMonth() && year == a.getaYear()){
                    tempList.add(a);
                }else {
                    if (!tempList.isEmpty()){
                        str.append(getEmployeesAttendance(tempList));
                        tempList.clear();
                        day = -1;
                    }
                }

                if (day == -1) {
                    day = a.getDay();
                    month = a.getMonth();
                    year = a.getaYear();
                    tempList.add(a);
                }
            }
            if (!tempList.isEmpty()){
                str.append(getEmployeesAttendance(tempList));
            }

        }

        String stringBegin = "<table id=\"tableEmployeeAttendance\"><caption id=\"employeeTableHeader\" align=\"top\">"+name+"</caption>\n" +
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

        respInfo.setRespHtml(stringBegin + str.toString() + stringEnd);

        return respInfo;
    }

    private String getEmployeesAttendance(List<Attendance> list){
        if (list.isEmpty()) return "";

        Attendance enterAdnc = list.get(0);
        Attendance leaveAdnc = list.get(list.size()-1);

        HtmlGenerator tr = new HtmlGeneratorImpl("tr");

        HtmlGenerator date = new HtmlGeneratorImpl("td");
        date.setInnerText(enterAdnc.getDay()+ "." + (enterAdnc.getMonth()+1) + "." +enterAdnc.getaYear());

        HtmlGenerator enter = new HtmlGeneratorImpl("td");
        enter.setInnerText(enterAdnc.getTime().toLocalTime().getHour()+ ":" + enterAdnc.getTime().toLocalTime().getMinute());

        HtmlGenerator leave = new HtmlGeneratorImpl("td");
        leave.setInnerText(leaveAdnc.getTime().toLocalTime().getHour()+ ":" + leaveAdnc.getTime().toLocalTime().getMinute());

        HtmlGenerator time = new HtmlGeneratorImpl("td");

        long deltaHour = leaveAdnc.getTime().toLocalTime().getHour() - enterAdnc.getTime().toLocalTime().getHour();
        long deltaMin = leaveAdnc.getTime().toLocalTime().getMinute() - enterAdnc.getTime().toLocalTime().getMinute();


        time.setInnerText( deltaHour + " ч. " + deltaMin + " мин.");

        tr.setInnerText(date.toString() + enter.toString() + leave.toString() + time.toString());
        return tr.toString();
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
        String transformedDepName = department.getDepName().substring(0, 1).toUpperCase() + department.getDepName().substring(1);
        str.append(transformedDepName);
        str.append("<span class =\"depNameHiddenSpan\" id =\"depNameHiddenSpan_")
                .append(department.getId()).append("\">")
                .append(transformedDepName)
                .append("</span>");


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
        emplLi.addAttribute("id", "employeeLi_" + employee.getId());
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
                string.append("<option id=\"year_")
                        .append(i)
                        .append("\" selected=\"selected\">");
            }else {
                string.append("<option id=\"year_")
                        .append(i)
                        .append("\">");
            }
            string.append(i)
                  .append("</option>");
        }

        selectYear.setInnerText(string.toString());

        return selectMonth.toString() + selectYear.toString();
    }


    public RespInfo getDepartment(int depId, int month, int year){
        RespInfo respInfo = new RespInfo();
        
        Department department = departmentsDAO.getDepartment(depId);
        Calendar calendar = new GregorianCalendar(year, month, 1);

        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        respInfo.setDayInMonth(days);
        respInfo.setMonth(month);
        respInfo.setYear(year);
        
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
        for (Employee e: department.getEmployeesById()) {
            str.append(getEmployee(e, calendar));
        }

        respInfo.setRespHtml(depDays.toString() + str);
        return respInfo;
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

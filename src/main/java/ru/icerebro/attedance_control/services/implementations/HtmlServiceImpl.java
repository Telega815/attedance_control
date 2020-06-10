package ru.icerebro.attedance_control.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import ru.icerebro.attedance_control.JSON.RespInfo;
import ru.icerebro.attedance_control.dao.interfaces.AttendanceDAO;
import ru.icerebro.attedance_control.dao.interfaces.DepartmentsDAO;
import ru.icerebro.attedance_control.dao.interfaces.EmployeesDAO;
import ru.icerebro.attedance_control.entities.Attendance;
import ru.icerebro.attedance_control.entities.Department;
import ru.icerebro.attedance_control.entities.Employee;
import ru.icerebro.attedance_control.services.MyCalendar;
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

    public RespInfo getEmployeeTable(int empId, int minDay, int maxDay, int minMonth, int maxMonth, int minYear, int maxYear){
        RespInfo respInfo = new RespInfo();
        respInfo.setMonth(minMonth);
        respInfo.setYear(minYear);
        respInfo.setDayInMonth(MyCalendar.getActualDaysMaximum(minMonth, minYear));

        if (minYear == 0 || maxYear ==0){
            respInfo.setRespHtml("Ошибка: Дата указана неверно!");
            return respInfo;
        }

        if (new MyCalendar(minDay, minMonth, minYear).compare(new MyCalendar(maxDay, maxMonth, maxYear))>0){
            respInfo.setRespHtml("Ошибка: Дата указана неверно!\n" +
                    "(начальная дата больше конечной)");
            return respInfo;
        }

        Employee employee = employeesDAO.getEmployee(empId);
        String name = "Нет такого динозавра";
        StringBuilder str = new StringBuilder();

        if (employee != null){
            name = employee.getSurname();

            List<Attendance> list = attendanceDAO.getAttendance(employee, minDay, maxDay, minMonth, maxMonth, minYear, maxYear);

            list.sort((o1, o2) -> {
                //year comparing
                if (o1.getaYear() > o2.getaYear())
                    return 1;
                else if (o1.getaYear() < o2.getaYear())
                    return -1;
                else {
                    //month comparing
                    if (o1.getMonth() > o2.getMonth())
                        return 1;
                    else if (o1.getMonth() < o2.getMonth())
                        return -1;
                    else {
                        //day comparing
                        if (o1.getDay() > o2.getDay())
                            return 1;
                        else if (o1.getDay() < o2.getDay())
                            return -1;
                        else {
                            //time comparing
                            if (o1.getTime().getTime() > o2.getTime().getTime())
                                return 1;
                            else if (o1.getTime().getTime() < o2.getTime().getTime())
                                return -1;
                            else {
                                return 0;
                            }
                        }
                    }
                }
            });


            int day = -1;
            int month = -1;
            int year = -1;

            int[] lastWrittenDate = {minDay , minMonth , minYear};


            List<Attendance> tempList = new ArrayList<>();

            if (!(minDay == maxDay && minMonth == maxMonth && minYear == maxYear))
                str.append(getEmployeesEmptyAttendance(minDay , minMonth , minYear));

            for (Attendance a: list) {
                if (day == a.getDay() && month == a.getMonth() && year == a.getaYear()){
                    tempList.add(a);
                }else {
                    if (!tempList.isEmpty()){

                        MyCalendar minDate = new MyCalendar(lastWrittenDate[0], lastWrittenDate[1], lastWrittenDate[2]);
                        MyCalendar maxDate = new MyCalendar(day , month , year);

                        if (minDay == day && minMonth == month && minYear == year) str = new StringBuilder();

                        str.append(getEmployeesEmptyAttendance(minDate, maxDate));

                        str.append(getEmployeesAttendance(tempList));


                        tempList.clear();
                        lastWrittenDate[0] = day;
                        lastWrittenDate[1] = month;
                        lastWrittenDate[2] = year;
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
                MyCalendar minDate = new MyCalendar(lastWrittenDate[0], lastWrittenDate[1], lastWrittenDate[2]);
                MyCalendar maxDate = new MyCalendar(day , month , year);

                str.append(getEmployeesEmptyAttendance(minDate, maxDate));

                str.append(getEmployeesAttendance(tempList));
            }

            MyCalendar minDate;
            if (year == -1){
                minDate = new MyCalendar(minDay , minMonth , minYear);
            }else {
                minDate = new MyCalendar(day , month , year);
            }

            MyCalendar maxDate = new MyCalendar(maxDay , maxMonth, maxYear);
            if (!minDate.eq(maxDate)) {
                str.append(getEmployeesEmptyAttendance(minDate, maxDate));
                str.append(getEmployeesEmptyAttendance(maxDay, maxMonth, maxYear));
            }else {
                if (tempList.isEmpty()){
                    str.append(getEmployeesEmptyAttendance(maxDay, maxMonth, maxYear));
                }
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



    private String getEmployeesEmptyAttendance(MyCalendar minDate, MyCalendar maxDate){
        StringBuilder builder = new StringBuilder();
        if (minDate.eq(maxDate)){
            return "";
        }
        minDate.incrementDay();
        while (!minDate.eq(maxDate)){
            builder.append(getEmployeesEmptyAttendance(minDate.getDay(), minDate.getMonth(), minDate.getYear()));
            minDate.incrementDay();
        }

        return builder.toString();
    }


    private String getEmployeesEmptyAttendance(int day, int month, int year){

        HtmlGenerator tr = new HtmlGeneratorImpl("tr");

        HtmlGenerator date = new HtmlGeneratorImpl("td");
        date.setInnerText(day+ "." + (month+1) + "." +year);

        HtmlGenerator enter = new HtmlGeneratorImpl("td");
        enter.setInnerText("--:--");

        HtmlGenerator leave = new HtmlGeneratorImpl("td");
        leave.setInnerText("--:--");

        HtmlGenerator time = new HtmlGeneratorImpl("td");

        time.setInnerText("0 ч. 0 мин.");

        tr.setInnerText(date.toString() + enter.toString() + leave.toString() + time.toString());
        return tr.toString();
    }

    private String getEmployeesAttendance(List<Attendance> list){
        if (list.isEmpty()) return "";

        Calendar currentDate = new GregorianCalendar();

        Attendance enterAdnc = list.get(0);
        Attendance leaveAdnc = list.get(list.size()-1);

        HtmlGenerator tr = new HtmlGeneratorImpl("tr");

        HtmlGenerator date = new HtmlGeneratorImpl("td");
        date.setInnerText(enterAdnc.getDay()+ "." + (enterAdnc.getMonth()+1) + "." +enterAdnc.getaYear());

        HtmlGenerator enter = new HtmlGeneratorImpl("td");

        int min = enterAdnc.getTime().toLocalTime().getMinute();
        if (min == 0)
            enter.setInnerText(enterAdnc.getTime().toLocalTime().getHour()+ ":00");
        else{
            if (min >= 10){
                enter.setInnerText(enterAdnc.getTime().toLocalTime().getHour()+ ":" + min);
            }else {
                enter.setInnerText(enterAdnc.getTime().toLocalTime().getHour()+ ":0" + min);
            }
        }

        HtmlGenerator leave = new HtmlGeneratorImpl("td");
        if (list.size() == 1){
            leave.setInnerText("--:--");
        }else {
            min = leaveAdnc.getTime().toLocalTime().getMinute();
            if (min == 0)
                leave.setInnerText(leaveAdnc.getTime().toLocalTime().getHour()+ ":00");
            else{
                if (min >= 10) {
                    leave.setInnerText(leaveAdnc.getTime().toLocalTime().getHour()+ ":" + min);
                }else {
                    leave.setInnerText(leaveAdnc.getTime().toLocalTime().getHour()+ ":0" + min);
                }
            }
        }


        HtmlGenerator time = new HtmlGeneratorImpl("td");

        if (list.size() == 1){
            time.setInnerText("0 ч. 0 мин.");
            if (enterAdnc.getDay() == currentDate.get(Calendar.DAY_OF_MONTH) &&
                    enterAdnc.getMonth() == currentDate.get(Calendar.MONTH) &&
                    enterAdnc.getaYear() == currentDate.get(Calendar.YEAR)){
                tr.addAttribute("class", "CurrentDay");
            }else {
                tr.addAttribute("class", "failure");
            }
        }else {
            long deltaHour = leaveAdnc.getTime().toLocalTime().getHour() - enterAdnc.getTime().toLocalTime().getHour();
            long deltaMin = leaveAdnc.getTime().toLocalTime().getMinute() - enterAdnc.getTime().toLocalTime().getMinute();
            if (deltaMin < 0){
                deltaHour--;
                deltaMin = 60 + deltaMin;
            }

            time.setInnerText( deltaHour + " ч. " + deltaMin + " мин.");
            if (enterAdnc.getDay() == currentDate.get(Calendar.DAY_OF_MONTH) &&
                    enterAdnc.getMonth() == currentDate.get(Calendar.MONTH) &&
                    enterAdnc.getaYear() == currentDate.get(Calendar.YEAR)){
                tr.addAttribute("class", "CurrentDay");
            }else {
                if (deltaHour >= 8){
                    tr.addAttribute("class", "success");
                }else if (deltaHour == 7 && deltaMin >= 30){
                    tr.addAttribute("class", "success");
                }else {
                    tr.addAttribute("class", "failure");
                }
            }
        }

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
                if (!e.isE_hidden())
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
            if (!e.isE_hidden())
                str.append(getEmployee(e, calendar));
        }

        respInfo.setRespHtml(depDays.toString() + str);
        return respInfo;
    }

    private String getEmployee(Employee employee, Calendar calendar){
        StringBuilder string = new StringBuilder();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar currentDate = new GregorianCalendar();


        //attendanceList.sort(Comparator.comparingLong(o -> o.getTime().getTime()));

        for (int i = 1; i <= days; i++) {
            List<Attendance> attendanceList = attendanceDAO.getAttendance(employee, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), i);
            attendanceList.sort(Comparator.comparingLong(o -> o.getTime().getTime()));

            double jobTime = 0;
            if (!attendanceList.isEmpty()){
                Attendance enterAdnc = attendanceList.get(0);
                Attendance leaveAdnc = attendanceList.get(attendanceList.size()-1);

                long deltaHour = leaveAdnc.getTime().toLocalTime().getHour() - enterAdnc.getTime().toLocalTime().getHour();
                long deltaMin = leaveAdnc.getTime().toLocalTime().getMinute() - enterAdnc.getTime().toLocalTime().getMinute();

                if (deltaMin < 0){
                    deltaHour--;
                    deltaMin = 60 + deltaMin;
                }

                jobTime = (double)deltaHour + (double)deltaMin/60;
            }

            if (attendanceList.size() == 1){
                if (i == currentDate.get(Calendar.DAY_OF_MONTH) &&
                        calendar.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                        calendar.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR)){
                    string.append("<td class=\"CurrentDay\">0")
                            .append("</td>\n");
                }else {
                    string.append("<td class=\"failure\">0")
                            .append("</td>\n");
                }
            }else if (attendanceList.size() > 1) {
                if (i == currentDate.get(Calendar.DAY_OF_MONTH) &&
                        calendar.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                        calendar.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR)){
                    if (jobTime == 0){
                        string.append("<td class=\"CurrentDay\">0")
                                .append("</td>\n");
                    }else {
                        string.append("<td class=\"CurrentDay\">")
                                .append(String.format("%.1f", jobTime))
                                .append("</td>\n");
                    }
                }else {
                    if (jobTime == 0){
                        string.append("<td class=\"failure\">0")
                                .append("</td>\n");
                    }else if (jobTime >= 7.5){
                        string.append("<td class=\"success\">")
                                .append(String.format("%.1f", jobTime))
                                .append("</td>\n");
                    }
                    else {
                        string.append("<td class=\"failure\">")
                                .append(String.format("%.1f", jobTime))
                                .append("</td>\n");
                    }
                }
            }else {
                string.append("<td>0")
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

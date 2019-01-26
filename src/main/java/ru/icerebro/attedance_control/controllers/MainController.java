package ru.icerebro.attedance_control.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.icerebro.attedance_control.JSON.*;
import ru.icerebro.attedance_control.entities.Authority;
import ru.icerebro.attedance_control.entities.User;
import ru.icerebro.attedance_control.services.implementations.AdminService;
import ru.icerebro.attedance_control.services.interfaces.AttedanceService;
import ru.icerebro.attedance_control.services.interfaces.HtmlService;
import ru.icerebro.attedance_control.services.interfaces.UserService;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
public class MainController {

    private final UserService userService;
    private final AttedanceService attedanceService;
    private final HtmlService htmlService;
    private final AdminService adminService;

    @Autowired
    public MainController(UserService userService, AttedanceService attedanceService, HtmlService htmlService, AdminService adminService) {
        this.userService = userService;
        this.attedanceService = attedanceService;
        this.htmlService = htmlService;
        this.adminService = adminService;
    }


    @GetMapping(value = "/")
    public ModelAndView welcomePage(@RequestParam(value = "error", required = false) String error){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();

        modelAndView.addObject(user);
        if (error != null) {
            modelAndView.addObject("error", "Couldn't find user with that name.");
        }

        modelAndView.setViewName("Welcome_page");
        return modelAndView;
    }

    @GetMapping(value = "/registration")
    public ModelAndView registrationPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("UserRegistration");
        User user = new User();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public String registationPost(@ModelAttribute("user") User user){
        Collection<SimpleGrantedAuthority> AUTHORITIES = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean admin = false;
        for (GrantedAuthority simpleGrantedAuthority:AUTHORITIES) {
            if (simpleGrantedAuthority.getAuthority().equals("ROLE_ADMIN"))
                admin = true;
        }
        if (admin) {
            userService.createUser(user);
        }else if (user.getUsername().equals("admin") && user.getPwd().equals("563453sei")){
            userService.createAdmin(user);
        }

        return "redirect:/";
    }


    @GetMapping(value = "/attendance")
    public ModelAndView mainPage(){
        ModelAndView modelAndView = new ModelAndView();
//        String s = attedanceService.getEmployees("Данилов").get(0).getName();
        String depNames = htmlService.getDepNames();
        modelAndView.addObject("depNames", depNames);

        String selectDate = htmlService.getSelectDate();
        modelAndView.addObject("selectDate", selectDate);

        modelAndView.setViewName("Private_page");
        return modelAndView;
    }

    @GetMapping(value = "/admin")
    public ModelAndView adminPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("AdminPage");
        return modelAndView;
    }

    @PostMapping(value = "admin/restService/createDepartment", consumes = "application/json")
    @ResponseBody
    public int createDep(@RequestBody AdminReqInfo info){

        return adminService.createDepartment(info.getNewDepName());
    }

    @PostMapping(value = "admin/restService/createEmployee", consumes = "application/json")
    @ResponseBody
    public int createEmpl(@RequestBody AdminReqInfo info){
        return adminService.createEmployee(info);
    }

    @GetMapping(value = "admin/restService/getDepartments", produces = "application/json")
    @ResponseBody
    public AdminRespInfo getDeps(){

        return adminService.getDepartments();
    }

    @PostMapping(value = "admin/restService/getEmployees", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public AdminRespInfo getEmps(@RequestBody AdminReqInfo info){
        return adminService.getEmployees(info);
    }

    @PostMapping(value = "admin/restService/getAttendanceOfDay", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public AdminRespInfo getAttendanceOfDay(@RequestBody ReqInfo info){
        return adminService.getAttendanceOfDay(info);
    }

    @PostMapping(value = "admin/restService/deleteAttendanceOfDay", consumes = "application/json")
    @ResponseBody
    public void deleteAttendanceOfDay(@RequestBody ReqInfo info){
        adminService.deleteAttendanceOfDay(info);
    }

    @PostMapping(value = "admin/restService/getEmpKey", consumes = "application/json")
    @ResponseBody
    public int getEmpKey(@RequestBody ReqInfo info){
        return adminService.getEmpKey(info);
    }

    @PostMapping(value = "admin/restService/setEmpKey", consumes = "application/json")
    @ResponseBody
    public void setEmpKey(@RequestBody ReqInfo info){
        adminService.setEmpKey(info);
    }


    @PostMapping(value = "admin/restService/writeAttendance", consumes = "application/json")
    @ResponseBody
    public long writeAttendance(@RequestBody AdminReqAttendance info){
        return adminService.writeAttendance(info);
    }



    @RequestMapping(method = RequestMethod.POST, value = "restService/getRightPanelContent", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public RespInfo saveNewPost(@RequestBody ReqInfo info){
        if (info.isSelectedEmployee()){
            return htmlService.getEmployeeTable(info.getEmpId(), info.getMinDay(), info.getMaxDay(),
                    info.getMinMonth(), info.getMaxMonth(), info.getMinYear(), info.getMaxYear());
        }else {
            return htmlService.getDepartment(info.getDepId(), info.getMonth(), info.getYear());
        }
    }
}

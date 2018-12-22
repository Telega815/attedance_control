package ru.icerebro.attedance_control.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.icerebro.attedance_control.JSON.ReqInfo;
import ru.icerebro.attedance_control.entities.User;
import ru.icerebro.attedance_control.services.interfaces.AttedanceService;
import ru.icerebro.attedance_control.services.interfaces.HtmlService;
import ru.icerebro.attedance_control.services.interfaces.UserService;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Controller
public class MainController {

    private final UserService userService;
    private final AttedanceService attedanceService;
    private final HtmlService htmlService;

    @Autowired
    public MainController(UserService userService, AttedanceService attedanceService, HtmlService htmlService) {
        this.userService = userService;
        this.attedanceService = attedanceService;
        this.htmlService = htmlService;
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
        userService.createUser(user);
        return "redirect:/";
    }


    @GetMapping(value = "/pidr")
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

    @RequestMapping(method = RequestMethod.POST, value = "restService/getRightPanelContent", consumes = "application/json", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String saveNewPost(@RequestBody ReqInfo info){

        if (info.isSelectedEmployee()){
            return "SASASASASASASASASSASASSA";
        }else {
            return htmlService.getDepartment(info.getDepId());
        }
    }
}

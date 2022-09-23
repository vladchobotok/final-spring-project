package com.example.hospitalspring.controller;

import com.example.hospitalspring.entity.Role;
import com.example.hospitalspring.entity.User;
import com.example.hospitalspring.utils.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class SignController {

    private static final Logger logger = LogManager.getLogger(SignController.class.getName());
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserValidator userValidator;

    @Autowired
    public SignController(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @GetMapping("/register")
    public String registerView(){
        logger.trace("Entered function registerView");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name, @RequestParam String surname,
                               @RequestParam(value ="birthday", required=false, defaultValue = "0") String birthday, @RequestParam String email,
                               @RequestParam String password, @RequestParam String confirmedPassword, ModelMap model){
        logger.trace("Entered function registerUser");
        if(birthday.equals("0")){
            model.addAttribute("bad_getaway", "incorrectBirthday");
            return "register";
        }

        User user = new User(name, surname, LocalDate.parse(birthday), email, passwordEncoder.encode(password), Role.PATIENT);

        if(userValidator.registerPatient(user, confirmedPassword, model)){
            return "redirect:/login";
        }
        return "register";
    }

    @GetMapping("/login")
    public String loginView(){
        logger.trace("Entered function loginView");
        return "login";
    }
}

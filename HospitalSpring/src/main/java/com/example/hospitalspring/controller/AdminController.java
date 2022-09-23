package com.example.hospitalspring.controller;

import com.example.hospitalspring.entity.DoctorsType;
import com.example.hospitalspring.entity.Role;
import com.example.hospitalspring.entity.User;
import com.example.hospitalspring.repository.DoctorsTypeRepository;
import com.example.hospitalspring.utils.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private static final Logger logger = LogManager.getLogger(AdminController.class.getName());
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserValidator userValidator;
    private final DoctorsTypeRepository doctorsTypeRepository;

    @Autowired
    public AdminController(UserValidator userValidator, DoctorsTypeRepository doctorsTypeRepository) {
        this.userValidator = userValidator;
        this.doctorsTypeRepository = doctorsTypeRepository;
    }

    @GetMapping
    public String adminView(){
        logger.trace("Entered function adminView");
        return "adminPage";
    }

    @GetMapping("/registerPatient")
    public String registerPatientView(){
        logger.trace("Entered function registerPatientView");
        return "adminRegisterPatientPage";
    }

    @GetMapping("/registerDoctor")
    public String registerDoctorView(ModelMap model){
        logger.trace("Entered function registerDoctorView");
        model.addAttribute("doctorsTypes", doctorsTypeRepository.findAllByOrderByIdAsc());
        return "adminRegisterDoctorPage";
    }

    @PostMapping("/registerPatient")
    public String registerPatient(@RequestParam String name, @RequestParam String surname,
                                  @RequestParam(value ="birthday", required=false, defaultValue = "0") String birthday, @RequestParam String email,
                                  @RequestParam String password, @RequestParam String confirmedPassword, ModelMap model){
        logger.trace("Entered function registerPatient");
        if(birthday.equals("0")){
            model.addAttribute("bad_getaway", "incorrectBirthday");
            return "adminRegisterPatientPage";
        }

        User user = new User(name, surname, LocalDate.parse(birthday), email, passwordEncoder.encode(password), Role.PATIENT);

        if(userValidator.registerPatient(user, confirmedPassword, model)){
            return "redirect:/admin";
        }
        return "adminRegisterPatientPage";
    }

    @PostMapping("/registerDoctor")
    public String registerDoctor(@RequestParam String name, @RequestParam String surname,
                                 @RequestParam(value ="birthday", required=false, defaultValue = "0") String birthday, @RequestParam String email,
                                 @RequestParam String password, @RequestParam String confirmedPassword, @RequestParam(value ="doctorsType", required=false, defaultValue = "0") String doctorsType, ModelMap model){
        logger.trace("Entered function registerDoctor");
        if(birthday.equals("0")){
            model.addAttribute("bad_getaway", "incorrectBirthday");
            model.addAttribute("doctorsTypes", doctorsTypeRepository.findAllByOrderByIdAsc());
            return "adminRegisterDoctorPage";
        }
        if(doctorsType.equals("0")){
            model.addAttribute("bad_getaway", "emptyDoctorsType");
            model.addAttribute("doctorsTypes", doctorsTypeRepository.findAllByOrderByIdAsc());
            return "adminRegisterDoctorPage";
        }

        User user = new User(name, surname, LocalDate.parse(birthday), email, passwordEncoder.encode(password), Role.DOCTOR);
        DoctorsType doctorsType1 = doctorsTypeRepository.findDoctorsTypeById(Long.parseLong(doctorsType));

        if(userValidator.registerDoctor(user, confirmedPassword, doctorsType1, model)){
            return "redirect:/admin";
        }
        return "adminRegisterDoctorPage";
    }
}

package com.example.hospitalspring.controller;

import com.example.hospitalspring.repository.PatientRepository;
import com.example.hospitalspring.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/patient")
@PreAuthorize("hasAnyAuthority('PATIENT', 'CURED')")
public class PatientController {
    private static final Logger logger = LogManager.getLogger(PatientController.class.getName());
    private final PatientRepository patientRepository;
    private final UserService userService;

    @Autowired
    public PatientController(PatientRepository patientRepository, UserService userService) {
        this.patientRepository = patientRepository;
        this.userService = userService;
    }

    @GetMapping
    public String patientView(ModelMap model, Principal principal){
        logger.trace("Entered function patientView");
        model.addAttribute("patient",
                patientRepository.findPatientByUser(
                        userService.findUserByEmail(
                                principal.getName())));
        return "patientPage";
    }
}

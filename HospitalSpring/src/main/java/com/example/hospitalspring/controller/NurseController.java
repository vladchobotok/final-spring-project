package com.example.hospitalspring.controller;

import com.example.hospitalspring.entity.Doctor;
import com.example.hospitalspring.entity.Patient;
import com.example.hospitalspring.entity.Role;
import com.example.hospitalspring.repository.DoctorRepository;
import com.example.hospitalspring.repository.PatientRepository;
import com.example.hospitalspring.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/nurse")
@PreAuthorize("hasAuthority('NURSE')")
public class NurseController {

    private static final Logger logger = LogManager.getLogger(NurseController.class.getName());
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserService userService;

    @Autowired
    public NurseController(DoctorRepository doctorRepository, PatientRepository patientRepository, UserService userService) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.userService = userService;
    }

    @GetMapping
    public String nurseView(ModelMap model, Principal principal){
        logger.trace("Entered function nurseView");
        fillTheModel(model, principal);
        return "nursePage";
    }

    @PostMapping("/completeAssignment")
    public String completeAssignment(@RequestParam String patientId2, ModelMap model, Principal principal){
        logger.trace("Entered function completeAssignment");
        Patient patient = patientRepository.findPatientById(Long.parseLong(patientId2));

        patient.getUser().setRole(Role.CURED);
        patientRepository.save(patient);

        fillTheModel(model, principal);
        return "redirect:/nurse";
    }

    public void fillTheModel(ModelMap modelMap, Principal principal){
        logger.trace("Entered function fillTheModel");
        Doctor nurse = doctorRepository.findDoctorByUser(userService.findUserByEmail(principal.getName()));

        List<Patient> patients = new ArrayList<>();
        List<Patient> allPatients = patientRepository.findAllByOrderByIdAsc();
        for (Patient patient: allPatients) {
            if(nurse.getUser().getEmail().equals(patient.getTreatment().getAssignment().getExecutor().getUser().getEmail()) && !patient.getUser().getRole().getAuthority().equals(Role.CURED.getAuthority())){
                patients.add(patient);
            }
        }
        modelMap.addAttribute("nurse", nurse);
        modelMap.addAttribute("patients", patients);
    }
}

package com.example.hospitalspring.controller;

import com.example.hospitalspring.entity.*;
import com.example.hospitalspring.repository.*;
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
@RequestMapping("/doctor")
@PreAuthorize("hasAuthority('DOCTOR')")
public class DoctorController {

    private static final Logger logger = LogManager.getLogger(DoctorController.class.getName());
    private final DoctorRepository doctorRepository;
    private final UserService userService;
    private final PatientRepository patientRepository;
    private final AssignmentsTypeRepository assignmentsTypeRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final AssignmentRepository assignmentRepository;
    private final TreatmentRepository treatmentRepository;

    @Autowired
    public DoctorController(DoctorRepository doctorRepository, UserService userService, PatientRepository patientRepository, AssignmentsTypeRepository assignmentsTypeRepository, DiagnosisRepository diagnosisRepository, AssignmentRepository assignmentRepository, TreatmentRepository treatmentRepository) {
        this.doctorRepository = doctorRepository;
        this.userService = userService;
        this.patientRepository = patientRepository;
        this.assignmentsTypeRepository = assignmentsTypeRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.assignmentRepository = assignmentRepository;
        this.treatmentRepository = treatmentRepository;
    }

    @GetMapping
    public String doctorView(ModelMap model, Principal principal){
        logger.trace("Entered function doctorView");
        fillTheModel(model, principal);
        return "doctorPage";
    }

    @PostMapping("/createAssignment")
    public String createAssignment(@RequestParam String patientId, @RequestParam( value ="doctorId2", required=false, defaultValue = "") String doctorId2,
                               @RequestParam String description, @RequestParam( value ="assignmentTypeId", required=false, defaultValue = "") String assignmentTypeId,  ModelMap model, Principal principal){
        logger.trace("Entered function createAssignment");

        Patient patient = patientRepository.findPatientById(Long.parseLong(patientId));
        Doctor executor;
        if(!doctorId2.equals("")){
            executor = doctorRepository.findDoctorById(Long.parseLong(doctorId2));
        }
        else {
            model.addAttribute("bad_getaway", "emptyExecutor");
            fillTheModel(model, principal);
            return "doctorPage";
        }
        Doctor prescriber = doctorRepository.findDoctorByUser(userService.findUserByEmail(principal.getName()));
        AssignmentsType assignmentsType;
        if(!assignmentTypeId.equals("")){
            assignmentsType = assignmentsTypeRepository.findAssignmentsTypeById(Long.parseLong(assignmentTypeId));
        }
        else {
            model.addAttribute("bad_getaway", "emptyAssignmentType");
            fillTheModel(model, principal);
            return "doctorPage";
        }

        String isNurse = doctorRepository.findDoctorByUser(executor.getUser()).getDoctorsType().getType();

        if(assignmentsType.getId() == 2 && isNurse.equals("Nurse")) {
            model.addAttribute("bad_getaway", "nurseCannotDoOperations");
            fillTheModel(model, principal);
            return "doctorPage";
        }

        Assignment assignment = new Assignment(executor, prescriber, description, assignmentsType);
        assignmentRepository.save(assignment);

        Treatment treatment = new Treatment(assignment, diagnosisRepository.findDiagnosisById(1));
        treatmentRepository.save(treatment);

        Patient patientToUpdateAssignment = patientRepository.findPatientByUser(patient.getUser());
        patientToUpdateAssignment.setTreatment(treatment);
        patientRepository.save(patientToUpdateAssignment);

        return "redirect:/doctor";
    }

    @PostMapping("/defineDiagnosis")
    public String defineDiagnosis(@RequestParam String patientId1, @RequestParam( value ="diagnosis1", required=false, defaultValue = "") String diagnosis1,
                                  ModelMap model, Principal principal){
        logger.trace("Entered function defineDiagnosis");
        Patient patient = patientRepository.findPatientById(Long.parseLong(patientId1));
        Diagnosis diagnosis;
        if(!diagnosis1.equals("")){
            diagnosis = diagnosisRepository.findDiagnosisById(Long.parseLong(diagnosis1));
        }
        else {
            model.addAttribute("bad_getaway", "emptyDiagnosis");
            fillTheModel(model, principal);
            return "doctorPage";
        }

        Treatment treatmentToUpdate = treatmentRepository.findTreatmentById(patient.getTreatment().getId());
        treatmentToUpdate.setDiagnosis(diagnosis);
        treatmentRepository.save(treatmentToUpdate);

        return "redirect:/doctor";
    }

    @PostMapping("/completeAssignment")
    public String completeAssignment(@RequestParam String patientId2, ModelMap model, Principal principal){
        logger.trace("Entered function completeAssignment");
        Patient patient = patientRepository.findPatientById(Long.parseLong(patientId2));

        patient.getUser().setRole(Role.CURED);
        patientRepository.save(patient);

        fillTheModel(model, principal);
        return "redirect:/doctor";
    }
    public void fillTheModel(ModelMap modelMap, Principal principal){
        logger.trace("Entered function fillTheModel");
        Doctor doctor = doctorRepository.findDoctorByUser(userService.findUserByEmail(principal.getName()));
        List<Patient> patients = new ArrayList<>();
        List<Patient> allPatients = patientRepository.findAllByOrderByIdAsc();
        for (Patient patient: allPatients) {
            long executorId = patient.getTreatment().getAssignment().getExecutor().getId();
            if (executorId == doctor.getId() && !doctor.getId().equals(patient.getDoctor().getId()) && !patient.getUser().getRole().getAuthority().equals(Role.CURED.getAuthority())) {
                patients.add(patient);
            }
            if (doctor.getId().equals(patient.getDoctor().getId()) && !patient.getUser().getRole().getAuthority().equals(Role.CURED.getAuthority())) {
                patients.add(patient);
            }
        }
        modelMap.addAttribute("doctor", doctor);
        modelMap.addAttribute("allPatients", patients);
        modelMap.addAttribute("doctorsAndNurses", doctorRepository.findAllByOrderByIdAsc());
        modelMap.addAttribute("assignmentsTypes", assignmentsTypeRepository.findAllByOrderByIdAsc());
        modelMap.addAttribute("diagnoses", diagnosisRepository.findAllByOrderByIdAsc());
    }
}

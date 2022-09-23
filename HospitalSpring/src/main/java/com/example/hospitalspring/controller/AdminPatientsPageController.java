package com.example.hospitalspring.controller;

import com.example.hospitalspring.entity.Patient;
import com.example.hospitalspring.repository.DoctorRepository;
import com.example.hospitalspring.repository.DoctorsTypeRepository;
import com.example.hospitalspring.repository.PatientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/adminPatientsPage")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminPatientsPageController {

    private static final Logger logger = LogManager.getLogger(AdminPatientsPageController.class.getName());
    private final DoctorRepository doctorRepository;
    private final DoctorsTypeRepository doctorsTypeRepository;
    private final PatientRepository patientRepository;
    private String typeOfSort = "id";

    @Autowired
    public AdminPatientsPageController(DoctorRepository doctorRepository, DoctorsTypeRepository doctorsTypeRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorsTypeRepository = doctorsTypeRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping(params = {"page"})
    public String adminPatientsPageView(ModelMap modelMap, @RequestParam int page, @PageableDefault(size = 4) Pageable pageable, @ModelAttribute("pageSorted") String pageSorted){
        logger.trace("Entered function adminPatientsPageView");
        if(!pageSorted.equals("")){
            typeOfSort = pageSorted;
        }
        Pageable newPaging = PageRequest.of(page-1, 4, Sort.by(typeOfSort));
        modelMap.addAttribute("page", patientRepository.findAll(newPaging));
        modelMap.addAttribute("doctors", doctorRepository.findAllByDoctorsTypeNot(doctorsTypeRepository.findDoctorsTypeById(1)));
        return "adminPatientsPage";
    }

    @PostMapping("/sorting")
    public String sortPatients(@RequestParam( value ="type", required=false, defaultValue = "0") String type,
                              RedirectAttributes redirectAttributes, @ModelAttribute("pageSorted") String pageSorted){
        logger.trace("Entered function sortPatients");
        switch (Integer.parseInt(type)) {
            case 1:
                redirectAttributes.addFlashAttribute("pageSorted", "user.name");
                break;
            case 2:
                redirectAttributes.addFlashAttribute("pageSorted", "user.birthday");
                break;
            case 3:
            case 0:
            default:
                redirectAttributes.addFlashAttribute("pageSorted", "id");
                break;
        }
        return "redirect:/admin/adminPatientsPage?page=1";
    }

    @PostMapping("/assignDoctor")
    public String assignDoctor(@RequestParam( value ="doctorId", required=false, defaultValue = "0") String doctorId,
                               @RequestParam String patientId, @ModelAttribute("pageSorted") String pageSorted, ModelMap modelMap){
        logger.trace("Entered function assignDoctor");
        if(doctorId.equals("0")){
            modelMap.addAttribute("bad_getaway", "emptyDoctor");
            Pageable newPaging = PageRequest.of(0, 4, Sort.by(typeOfSort));
            modelMap.addAttribute("page", patientRepository.findAll(newPaging));
            modelMap.addAttribute("doctors", doctorRepository.findAllByDoctorsTypeNot(doctorsTypeRepository.findDoctorsTypeById(1)));
            return "adminPatientsPage";
        }

        Patient patientToUpdate = patientRepository.findPatientById(Long.parseLong(patientId));
        patientToUpdate.setDoctor(doctorRepository.findDoctorById(Long.parseLong(doctorId)));
        patientRepository.save(patientToUpdate);
        return "redirect:/admin/adminPatientsPage?page=1";
    }
}
package com.example.hospitalspring.controller;

import com.example.hospitalspring.entity.Doctor;
import com.example.hospitalspring.repository.DoctorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/admin/adminDoctorsPage")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminDoctorsPageController {

    private static final Logger logger = LogManager.getLogger(AdminDoctorsPageController.class.getName());
    private final DoctorRepository doctorRepository;
    private String typeOfSort = "id";

    @Autowired
    public AdminDoctorsPageController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping(params = {"page"})
    public String adminDoctorsPageView(ModelMap modelMap, @RequestParam int page, @PageableDefault(size = 4) Pageable pageable, @ModelAttribute("pageSorted") String pageSorted){
        logger.trace("Entered function adminDoctorsPageView");
        if(!pageSorted.equals("")){
            typeOfSort = pageSorted;
        }
        if(typeOfSort.equals("countPatients")){
            Page<Doctor> pageDoctor = doctorRepository.findAllPatients(pageable);
            modelMap.addAttribute("page", pageDoctor);
        }
        else{
            Pageable newPaging = PageRequest.of(page-1, 4, Sort.by(typeOfSort));
            modelMap.addAttribute("page", doctorRepository.findAll(newPaging));
        }
        return "adminDoctorsPage";
    }

    @PostMapping("/sorting")
    public String sortDoctors(@RequestParam( value ="type", required=false, defaultValue = "0") String type,
                              RedirectAttributes redirectAttributes, @ModelAttribute("pageSorted") String pageSorted){
        logger.trace("Entered function sortDoctors");
        switch (Integer.parseInt(type)) {
            case 1:
                redirectAttributes.addFlashAttribute("pageSorted", "user.name");
                break;
            case 2:
                redirectAttributes.addFlashAttribute("pageSorted", "doctorsType.type");
                break;
            case 3:
                redirectAttributes.addFlashAttribute("pageSorted", "countPatients");
                break;
            case 4:
            case 0:
            default:
                redirectAttributes.addFlashAttribute("pageSorted", "id");
                break;
        }
        return "redirect:/admin/adminDoctorsPage?page=1";
    }
}

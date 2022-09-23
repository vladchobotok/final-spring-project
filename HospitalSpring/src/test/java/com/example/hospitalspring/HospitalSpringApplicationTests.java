package com.example.hospitalspring;

import com.example.hospitalspring.controller.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class HospitalSpringApplicationTests {

    @Autowired
    private AdminController adminController;

    @Autowired
    private AdminDoctorsPageController adminDoctorsPageController;
    @Autowired
    private AdminPatientsPageController adminPatientsPageController;
    @Autowired
    private DoctorController doctorController;
    @Autowired
    private NurseController nurseController;
    @Autowired
    private PatientController patientController;
    @Autowired
    private SignController signController;

    @Test
    void contextLoads() {
        assertThat(adminController).isNotNull();
        assertThat(adminDoctorsPageController).isNotNull();
        assertThat(adminPatientsPageController).isNotNull();
        assertThat(doctorController).isNotNull();
        assertThat(nurseController).isNotNull();
        assertThat(patientController).isNotNull();
        assertThat(signController).isNotNull();
    }

}

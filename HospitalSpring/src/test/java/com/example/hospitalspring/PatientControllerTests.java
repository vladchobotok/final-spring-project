package com.example.hospitalspring;

import com.example.hospitalspring.controller.DoctorController;
import com.example.hospitalspring.controller.PatientController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("patient1@gmail.com")
public class PatientControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PatientController patientController;

    @Test
    public void doctorLoggedInTest() throws Exception {
        this.mockMvc.perform(get("/patient"))
                .andDo(print())
                .andExpect(authenticated());
    }
}

package com.example.hospitalspring;

import com.example.hospitalspring.controller.SignController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SignController signController;

    @Test
    public void loginShouldReturnDefaultMessageTest() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome back")));
    }
    @Test
    public void registerShouldReturnDefaultMessageTest() throws Exception {
        this.mockMvc.perform(get("/register"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Register")));
    }

    @Test
    public void registerUserTest() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("name", "Bohdan")
                        .param("surname", "Nesterenko")
                        .param("email", "nesterenkobohdan@gmail.com")
                        .param("birthday", "2003-08-22")
                        .param("password", "DDDDDDd1")
                        .param("confirmedPassword", "DDDDDDd1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void registerBadCredentialsTest() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("name", "Bohdan")
                        .param("surname", "Nesterenko")
                        .param("email", "nesterenkobohdan@gmail.com")
                        .param("birthday", "2003-08-22")
                        .param("password", "DDDDDDd1")
                        .param("confirmedPassword", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Register")));
    }

    @Test
    public void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void loginAdminTest() throws Exception {
        this.mockMvc.perform(formLogin().user("email", "admin@gmail.com").password("admin"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    public void loginDoctorTest() throws Exception {
        this.mockMvc.perform(formLogin().user("email", "doctor1@gmail.com").password("1111"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor"));
    }

    @Test
    public void loginNurseTest() throws Exception {
        this.mockMvc.perform(formLogin().user("email", "nurse1@gmail.com").password("1111"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/nurse"));
    }

    @Test
    public void loginPatientTest() throws Exception {
        this.mockMvc.perform(formLogin().user("email", "patient1@gmail.com").password("1111"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient"));
    }

    @Test
    public void loginBadCredentialsTest() throws Exception {
        this.mockMvc.perform(formLogin().user("email", "patient1@gmail.com").password(""))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }
}

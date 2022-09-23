package com.example.hospitalspring;

import com.example.hospitalspring.entity.*;
import com.example.hospitalspring.repository.DoctorRepository;
import com.example.hospitalspring.repository.PatientRepository;
import com.example.hospitalspring.repository.TreatmentRepository;
import com.example.hospitalspring.repository.UserRepository;
import com.example.hospitalspring.utils.UserValidator;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ModelMap;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserValidatorTests {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PatientRepository patientRepository;
    public final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserValidator userValidator;

    @Test
    public void registerPatientTest(){
        ModelMap modelMap = new ModelMap();
        User user = new User("Bohdan", "Nesterenko",  LocalDate.parse("2003-08-12"), "nesterenkobohdan@gmail.com", passwordEncoder.encode("DDDDDDd1"), Role.PATIENT);
        boolean isUserRegistered = userValidator.registerPatient(user, "DDDDDDd1", modelMap);

        Assert.assertTrue(isUserRegistered);
        Assert.assertNotNull(userRepository.findUserByEmail("nesterenkobohdan@gmail.com"));
        Assert.assertTrue(CoreMatchers.is(user.getRole()).matches(Role.PATIENT));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void registerPatientFailTest(){
        Mockito.when(userRepository.findUserByEmail("patient1@gmail.com"))
                .thenReturn(Optional.of(new User()));

        ModelMap modelMap = new ModelMap();
        User user = new User("Bohdan", "Nesterenko",  LocalDate.parse("2003-08-12"), "patient1@gmail.com", passwordEncoder.encode("DDDDDDd1"), Role.PATIENT);
        boolean isUserRegistered = userValidator.registerPatient(user, "DDDDDDd1", modelMap);

        Assert.assertFalse(isUserRegistered);
        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}

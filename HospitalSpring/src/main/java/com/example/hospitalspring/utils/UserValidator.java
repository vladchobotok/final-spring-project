package com.example.hospitalspring.utils;

import com.example.hospitalspring.entity.Doctor;
import com.example.hospitalspring.entity.DoctorsType;
import com.example.hospitalspring.entity.Patient;
import com.example.hospitalspring.entity.User;
import com.example.hospitalspring.repository.DoctorRepository;
import com.example.hospitalspring.repository.PatientRepository;
import com.example.hospitalspring.repository.TreatmentRepository;
import com.example.hospitalspring.repository.UserRepository;
import com.example.hospitalspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {
    private static final String EMAIL_REGEX = "^[a-zA-Z\\d_.+-]+@[a-zA-Z\\d-]+\\.[a-zA-Z\\d-.]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    private static final String BAD_GETAWAY = "bad_getaway";
    private final UserService userService;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final TreatmentRepository treatmentRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public UserValidator(UserService userService, UserRepository userRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, TreatmentRepository treatmentRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.treatmentRepository = treatmentRepository;
    }

    public boolean registerPatient(User user, String confirmedPassword, ModelMap model){
        if (isRegisterInvalid(user, confirmedPassword, model)){
            return false;
        }

        Matcher emailMatcher = Pattern.compile(EMAIL_REGEX).matcher(user.getEmail());
        Matcher passwordMatcher = Pattern.compile(PASSWORD_REGEX).matcher(confirmedPassword);
        if (emailMatcher.matches() && passwordMatcher.matches()) {
            userRepository.save(user);
            Patient patient = new Patient(user, doctorRepository.findDoctorById(7), treatmentRepository.findTreatmentById(4));
            patientRepository.save(patient);
            return true;
        }
        else {
            if(!emailMatcher.matches()){
                model.put(BAD_GETAWAY, "incorrectEmailFormat");
            }
            else{
                model.put(BAD_GETAWAY, "incorrectPasswordFormat");
            }
            return false;
        }
    }

    public boolean registerDoctor(User user, String confirmedPassword, DoctorsType doctorsType, ModelMap model){
        if (isRegisterInvalid(user, confirmedPassword, model)){
            return false;
        }
        Matcher emailMatcher = Pattern.compile(EMAIL_REGEX).matcher(user.getEmail());
        Matcher passwordMatcher = Pattern.compile(PASSWORD_REGEX).matcher(confirmedPassword);
        if (emailMatcher.matches() && passwordMatcher.matches()) {
            userRepository.save(user);
            Doctor doctor = new Doctor(user, doctorsType);
            doctorRepository.save(doctor);
            return true;

        } else {
            if(!emailMatcher.matches()){
                model.put(BAD_GETAWAY, "incorrectEmailFormat");
            }
            else{
                model.put(BAD_GETAWAY, "incorrectPasswordFormat");
            }
            return false;
        }
    }

    public boolean isRegisterInvalid(User user, String confirmedPassword, ModelMap model){
        if (user.getName().equals("")){
            model.put(BAD_GETAWAY, "emptyName");
            return true;
        }
        if(user.getSurname().equals("")){
            model.put(BAD_GETAWAY, "emptySurname");
            return true;
        }
        if(user.getEmail().equals("")){
            model.put(BAD_GETAWAY, "emptyEmail");
            return true;
        }
        if(user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())){
            model.put(BAD_GETAWAY, "incorrectBirthday");
            return true;
        }
        if(user.getPassword().equals("")){
            model.put(BAD_GETAWAY, "emptyPassword");
            return true;
        }
        if(userService.findUserByEmail(user.getEmail()) != null) {
            model.put(BAD_GETAWAY, "accountAlreadyExists");
            return true;
        }

        if (!passwordEncoder.matches(confirmedPassword, user.getPassword())) {
            model.put(BAD_GETAWAY, "wrongConfirmedPassword");
            return true;
        }
        return false;
    }
}

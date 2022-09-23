package com.example.hospitalspring.repository;

import com.example.hospitalspring.entity.Doctor;
import com.example.hospitalspring.entity.Patient;
import com.example.hospitalspring.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findPatientByUser(User user);

    List<Patient> findAllByDoctor(Doctor doctor);
    Patient findPatientById(long id);
    Page<Patient> findAllByOrderByIdAsc(Pageable pageable);
    List<Patient> findAllByOrderByIdAsc();
}

package com.example.hospitalspring.repository;

import com.example.hospitalspring.entity.Doctor;
import com.example.hospitalspring.entity.DoctorsType;
import com.example.hospitalspring.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findDoctorById(long id);
    Doctor findDoctorByUser(User user);

    @Query("SELECT d FROM Doctor d LEFT JOIN Patient p ON d.id = p.doctor.id GROUP BY d ORDER BY COUNT(p.id) DESC")
    Page<Doctor> findAllPatients(Pageable pageable);
    Page<Doctor> findAll(Pageable pageable);

    List<Doctor> findAllByOrderByIdAsc();
    List<Doctor> findAllByDoctorsTypeNot(DoctorsType doctorsType);
    List<Doctor> findAllByDoctorsType(DoctorsType doctorsType);
}

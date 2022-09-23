package com.example.hospitalspring.repository;

import com.example.hospitalspring.entity.Diagnosis;
import com.example.hospitalspring.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    Diagnosis findDiagnosisById(long id);
    List<Diagnosis> findAllByOrderByIdAsc();
}

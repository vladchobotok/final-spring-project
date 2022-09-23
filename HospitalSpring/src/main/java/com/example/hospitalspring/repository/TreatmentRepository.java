package com.example.hospitalspring.repository;

import com.example.hospitalspring.entity.Patient;
import com.example.hospitalspring.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    Treatment findTreatmentById(long id);
    List<Treatment> findAllByOrderByIdAsc();
}

package com.example.hospitalspring.repository;

import com.example.hospitalspring.entity.Assignment;
import com.example.hospitalspring.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllByOrderByIdAsc();
}

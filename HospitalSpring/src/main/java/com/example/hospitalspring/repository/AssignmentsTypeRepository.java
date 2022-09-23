package com.example.hospitalspring.repository;

import com.example.hospitalspring.entity.AssignmentsType;
import com.example.hospitalspring.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentsTypeRepository extends JpaRepository<AssignmentsType, Long> {
    AssignmentsType findAssignmentsTypeById(long id);
    List<AssignmentsType> findAllByOrderByIdAsc();
}

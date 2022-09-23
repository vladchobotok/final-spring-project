package com.example.hospitalspring.repository;

import com.example.hospitalspring.entity.DoctorsType;
import com.example.hospitalspring.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorsTypeRepository extends JpaRepository<DoctorsType, Long> {
    DoctorsType findDoctorsTypeById(long id);
    List<DoctorsType> findAllByOrderByIdAsc();
}

package com.example.hospitalspring.repository;

import com.example.hospitalspring.entity.Patient;
import com.example.hospitalspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    List<User> findAllByOrderByIdAsc();
}

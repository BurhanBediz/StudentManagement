package com.TechProEd.StudentManagementSystem.repository;

import com.TechProEd.StudentManagementSystem.domain.Student;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@Transactional
public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findByEmail(String email);
    Boolean existsByEmail(String email) throws ResourceNotFoundException;
}

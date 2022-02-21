package com.TechProEd.StudentManagementSystem.repository;

import com.TechProEd.StudentManagementSystem.domain.Role;
import com.TechProEd.StudentManagementSystem.domain.enumeration.StudentRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(StudentRole name);
}

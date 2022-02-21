package com.TechProEd.StudentManagementSystem.security.service;

import com.TechProEd.StudentManagementSystem.domain.Student;
import com.TechProEd.StudentManagementSystem.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class StudentDetailsServiceImpl implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Student student=studentRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email+" emailine sahip öğrenci bulunamadı"));
        return  StudentDetailsImpl.build(student);
    }
}

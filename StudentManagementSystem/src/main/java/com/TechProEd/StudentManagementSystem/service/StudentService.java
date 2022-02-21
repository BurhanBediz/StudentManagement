package com.TechProEd.StudentManagementSystem.service;

import com.TechProEd.StudentManagementSystem.domain.Role;
import com.TechProEd.StudentManagementSystem.domain.Student;
import com.TechProEd.StudentManagementSystem.domain.enumeration.StudentRole;
import com.TechProEd.StudentManagementSystem.exception.ConflictException;
import com.TechProEd.StudentManagementSystem.repository.RoleRepository;
import com.TechProEd.StudentManagementSystem.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import javax.ws.rs.BadRequestException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(Student student) throws BadRequestException{

        if(studentRepository.existsByEmail(student.getEmail())){
            throw new ConflictException("Hata: Bu email zaten kullanılmakta!");
        }
        String encodedPassword=passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);
        Set<Role> roles=new HashSet<>();
        Role studentRole=roleRepository.findByName(StudentRole.ROLE_STUDENT).orElseThrow(()->
                new ResourceNotFoundException("Hata: Rol bulunamadı."));
        roles.add(studentRole);
        student.setRoles(roles);
        studentRepository.save(student);

    }
    public void login(String email,String password) throws AuthException{

        try {
            Optional<Student> student = studentRepository.findByEmail(email);
            if (!BCrypt.checkpw(password, student.get().getPassword()))
                throw new AuthException("Geçersiz email veya şifre");
        }
        catch (Exception e){
            throw new AuthException("Geçersiz email veya şifre");
        }
    }



}

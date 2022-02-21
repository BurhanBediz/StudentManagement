package com.TechProEd.StudentManagementSystem.controller;


import com.TechProEd.StudentManagementSystem.domain.Student;
import com.TechProEd.StudentManagementSystem.security.jwt.JwtUtils;
import com.TechProEd.StudentManagementSystem.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping
public class StudentController {

    public StudentService studentService;
    public AuthenticationManager authenticationManager;
    public JwtUtils jwtUtils;

    @PostMapping("/register")

    public ResponseEntity<Map<String,Boolean>> registerStudent(@Valid @RequestBody Student student){

        studentService.register(student);
        Map<String,Boolean> map=new HashMap<>();
        map.put("Öğrenci başarılı bir şekilde kayıt oldu!",true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> authenticateStudent(@RequestBody Map<String,Object> userMap) throws AuthException {

        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        studentService.login(email, password);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtUtils.generateJwtToken(authentication);
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseEntity<>(map,HttpStatus.OK);

    }

}

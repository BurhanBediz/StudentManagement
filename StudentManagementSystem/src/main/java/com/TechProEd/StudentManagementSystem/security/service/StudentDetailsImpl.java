package com.TechProEd.StudentManagementSystem.security.service;

import com.TechProEd.StudentManagementSystem.domain.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudentDetailsImpl implements UserDetails {

    private Long id;

    private String email;

    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static StudentDetailsImpl build(Student student){

        List<GrantedAuthority> authorities=student.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new StudentDetailsImpl(student.getId(),
                    student.getEmail(),
                    student.getPassword(),
                    authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){

        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.TechProEd.StudentManagementSystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=15)
    @NotNull(message="Lütfen isminizi giriniz")
    @Column(nullable = false,length=15)
    private String firstName;
    @Size(max=15)
    @NotNull(message="Lütfen soyisminizi giriniz")
    @Column(nullable = false,length=15)
    private String lastName;

    @Size(min=4,max=60)
    @NotNull(message = "Lütfen şifrenizi giriniz")
    @Column(nullable = false,length = 120)
    private String password;

    @Pattern(regexp="^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    @Size(min=14,max=14)
    @NotNull(message = "Lütfen telefon numaranızı giriniz")
    @Column(nullable = false,length = 14)
    private String phoneNumber;

    @Email(message = "Lütfen geçerli bir mail adresi girin")
    @Size(min=5,max=150)
    @NotNull(message = "Lütfen mail adresinizi giriniz")
    @Column(nullable = false,length = 14,unique = true)
    private String email;

    @Size(max=50)
    @NotNull(message = "Lütfen okul adını giriniz")
    @Column(nullable = false,length = 50)
    private String school;

    @NotNull(message = "Lütfen okul numaranızı giriniz")
    @Column(nullable = false,unique = true)
    private Long schoolNumber;

    @Size(max=5)
    @NotNull(message = "Lütfen sınıfınızı giriniz")
    @Column(nullable = false,length = 5)
    private String className;

    @Column(nullable = false)
    private Boolean builtIn=false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="student_roles",
               joinColumns=@JoinColumn(name="student_id"),
               inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles=new HashSet<>();
}

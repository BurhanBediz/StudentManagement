package com.TechProEd.StudentManagementSystem.domain;


import com.TechProEd.StudentManagementSystem.domain.enumeration.StudentRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private StudentRole name;

    @Override
    public String toString() {
        return
                " " + name +
                '}';
    }
}

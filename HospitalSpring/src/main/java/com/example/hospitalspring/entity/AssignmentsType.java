package com.example.hospitalspring.entity;

import javax.persistence.*;

@Entity
@Table(name = "assignments_types")
public class AssignmentsType {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @SequenceGenerator(name="assignments_types_generator", sequenceName = "assignments_types_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "assignments_types_generator")
    private Long id;

    private String type;

    public AssignmentsType() {
    }

    public AssignmentsType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.example.hospitalspring.entity;

import javax.persistence.*;

@Entity
@Table(name = "diagnosis")
public class Diagnosis {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @SequenceGenerator(name="diagnosis_generator", sequenceName = "diagnosis_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "diagnosis_generator")
    private Long id;

    private String type;

    public Diagnosis() {
    }

    public Diagnosis(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

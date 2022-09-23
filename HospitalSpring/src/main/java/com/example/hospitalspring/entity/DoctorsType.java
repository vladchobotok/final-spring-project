package com.example.hospitalspring.entity;

import javax.persistence.*;

@Entity
@Table(name = "doctors_types")
public class DoctorsType {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @SequenceGenerator(name="doctors_types_generator", sequenceName = "doctors_types_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "doctors_types_generator")
    private Long id;

    private String type;

    public DoctorsType() {
    }

    public DoctorsType(String type) {
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

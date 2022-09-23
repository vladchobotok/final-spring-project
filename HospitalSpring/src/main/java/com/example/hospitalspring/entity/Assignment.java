package com.example.hospitalspring.entity;

import javax.persistence.*;

@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @SequenceGenerator(name="assignments_generator", sequenceName = "assignments_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "assignments_generator")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id")
    private Doctor executor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prescriber_id")
    private Doctor prescriber;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assignments_type_id")
    private AssignmentsType type;

    public Assignment() {
    }

    public Assignment(Doctor executor, Doctor prescriber, String description, AssignmentsType type) {
        this.executor = executor;
        this.prescriber = prescriber;
        this.description = description;
        this.type = type;
    }

    public Doctor getExecutor() {
        return executor;
    }

    public void setExecutor(Doctor executor) {
        this.executor = executor;
    }

    public Doctor getPrescriber() {
        return prescriber;
    }

    public void setPrescriber(Doctor prescriber) {
        this.prescriber = prescriber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssignmentsType getType() {
        return type;
    }

    public void setType(AssignmentsType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

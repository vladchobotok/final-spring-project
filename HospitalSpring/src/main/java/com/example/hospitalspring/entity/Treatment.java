package com.example.hospitalspring.entity;

import javax.persistence.*;

@Entity
@Table(name = "treatments")
public class Treatment {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @SequenceGenerator(name="treatments_generator", sequenceName = "treatments_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "treatments_generator")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    public Treatment(Assignment assignment, Diagnosis diagnosis) {
        this.assignment = assignment;
        this.diagnosis = diagnosis;
    }

    public Treatment() {
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

package com.example.hospitalspring.entity;

import javax.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @SequenceGenerator(name="patients_generator", sequenceName = "patients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "patients_generator")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    public Patient(User user, Doctor doctor, Treatment treatment) {
        this.user = user;
        this.doctor = doctor;
        this.treatment = treatment;
    }

    public Patient() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

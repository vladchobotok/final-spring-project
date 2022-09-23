package com.example.hospitalspring.entity;

import javax.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @SequenceGenerator(name="doctors_generator", sequenceName = "doctors_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "doctors_generator")
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctors_type_id")
    private DoctorsType doctorsType;

    public Doctor(User user, DoctorsType doctorsType) {
        this.user = user;
        this.doctorsType = doctorsType;
    }

    public Doctor() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DoctorsType getDoctorsType() {
        return doctorsType;
    }

    public void setDoctorsType(DoctorsType doctorsType) {
        this.doctorsType = doctorsType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

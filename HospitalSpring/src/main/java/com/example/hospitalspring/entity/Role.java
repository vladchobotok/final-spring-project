package com.example.hospitalspring.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    PATIENT, DOCTOR, ADMIN, NURSE, CURED;

    @Override
    public String getAuthority() {
        return name();
    }
}

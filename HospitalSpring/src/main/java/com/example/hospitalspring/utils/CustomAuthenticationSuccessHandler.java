package com.example.hospitalspring.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    SimpleUrlAuthenticationSuccessHandler patientSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/patient");
    SimpleUrlAuthenticationSuccessHandler doctorSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/doctor");
    SimpleUrlAuthenticationSuccessHandler nurseSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/nurse");
    SimpleUrlAuthenticationSuccessHandler adminSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/admin");

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            switch (authorityName) {
                case "DOCTOR":
                    this.doctorSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                    return;
                case "NURSE":
                    this.nurseSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                    return;
                case "ADMIN":
                    this.adminSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                    return;
                case "PATIENT":
                default:
                    this.patientSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                    return;
            }
        }
    }
}
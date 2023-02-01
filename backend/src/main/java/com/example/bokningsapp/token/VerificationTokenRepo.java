package com.example.bokningsapp.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepo extends JpaRepository <VerificationToken, Long> {

    VerificationToken findByToken (String token);
}

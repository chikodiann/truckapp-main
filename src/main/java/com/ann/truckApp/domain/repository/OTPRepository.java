package com.ann.truckApp.domain.repository;

import com.ann.truckApp.domain.model.OTP;
import com.ann.truckApp.domain.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP,Long> {
    Optional<OTP> findByUsers(Users users);
}

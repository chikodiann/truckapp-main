package com.ann.truckApp.domain.repository;

import com.ann.truckApp.domain.enums.Type;
import com.ann.truckApp.domain.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByEmail(String email);
    Boolean existsByEmail(String email);

    Optional<Users> findUsersByType(Type type);

    Optional<List<Users>> findByType(Type type);


}

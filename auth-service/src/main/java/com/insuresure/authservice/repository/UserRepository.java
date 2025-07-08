package com.insuresure.authservice.repository;

import com.insuresure.authservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailEquals(String email);
    User save(User user);

}

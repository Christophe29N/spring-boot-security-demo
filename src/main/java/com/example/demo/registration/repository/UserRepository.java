package com.example.demo.registration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.registration.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddress(String emailAddress);
    
    @Query("select u from User u join fetch u.roles where u.emailAddress = :emailAddressParam")
    Optional<User> findByEmailAddressWithRoles(@Param("emailAddressParam") String emailAddress);
}

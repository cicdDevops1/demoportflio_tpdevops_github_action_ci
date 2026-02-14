package com.example.demoportflio_tpdevops_github_action_ci.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoportflio_tpdevops_github_action_ci.model.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySlug(String slug);
    //User findBySlug(String email);
    boolean existsByEmail(String email);
    boolean existsBySlug(String slug);
    Optional<Object> findByEmail(String email);
}


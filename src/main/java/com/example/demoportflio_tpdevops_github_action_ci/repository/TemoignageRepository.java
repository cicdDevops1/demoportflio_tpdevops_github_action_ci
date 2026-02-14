package com.example.demoportflio_tpdevops_github_action_ci.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Temoignage;

public interface TemoignageRepository extends JpaRepository<Temoignage, Long> {
}

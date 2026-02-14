package com.example.demoportflio_tpdevops_github_action_ci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Partenaire;

import java.util.List;

@Repository
public interface PartenaireRepository extends JpaRepository<Partenaire, Long> {
    List<Partenaire> findBySectionUserSlug(String slug);
}

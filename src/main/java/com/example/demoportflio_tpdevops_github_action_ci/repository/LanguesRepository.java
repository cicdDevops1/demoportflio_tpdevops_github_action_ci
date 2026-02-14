package com.example.demoportflio_tpdevops_github_action_ci.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Langues;

import java.util.List;

public interface LanguesRepository   extends JpaRepository<Langues, Long> {
    List<Langues> findBySectionUserSlug(String slug);
}

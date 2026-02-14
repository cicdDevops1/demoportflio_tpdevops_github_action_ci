package com.example.demoportflio_tpdevops_github_action_ci.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Apropos;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;


import java.util.Optional;


public interface AproposRepository  extends JpaRepository<Apropos, Long> {
   // boolean existsByEmail(String email);
    boolean existsByTelephone(String phone);
    Apropos findBySectionUserId(Long userId);

   Optional<Apropos> findBySectionUserSlugAndSectionSection(String slug, Section.Sections sections);
}

package com.example.demoportflio_tpdevops_github_action_ci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Atout;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;

import java.util.List;


@Repository
public interface AtoutRepository extends JpaRepository<Atout, Long> {
    //List<Atout> findBySectionUserSlugAndSectionType(String slug, Section.Sections sectionType);

    List<Atout> findBySectionUserSlugAndSectionSection(String slug, Section.Sections sectionSection);

}

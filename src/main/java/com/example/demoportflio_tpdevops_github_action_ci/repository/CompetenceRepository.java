package com.example.demoportflio_tpdevops_github_action_ci.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Competence;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;

import java.util.List;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    List<Competence> findByTypes(Competence.Type types);
    List<Competence> findByTypesAndSection_User_Slug(Competence.Type compType, String slug);
    List<Competence> findByGroupId(String groupId);
    boolean existsByNiveauAndDescriptionAndTypesAndSectionAndNom( String niveau,  String description, Competence.Type types, Section section,  String nom);
}

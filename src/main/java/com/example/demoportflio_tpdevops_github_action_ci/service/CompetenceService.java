package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.Competence;

public interface CompetenceService {
     List<Competence> findAllCompetence();
     List<Competence> createCompetence(Competence competence);
     Competence updateCompetence(Competence competence);
     List<Competence> deleteCompetence(String groupId);
     Competence findCompetenceById(Long id);
     List<Competence> findByType(String type, String slug);

}

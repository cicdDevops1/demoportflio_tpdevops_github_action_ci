package com.example.demoportflio_tpdevops_github_action_ci.service;



import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.Formation;

public interface FormationService {
    List<Formation> createFormation(Formation formation);
    List<Formation> getAllFormation();
    Formation getFormationById(Long id);
    Formation updateFormation(Formation formation);
    List<Formation> deleteFormation(String idGroup);
     List<Formation> findByType(String type, String slug);
}

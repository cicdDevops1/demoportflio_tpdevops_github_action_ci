package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.Experience;

public interface ExperienceService {
    List<Experience> createExperience(Experience experience);

    Experience getExperienceById(Long id);
    Experience updateExperience(Experience section);
    List<Experience> deleteExperience(String idGroup);
    List<Experience> findByType(String type,  String slug);
}

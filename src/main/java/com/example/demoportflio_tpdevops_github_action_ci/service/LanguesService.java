package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.Langues;

public interface LanguesService {
    Langues createLangues(Langues langues);
    List<Langues> getAllLangues();
    Langues getLanguesById(Long id);
    Langues updateLangues(Langues langues);
    Langues deleteLangues(Long id);
    List<Langues> getLanguesByUserSlug(String slug);

}

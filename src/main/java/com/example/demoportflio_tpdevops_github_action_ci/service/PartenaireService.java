package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.Partenaire;

public interface PartenaireService {

    Partenaire getPartenaireById(Long id);

    Partenaire deletePartenaire(Long id);
    Partenaire updatePartenaire(Partenaire partenaire, String slug);
    Partenaire createPartenaireForCurrentUser(Partenaire partenaire, String slug);
    List<Partenaire> findBySectionUserSlug(String slug);
}

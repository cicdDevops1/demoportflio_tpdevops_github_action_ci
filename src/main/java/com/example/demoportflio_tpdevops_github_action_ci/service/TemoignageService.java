package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.Temoignage;

public interface TemoignageService {

    List<Temoignage> listTemoignage();
    Temoignage getTemoignageById(Long id);
    Temoignage saveTemoignage(Temoignage temoignage);
    Temoignage deleteTemoignage(Long id);
     Temoignage updateTemoignage(Temoignage temoignage);
}

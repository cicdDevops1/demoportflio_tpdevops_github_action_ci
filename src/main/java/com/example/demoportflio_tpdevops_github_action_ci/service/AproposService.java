package com.example.demoportflio_tpdevops_github_action_ci.service;



import com.example.demoportflio_tpdevops_github_action_ci.model.Apropos;

public interface AproposService {
    Apropos createApropos(Apropos apropos);

    Apropos getAproposBySlug(String slug);
    Apropos updateApropos(Apropos apropos);
    Apropos deleteApropos(Long id);


}

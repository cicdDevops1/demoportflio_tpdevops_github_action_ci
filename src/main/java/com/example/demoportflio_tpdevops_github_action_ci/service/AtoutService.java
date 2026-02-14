package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.Atout;

public interface AtoutService {

     List<Atout> getAllSkills(String slug);
     Atout getAtoutById(Long id);
     Atout updateAtout(Atout to);
     Atout deleteAtout(Long id);
     Atout addAtout(Atout to);
}

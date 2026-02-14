package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.Hobbies;

public interface HobbiesService {
 List<Hobbies> getAllHobbies();
 Hobbies getHobbies(Long id);
 Hobbies addHobbies(Hobbies hobbies);
 Hobbies updateHobbies(Hobbies hobbies);
 Hobbies deleteHobbies(Long id);
 List<Hobbies> getHobbiesByUserSlug(String slug);
}

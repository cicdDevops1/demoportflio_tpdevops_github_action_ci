package com.example.demoportflio_tpdevops_github_action_ci.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.model.User;

import java.util.List;

public interface SectionRepository  extends JpaRepository<Section, Long> {
    List<Section> findByUser(User user);


}

package com.example.demoportflio_tpdevops_github_action_ci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Hobbies;

import java.util.List;

@Repository
public interface HobbiesRRepository extends JpaRepository<Hobbies, Long> {
   List<Hobbies> findBySectionUserSlug(String slug);
}

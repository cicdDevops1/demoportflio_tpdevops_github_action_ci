package com.example.demoportflio_tpdevops_github_action_ci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Blog;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findBySectionUserSlug(String slug);
    List<Blog> findBySectionUserSlugAndSectionSection(String slug, Section.Sections section);

    //List<Blog> findBySectionUserSlugAndSectionSection(String section_user_slug, Section.Sections section_section);
}

package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.Section;

public interface SectionService {
    Section createSectionForCurrentUser(Section section, String slug);
    List<Section> getSections();
    Section getSectionById(Long id);
    Section updateSection(Section section);
    Section deleteSection(Long id);
    List<Section> getSectionsByUserSlug(String slug);
    public boolean slugExists(String slug);
}

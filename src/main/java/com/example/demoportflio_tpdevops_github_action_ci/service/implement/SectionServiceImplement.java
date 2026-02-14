package com.example.demoportflio_tpdevops_github_action_ci.service.implement;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.AccessDeniedException;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.model.User;

import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.UserRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.SectionService;

import java.util.Collections;
import java.util.List;

@Service
public class SectionServiceImplement implements SectionService {

    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final AuthService authService;
    //private final UserPrincipal userPrincipal;

    public SectionServiceImplement(SectionRepository sectionRepository, UserRepository userRepository, MessageSource messageSource) {
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.messageSource = messageSource;

        this.authService = new AuthService();
        //this.userPrincipal = userPrincipal;
    }

    @Override
    public Section createSectionForCurrentUser(Section section, String slug) {
        User currentUser = authService.getCurrentUser();

        if (!currentUser.getSlug().equals(slug)) {
            throw new AccessDeniedException( messageSource.getMessage("section.notfound",null, LocaleContextHolder.getLocale()));
        }

        section.setUser(currentUser);
        return sectionRepository.save(section);
    }


    @Override
    public List<Section> getSections() {
        return sectionRepository.findAll();
    }

    @Override
    public Section getSectionById(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public Section updateSection(Section section) {
        User currentUser = authService.getCurrentUser();

        Section existingSection = sectionRepository.findById(section.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound",
                                new Object[]{section.getId()},
                                LocaleContextHolder.getLocale())
                ));
                if (!existingSection.getUser().getId().equals(currentUser.getId())) {
                    throw new AccessDeniedException(messageSource.getMessage("section.untorize",
                            null,
                                   LocaleContextHolder.getLocale()));
                }
                existingSection.setSection(section.getSection());
                return sectionRepository.save(existingSection);
          }
    @Override
    public boolean slugExists(String slug) {
        return userRepository.existsBySlug(slug);
    }
    @Override
    public Section deleteSection(Long id) {
        User currentUser =authService.getCurrentUser();
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound",
                                new Object[]{id},
                                LocaleContextHolder.getLocale())
                ));
        if (!section.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("section.unauthorized",
                            null,
                            LocaleContextHolder.getLocale())
            );
        }
        sectionRepository.delete(section);
        return section;
    }

    @Override
    public List<Section> getSectionsByUserSlug(String slug) {
        User user = userRepository.findBySlug(slug)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("user.notfound", null, LocaleContextHolder.getLocale())
                ));
        List<Section> sections = sectionRepository.findByUser(user);
        return sections.isEmpty() ? Collections.emptyList() : sections;
    }



}

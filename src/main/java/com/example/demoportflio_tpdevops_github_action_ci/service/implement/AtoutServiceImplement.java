package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.AccessDeniedException;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Atout;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.model.User;
import com.example.demoportflio_tpdevops_github_action_ci.repository.AtoutRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.AtoutService;

import java.util.List;

@Service
public class AtoutServiceImplement implements AtoutService {

    private final AtoutRepository atoutRepository;
    private final SectionRepository sectionRepository;
    private final MessageSource messageSource;
    private final AuthService authService;

    public AtoutServiceImplement(AtoutRepository atoutRepository,
                                 SectionRepository sectionRepository,
                                 MessageSource messageSource,
                                 AuthService authService) {
        this.atoutRepository = atoutRepository;
        this.sectionRepository = sectionRepository;
        this.messageSource = messageSource;
        this.authService = authService;
    }

    // 🔹 Lister tous les Atouts d’un utilisateur pour une section spécifique
    @Override
    public List<Atout> getAllSkills(String slug) {

        return atoutRepository.findBySectionUserSlugAndSectionSection(slug , Section.Sections.Loisirs);
    }

    @Override
    public Atout getAtoutById(Long id) {
        return atoutRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("atout.notfound.id", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public Atout addAtout(Atout atout) {
        User currentUser = authService.getCurrentUser();
        Long sectionId = atout.getSection().getId(); // récupère l'ID envoyé dans le JSON

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{sectionId}, LocaleContextHolder.getLocale())
                ));


        if (!section.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("atout.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }

        atout.setSection(section);
        return atoutRepository.save(atout);
    }

    @Override
    public Atout updateAtout(Atout atout) {
        Atout existing = atoutRepository.findById(atout.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("atout.notfound.id", new Object[]{atout.getId()}, LocaleContextHolder.getLocale())
                ));

        User currentUser = authService.getCurrentUser();
        checkOwnership(currentUser, existing);

        existing.setDescription(atout.getDescription());
       existing.setTitle(atout.getTitle());




        return atoutRepository.save(existing);
    }

    @Override
    public Atout deleteAtout(Long id) {
        Atout existing = atoutRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("atout.notfound.id", new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        User currentUser = authService.getCurrentUser();
        checkOwnership(currentUser, existing);

        atoutRepository.delete(existing);
        return existing;
    }

    // 🔹 Vérifie que l'Atout appartient bien à l’utilisateur
    private void checkOwnership(User currentUser, Atout atout) {
        if (!atout.getSection().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("atout.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }
    }
}

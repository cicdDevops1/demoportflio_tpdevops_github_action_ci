package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.AccessDeniedException;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Langues;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.model.User;
import com.example.demoportflio_tpdevops_github_action_ci.repository.LanguesRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.LanguesService;

import java.util.List;

@Service
public class LanguesServiceImplement implements LanguesService {

    private final SectionRepository sectionRepository;
    private final LanguesRepository languesRepository;
    private final MessageSource messageSource;
    private final AuthService authService;

    public LanguesServiceImplement(SectionRepository sectionRepository,
                                   LanguesRepository languesRepository,
                                   MessageSource messageSource,
                                   AuthService authService) {
        this.sectionRepository = sectionRepository;
        this.languesRepository = languesRepository;
        this.messageSource = messageSource;
        this.authService = authService;
    }

    @Override
    public Langues createLangues(Langues langues) {
        Long sectionId = langues.getSection().getId();
        Section sectionFromDb = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        "Section introuvable avec l'ID : " + sectionId
                ));

        // Vérification du propriétaire
        User currentUser = authService.getCurrentUser();
        if (!sectionFromDb.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("langue.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }

        langues.setSection(sectionFromDb);
        return languesRepository.save(langues);
    }

    @Override
    public List<Langues> getAllLangues() {
        return languesRepository.findAll();
    }

    @Override
    public Langues getLanguesById(Long id) {
        return languesRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("langue.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public Langues updateLangues(Langues langues) {
        Langues existing = languesRepository.findById(langues.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("langue.notfound", new Object[]{langues.getId()}, LocaleContextHolder.getLocale())
                ));

        User currentUser = authService.getCurrentUser();
        if (!existing.getSection().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("langue.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }

        existing.setNom(langues.getNom());
        existing.setNiveau(langues.getNiveau());
        // Ajoute d'autres champs si nécessaire

        return languesRepository.save(existing);
    }

    @Override
    public Langues deleteLangues(Long id) {
        Langues langues = languesRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("langue.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        User currentUser = authService.getCurrentUser();
        if (!langues.getSection().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("langue.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }

        languesRepository.delete(langues);
        return langues;
    }


    @Override
    public List<Langues> getLanguesByUserSlug(String slug) {
        return languesRepository.findBySectionUserSlug(slug);
    }
}

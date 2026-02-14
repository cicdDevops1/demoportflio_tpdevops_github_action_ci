package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.AccessDeniedException;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Hobbies;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.model.User;
import com.example.demoportflio_tpdevops_github_action_ci.repository.HobbiesRRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.HobbiesService;

import java.util.List;

@Service
public class HobbiesImplementService implements HobbiesService {

    private final HobbiesRRepository hobbiesRRepository;
    private final SectionRepository sectionRepository;
    private final MessageSource messageSource;
    private final AuthService authService;

    public HobbiesImplementService(HobbiesRRepository hobbiesRRepository,
                                   SectionRepository sectionRepository,
                                   MessageSource messageSource,
                                   AuthService authService) {
        this.hobbiesRRepository = hobbiesRRepository;
        this.sectionRepository = sectionRepository;
        this.messageSource = messageSource;
        this.authService = authService;
    }

    @Override
    public Hobbies addHobbies(Hobbies hobbies) {
        Long sectionId = hobbies.getSection().getId();
        Section sectionFromDb = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{sectionId}, LocaleContextHolder.getLocale())
                ));

        // Vérification du propriétaire
        User currentUser = authService.getCurrentUser();
        if (!sectionFromDb.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("hobbies.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }

        hobbies.setSection(sectionFromDb);
        return hobbiesRRepository.save(hobbies);
    }

    @Override
    public Hobbies updateHobbies(Hobbies hobbies) {
        Hobbies existing = hobbiesRRepository.findById(hobbies.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("hobbies.notfound", new Object[]{hobbies.getId()}, LocaleContextHolder.getLocale())
                ));

        User currentUser = authService.getCurrentUser();
        if (!existing.getSection().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("hobbies.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }

        existing.setNom(hobbies.getNom());
        existing.setDescription(hobbies.getDescription());
        // ajouter d'autres champs si nécessaire

        return hobbiesRRepository.save(existing);
    }

    @Override
    public Hobbies deleteHobbies(Long id) {
        Hobbies hobbies = hobbiesRRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("hobbies.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        User currentUser = authService.getCurrentUser();
        if (!hobbies.getSection().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("hobbies.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }

        hobbiesRRepository.delete(hobbies);
        return hobbies;
    }

    @Override
    public Hobbies getHobbies(Long id) {
        return hobbiesRRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("hobbies.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public List<Hobbies> getAllHobbies() {
        return hobbiesRRepository.findAll();
    }

    @Override
    public List<Hobbies> getHobbiesByUserSlug(String slug) {
        return hobbiesRRepository.findBySectionUserSlug(slug);
    }
}

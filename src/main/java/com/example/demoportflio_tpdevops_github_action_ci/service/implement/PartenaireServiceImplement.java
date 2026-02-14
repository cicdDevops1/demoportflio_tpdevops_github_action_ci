package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.AccessDeniedException;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Partenaire;

import com.example.demoportflio_tpdevops_github_action_ci.model.User;
import com.example.demoportflio_tpdevops_github_action_ci.repository.PartenaireRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.PartenaireService;

import java.util.List;

@Service
public class PartenaireServiceImplement implements PartenaireService {

    private final PartenaireRepository partenaireRepository;
    private final MessageSource messageSource;
    private final AuthService authService;

    public PartenaireServiceImplement(PartenaireRepository partenaireRepository, MessageSource messageSource, AuthService authService) {
        this.partenaireRepository = partenaireRepository;
        this.messageSource = messageSource;
        this.authService = authService;

    }






    @Override
    public Partenaire getPartenaireById(Long id) {
        return partenaireRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("partenaire.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }











    @Override
    public Partenaire deletePartenaire(Long id) {

        Partenaire partenaire = partenaireRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("partenaire.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
        User currentUser = authService.getCurrentUser();

        if (!partenaire.getSection().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("partenaire.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }

        partenaireRepository.delete(partenaire);
        return partenaire;
    }

    /*@Override
    public Partenaire updatePartenaire(Partenaire c) {
        Partenaire existing = partenaireRepository.findById(c.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("partenaire.notfound", new Object[]{c.getId()}, LocaleContextHolder.getLocale())
                ));

        User currentUser = authService.getCurrentUser();

        if (!existing.getSection().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("partenaire.untorize", null, LocaleContextHolder.getLocale())
            );
        }
        existing.setNom(c.getNom());
        existing.setDescription(c.getDescription());
        existing.setLogo(c.getLogo());
        existing.setUrl(c.getUrl());
        return partenaireRepository.save(existing);
    }*/
@Override
    public Partenaire updatePartenaire(Partenaire partenaire, String slug) {

    Partenaire existing = partenaireRepository.findById(partenaire.getId())
            .orElseThrow(() -> new RuntimeException("Partenaire introuvable"));

    User currentUser = authService.getCurrentUser();

    if (!existing.getSection().getUser().getId().equals(currentUser.getId())) {
        throw new RuntimeException("partenaire.accessdenied");
    }

    existing.setNom(partenaire.getNom());
    existing.setDescription(partenaire.getDescription());
    existing.setLogo(partenaire.getLogo());
    existing.setUrl(partenaire.getUrl());
        return partenaireRepository.save(existing);
    }



    @Override
    public Partenaire createPartenaireForCurrentUser(Partenaire partenaire, String slug) {
        User currentUser = authService.getCurrentUser();
        if (!currentUser.getSlug().equals(slug)) {
            throw new AccessDeniedException(
                    messageSource.getMessage("partenaire.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }
        if (partenaire.getSection() == null) {
            throw new AccessDeniedException( messageSource.getMessage("section.notfound", null, LocaleContextHolder.getLocale()));
        }
        partenaire.getSection().setUser(currentUser);
        return partenaireRepository.save(partenaire);
    }

    @Override
    public List<Partenaire> findBySectionUserSlug(String slug) {
        return partenaireRepository.findBySectionUserSlug(slug);
    }


}

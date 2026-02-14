package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.AccessDeniedException;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Apropos;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.model.User;
import com.example.demoportflio_tpdevops_github_action_ci.repository.AproposRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.AproposService;


@Service
public class AproposServiceImplement implements AproposService {

    private final AproposRepository aproposRepository;
    private final SectionRepository sectionRepository;
    private final AuthService authService;
    private final MessageSource messageSource;

    public AproposServiceImplement(AproposRepository aproposRepository,
                                   SectionRepository sectionRepository,
                                   AuthService authService,
                                   MessageSource messageSource) {
        this.aproposRepository = aproposRepository;
        this.sectionRepository = sectionRepository;
        this.authService = authService;
        this.messageSource = messageSource;
    }

    // 🔹 Récupère l'Apropos d'un utilisateur par slug
    @Override
    public Apropos  getAproposBySlug(String slug) {
        Apropos apropos = aproposRepository.findBySectionUserSlugAndSectionSection(slug, Section.Sections.Apropos)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("apropos.notfound", new Object[]{slug}, LocaleContextHolder.getLocale())
                ));
        return apropos;
    }


    @Override
    public Apropos createApropos(Apropos apropos) {
        User currentUser = authService.getCurrentUser();
        Section section = sectionRepository.findById(apropos.getSection().getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{apropos.getSection().getId()}, LocaleContextHolder.getLocale())
                ));

        checkOwnership(currentUser, section);

        apropos.setSection(section);
        return aproposRepository.save(apropos);
    }

    @Override
    public Apropos updateApropos(Apropos apropos) {
        Apropos existing = aproposRepository.findById(apropos.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("apropos.notfound.id", new Object[]{apropos.getId()}, LocaleContextHolder.getLocale())
                ));

        User currentUser = authService.getCurrentUser();
        checkOwnership(currentUser, existing);

        existing.setProfile(apropos.getProfile());
        existing.setTitre(apropos.getTitre());
        existing.setNom(apropos.getNom());

        existing.setPhoto(apropos.getPhoto());
        existing.setGit(apropos.getGit());
        existing.setLinkedin(apropos.getLinkedin());
        existing.setEmail(apropos.getEmail());

        existing.setTelephone(apropos.getTelephone());

        existing.setLocalisation(apropos.getLocalisation());
        existing.setNiveau(apropos.getNiveau());

        return aproposRepository.save(existing);
    }

    @Override
    public Apropos deleteApropos(Long id) {
        Apropos existing = aproposRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("apropos.notfound.id", new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        User currentUser = authService.getCurrentUser();
        checkOwnership(currentUser, existing);

        aproposRepository.delete(existing);
        return existing;
    }

    // 🔹 Vérifie que l'Apropos appartient bien à l’utilisateur
    private void checkOwnership(User currentUser, Apropos apropos) {
        if (!apropos.getSection().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("apropos.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }
    }

    private void checkOwnership(User currentUser, Section section) {
        if (!section.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    messageSource.getMessage("apropos.accessdenied", null, LocaleContextHolder.getLocale())
            );
        }
    }
}

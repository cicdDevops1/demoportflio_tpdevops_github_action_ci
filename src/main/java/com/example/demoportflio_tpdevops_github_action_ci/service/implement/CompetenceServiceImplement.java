package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.AccessDeniedException;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Competence;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.repository.CompetenceRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.CompetenceService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class CompetenceServiceImplement implements CompetenceService {
    private final CompetenceRepository competenceRepository;
    private final SectionRepository sectionRepository;
    private final MessageSource messageSource;

    public CompetenceServiceImplement(CompetenceRepository competenceRepository,
                                      SectionRepository sectionRepository,
                                      MessageSource messageSource) {
        this.competenceRepository = competenceRepository;
        this.sectionRepository = sectionRepository;
        this.messageSource = messageSource;
    }

    private String getCurrentUserSlug() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public List<Competence> findAllCompetence() {
        return competenceRepository.findAll();
    }

    @Override
    public List<Competence> createCompetence(Competence competence) {
        if (competence.getSection() == null) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("section.missing", null, LocaleContextHolder.getLocale())
            );
        }

        Long sectionId = competence.getSection().getId();
        Section sectionFromDb = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{sectionId}, LocaleContextHolder.getLocale())
                ));

        // 🔒 Vérifie que la section appartient à l’utilisateur connecté
        if (!sectionFromDb.getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("competence.unautorize");
        }

        boolean exists = competenceRepository.existsByNiveauAndDescriptionAndTypesAndSectionAndNom(
                competence.getNiveau(),
                competence.getDescription(),
                competence.getTypes(),
                sectionFromDb,
                competence.getNom()
        );

        if (exists) {
            throw new ApiExecptionHandler.UserAlreadyExistsException(
                    messageSource.getMessage("competence.exists", null, LocaleContextHolder.getLocale())
            );
        }

        List<Competence> savedCompetences = new ArrayList<>();

        if (competence.getTypes() == Competence.Type.BOTH) {
            String groupId = UUID.randomUUID().toString();

            Competence compCV = cloneCompetence(competence, Competence.Type.CV, sectionFromDb);
            compCV.setGroupId(groupId);
            savedCompetences.add(competenceRepository.save(compCV));

            Competence compPortfolio = cloneCompetence(competence, Competence.Type.PORTFOLIO, sectionFromDb);
            compPortfolio.setGroupId(groupId);
            savedCompetences.add(competenceRepository.save(compPortfolio));
        } else {
            competence.setSection(sectionFromDb);
            savedCompetences.add(competenceRepository.save(competence));
        }

        return savedCompetences;
    }

    @Override
    public Competence updateCompetence(Competence competence) {
        Competence existing = competenceRepository.findById(competence.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("competence.notfound.id", new Object[]{competence.getId()}, LocaleContextHolder.getLocale())
                ));

        // 🔒 Vérifie que l’utilisateur connecté est le propriétaire
        if (!existing.getSection().getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("competence.update-unautorize");
        }

        existing.setNom(competence.getNom());
        existing.setNiveau(competence.getNiveau());
        existing.setDescription(competence.getDescription());
        existing.setTypes(competence.getTypes());

        return competenceRepository.save(existing);
    }

    @Override
    public List<Competence> deleteCompetence(String groupId) {
        if (groupId == null || groupId.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("groupId.invalid", null, LocaleContextHolder.getLocale())
            );
        }

        List<Competence> toDelete = competenceRepository.findByGroupId(groupId);
        if (toDelete.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("competence.notfound.groupId", new Object[]{groupId}, LocaleContextHolder.getLocale())
            );
        }

        // 🔒 Vérifie que l’utilisateur connecté est le propriétaire
        if (!toDelete.get(0).getSection().getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("competence.delete-unautorize");
        }

        competenceRepository.deleteAll(toDelete);
        return toDelete;
    }

    @Override
    public Competence findCompetenceById(Long id) {
        return competenceRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("competence.notfound.id", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public List<Competence> findByType(String type, String slug) {
        Competence.Type compType;
        try {
            compType = Competence.Type.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("type.invalide", new Object[]{type}, LocaleContextHolder.getLocale())
            );
        }

        List<Competence> competences = competenceRepository.findByTypesAndSection_User_Slug(compType, slug);

        if (competences.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("competence.notfound.type", new Object[]{compType}, LocaleContextHolder.getLocale())
            );
        }

        return competences;
    }

    private Competence cloneCompetence(Competence source, Competence.Type type, Section section) {
        Competence target = new Competence();
        target.setNom(source.getNom());
        target.setNiveau(source.getNiveau());
        target.setTypes(type);
        target.setDescription(source.getDescription());
        target.setSection(section);
        return target;
    }
}

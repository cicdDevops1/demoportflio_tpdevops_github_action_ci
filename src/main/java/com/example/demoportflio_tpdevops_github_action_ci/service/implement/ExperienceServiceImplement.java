package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.AccessDeniedException;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Experience;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.repository.ExperienceRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.ExperienceService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Service
public class ExperienceServiceImplement  implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final SectionRepository sectionRepository;
    private final MessageSource messageSource;

    public ExperienceServiceImplement(ExperienceRepository experienceRepository,
                                      SectionRepository sectionRepository,
                                      MessageSource messageSource) {
        this.experienceRepository = experienceRepository;
        this.sectionRepository = sectionRepository;
        this.messageSource = messageSource;
    }

    // ✅ Récupère le slug de l’utilisateur connecté
    private String getCurrentUserSlug() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    @Override public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id) .orElseThrow(() ->
                new ApiExecptionHandler.UserNotFoundException( messageSource.getMessage("experience.notfound.id",
                        null, LocaleContextHolder.getLocale()) )); }
    @Override
    public List<Experience> findByType(String type, String slug) {
        Experience.Type expType;
        try {
            expType = Experience.Type.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("type.invalide", new Object[]{type}, LocaleContextHolder.getLocale())
            );
        }

        List<Experience> experiences = experienceRepository.findByTypesAndSection_User_Slug(expType, slug);

        if (experiences.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("experience.notfound", new Object[]{expType}, LocaleContextHolder.getLocale())
            );
        }

        return experiences;
    }

    @Override
    public List<Experience> createExperience(Experience experience) {
        if (experience.getSection() == null) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("section.missing", null, LocaleContextHolder.getLocale())
            );
        }

        Long sectionId = experience.getSection().getId();
        Section sectionFromDB = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{sectionId}, LocaleContextHolder.getLocale())
                ));

        // 🔒 Vérifie que la section appartient à l’utilisateur connecté
        if (!sectionFromDB.getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("experience.unautorize");
        }

        boolean exists = experienceRepository.existsByTitleAndTypesAndDescriptionAndLocalisationAndDateDebutAndDateFinAndMessionAndRealisationAndEntrepriseAndSection(
                experience.getTitle(),
                experience.getTypes(),
                experience.getDescription(),
                experience.getLocalisation(),
                experience.getDateDebut(),
                experience.getDateFin(),
                experience.getMession(),
                experience.getRealisation(),
                experience.getEntreprise(),
                sectionFromDB
        );

        if (exists) {
            throw new ApiExecptionHandler.UserAlreadyExistsException(
                    messageSource.getMessage("experience.exists", null, LocaleContextHolder.getLocale())
            );
        }

        List<Experience> savedExperiences = new ArrayList<>();

        if (experience.getTypes() == Experience.Type.BOTH) {
            String groupId = UUID.randomUUID().toString();

            Experience experCV = cloneExperience(experience, Experience.Type.CV, sectionFromDB);
            experCV.setGroupId(groupId);
            savedExperiences.add(experienceRepository.save(experCV));

            Experience experPortfolio = cloneExperience(experience, Experience.Type.PORTFOLIO, sectionFromDB);
            experPortfolio.setGroupId(groupId);
            savedExperiences.add(experienceRepository.save(experPortfolio));
        } else {
            experience.setSection(sectionFromDB);
            savedExperiences.add(experienceRepository.save(experience));
        }

        return savedExperiences;
    }

    @Override
    public Experience updateExperience(Experience experience) {
        Experience existing = experienceRepository.findById(experience.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("experience.notfound.id", new Object[]{experience.getId()}, LocaleContextHolder.getLocale())
                ));

        // 🔒 Vérifie que l’utilisateur connecté est le propriétaire
        if (!existing.getSection().getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("experience.update-unautorize");
        }

        ExperienceDuplicateExist(experience, existing);
        existing.setDescription(experience.getDescription());

        return experienceRepository.save(existing);
    }

    private void ExperienceDuplicateExist(Experience experience, Experience existing) {
        existing.setTitle(experience.getTitle());
        existing.setEntreprise(experience.getEntreprise());
        existing.setDateDebut(experience.getDateDebut());
        existing.setDateFin(experience.getDateFin());
        existing.setLocalisation(experience.getLocalisation());
        existing.setMession(experience.getMession());
        existing.setRealisation(experience.getRealisation());
        existing.setLogo(experience.getLogo());
    }

    @Override
    public List<Experience> deleteExperience(String groupId) {
        if (groupId == null || groupId.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("experience.notfound.groupId", new Object[]{groupId}, LocaleContextHolder.getLocale())
            );
        }

        List<Experience> deletedExpers = experienceRepository.findByGroupId(groupId);

        if (deletedExpers.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("experience.notfound.groupId", new Object[]{groupId}, LocaleContextHolder.getLocale())
            );
        }

        // 🔒 Vérifie que l’utilisateur connecté est le propriétaire
        if (!deletedExpers.get(0).getSection().getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("experience.delete-unautorize");
        }

        experienceRepository.deleteAll(deletedExpers);
        return deletedExpers;
    }

    private Experience cloneExperience(Experience source, Experience.Type type, Section section) {
        Experience target = new Experience();
        ExperienceDuplicateExist(source, target);
        target.setTypes(type);
        target.setDescription(source.getDescription());
        target.setSection(section);
        target.setLien(source.getLien());

        if (type == Experience.Type.PORTFOLIO) {
            target.setMedia(source.getMedia());
            target.setLogo(source.getLogo());
        } else if (type == Experience.Type.CV) {
            target.setMedia(null);
            target.setLogo(null);
        }

        return target;
    }

}

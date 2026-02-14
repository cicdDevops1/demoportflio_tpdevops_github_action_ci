package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.AccessDeniedException;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Formation;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.repository.FormationRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.FormationService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FormationServiceImplement implements FormationService {

    private final FormationRepository formationRepository;
    private final SectionRepository sectionRepository;
    private final MessageSource messageSource;

    public FormationServiceImplement(FormationRepository formationRepository, SectionRepository sectionRepository, MessageSource messageSource) {
        this.formationRepository = formationRepository;
        this.sectionRepository = sectionRepository;
        this.messageSource = messageSource;
    }

    // ✅ Récupère le slug de l’utilisateur connecté
    private String getCurrentUserSlug() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public List<Formation> findByType(String type, String slug) {
        Formation.Type formaType;
        try {
            formaType = Formation.Type.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("type.invalide", new Object[]{type}, LocaleContextHolder.getLocale())
            );
        }
        List<Formation> formations = formationRepository.findByTypesAndSection_User_Slug(formaType, slug);

        if (formations.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("formation.notfound", new Object[]{formaType}, LocaleContextHolder.getLocale())
            );
        }

        return formations;
    }

    @Override
    public List<Formation> createFormation(Formation formation) {
        if (formation.getSection() == null) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("section.missing", new Object[]{formation.getId()}, LocaleContextHolder.getLocale()));
        }

        Long formationId = formation.getSection().getId();
        Section experienceFromDB = sectionRepository.findById(formationId)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{formation.getId()}, LocaleContextHolder.getLocale())));

        // 🔒 Vérifie que la section appartient bien à l’utilisateur connecté
        if (!experienceFromDB.getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("formation.unautorize");
        }

        boolean exists = formationRepository.existsByTitreAndDescriptionAndOrganismeAndLieuAndDateDebutAndDateFinAndSigleAndCertificatIsAndDiplomate(
                formation.getTitre(),
                formation.getDescription(),
                formation.getOrganisme(),
                formation.getLieu(),
                formation.getDateDebut(),
                formation.getDateFin(),
                formation.getSigle(),
                formation.isCertificat(),
                formation.isDiplomate()
        );
        if (exists) {
            throw new ApiExecptionHandler.UserAlreadyExistsException(
                    messageSource.getMessage("formation.exists", new Object[]{formation.getId()}, LocaleContextHolder.getLocale()));
        }

        List<Formation> savedFormations = new ArrayList<>();

        if (formation.getTypes() == Formation.Type.BOTH) {
            String groupId = UUID.randomUUID().toString();
            Formation formaCV = cloneFormation(formation, Formation.Type.CV, experienceFromDB);
            formaCV.setGroupId(groupId);
            savedFormations.add(formationRepository.save(formaCV));

            Formation experPortfolio = cloneFormation(formation, Formation.Type.PORTFOLIO, experienceFromDB);
            experPortfolio.setGroupId(groupId);
            savedFormations.add(formationRepository.save(experPortfolio));
        } else {
            formation.setSection(experienceFromDB);
            savedFormations.add(formationRepository.save(formation));
        }
        return savedFormations;
    }

    @Override
    public List<Formation> getAllFormation() {
        return formationRepository.findAll();
    }

    @Override
    public Formation getFormationById(Long id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("formation.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public Formation updateFormation(Formation formation) {
        Formation existing = formationRepository.findById(formation.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("formation.notfound", new Object[]{formation.getId()}, LocaleContextHolder.getLocale())
                ));

        if (!existing.getSection().getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("formation.update-unautorize");
        }

        existing.setTitre(formation.getTitre());
        existing.setDescription(formation.getDescription());
        existing.setOrganisme(formation.getOrganisme());
        existing.setLieu(formation.getLieu());
        existing.setDateDebut(formation.getDateDebut());
        existing.setDateFin(formation.getDateFin());
        existing.setSigle(formation.getSigle());
        existing.setLogo(formation.getLogo());
        existing.setCertificat(formation.isCertificat());
        existing.setDiplomate(formation.isDiplomate());

        return formationRepository.save(existing);
    }


    @Override
    public List<Formation> deleteFormation(String groupId) {
        if (groupId == null || groupId.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("groupId.invalid", new Object[]{groupId}, LocaleContextHolder.getLocale()));
        }
        List<Formation> deletedFormations = formationRepository.findByGroupId(groupId);
        if (deletedFormations.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("formation.notfound", new Object[]{groupId}, LocaleContextHolder.getLocale()));
        }


        String currentSlug = getCurrentUserSlug();
        if (!deletedFormations.get(0).getSection().getUser().getSlug().equals(currentSlug)) {
            throw new AccessDeniedException("formation.delete-unautorize");
        }

        formationRepository.deleteAll(deletedFormations);
        return deletedFormations;
    }

    private Formation cloneFormation(Formation source, Formation.Type type, Section section) {
        Formation target = new Formation();
        target.setTitre(source.getTitre());
        target.setOrganisme(source.getOrganisme());
        target.setDateDebut(source.getDateDebut());
        target.setDateFin(source.getDateFin());
        target.setDescription(source.getDescription());
        target.setSection(section);
        target.setLieu(source.getLieu());
        target.setSigle(source.getSigle());
        target.setLieu(source.getLieu());
        target.setTypes(type);
        if (type == Formation.Type.PORTFOLIO) {
            target.setLogo(source.getLogo());
            target.setCertificat(source.isCertificat());
            target.setDiplomate(source.isDiplomate());
        } else if (type == Formation.Type.CV) {
            target.setLogo(null);
        }
        return target;
    }
}

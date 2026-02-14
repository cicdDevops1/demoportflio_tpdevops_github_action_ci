package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.AccessDeniedException;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Certification;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.repository.CertificationRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.CertificationService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CertificationServiceImplement implements CertificationService {

    private final CertificationRepository certificationRepository;
    private final SectionRepository sectionRepository;
    private final MessageSource messageSource;

    public CertificationServiceImplement(CertificationRepository certificationRepository,
                                         SectionRepository sectionRepository,
                                         MessageSource messageSource) {
        this.certificationRepository = certificationRepository;
        this.sectionRepository = sectionRepository;
        this.messageSource = messageSource;
    }

    // 🔒 Récupère le slug de l’utilisateur connecté
    private String getCurrentUserSlug() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public Certification findCertificationById(Long id) {
        Certification cert = certificationRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("certification.notfound.id", new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        if (!cert.getSection().getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("certification.update-unautorize");
        }
        return cert;
    }



    @Override
    @Transactional
    public List<Certification> saveCertification(Certification certification) {
        Section section = validateSection(certification.getSection());

        // 🔒 Vérifie que la section appartient à l'utilisateur connecté
        if (!section.getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("certification.unautorize");
        }

        if (certificationRepository.existsByTitreAndOrganismeAndDateAndTypesAndSection(
                certification.getTitre(),
                certification.getOrganisme(),
                certification.getDate(),
                certification.getTypes(),
                section)) {
            throw new ApiExecptionHandler.UserAlreadyExistsException(
                    messageSource.getMessage("certification.exists", null, LocaleContextHolder.getLocale())
            );
        }

        List<Certification> savedCertifications = new ArrayList<>();
        if (certification.getTypes() == Certification.Type.BOTH) {
            String groupId = UUID.randomUUID().toString();
            savedCertifications.add(certificationRepository.save(cloneCertification(certification, Certification.Type.CV, section, groupId)));
            savedCertifications.add(certificationRepository.save(cloneCertification(certification, Certification.Type.PORTFOLIO, section, groupId)));
        } else {
            certification.setSection(section);
            savedCertifications.add(certificationRepository.save(certification));
        }

        return savedCertifications;
    }

    @Override
    public List<Certification> findByType(String type, String slug) {
        Certification.Type certType;
        try {
            certType = Certification.Type.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("type.invalide", new Object[]{type}, LocaleContextHolder.getLocale())
            );
        }

        List<Certification> certs = certificationRepository.findByTypesAndSection_User_Slug(certType, slug);

        if (certs.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("certification.notfound.type", new Object[]{certType}, LocaleContextHolder.getLocale())
            );
        }

        return certs;
    }


    @Override
    public List<Certification> deleteCertification(String groupId) {
        if (groupId == null || groupId.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("groupId.invalid", null, LocaleContextHolder.getLocale())
            );
        }

        List<Certification> deletedCerts = certificationRepository.findByGroupId(groupId);
        if (deletedCerts.isEmpty()) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("certification.notfound.groupId", new Object[]{groupId}, LocaleContextHolder.getLocale())
            );
        }

        if (!deletedCerts.get(0).getSection().getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("certification.delete-unautorize");
        }

        certificationRepository.deleteAll(deletedCerts);
        return deletedCerts;
    }

    @Override
    public Certification updateCertification(Certification certification) {
        Certification existing = certificationRepository.findById(certification.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("certification.notfound.id", new Object[]{certification.getId()}, LocaleContextHolder.getLocale())
                ));

        // 🔒 Vérifie que la section appartient à l'utilisateur connecté
        if (!existing.getSection().getUser().getSlug().equals(getCurrentUserSlug())) {
            throw new AccessDeniedException("certification.update-unautorize");
        }

        existing.setTitre(certification.getTitre());
        existing.setOrganisme(certification.getOrganisme());
        existing.setDate(certification.getDate());
        existing.setTypes(certification.getTypes());
        existing.setDescription(certification.getDescription());
        existing.setUrl(certification.getUrl());
        existing.setDateDexpiration(certification.getDateDexpiration());
        existing.setNiveau(certification.getNiveau());
        existing.setPdf(certification.getPdf());
        existing.setLogo(certification.getLogo());

        return certificationRepository.save(existing);
    }

    private Section validateSection(Section section) {
        if (section == null || section.getId() == null) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("section.missing", null, LocaleContextHolder.getLocale())
            );
        }
        return sectionRepository.findById(section.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{section.getId()}, LocaleContextHolder.getLocale())
                ));
    }

    private Certification cloneCertification(Certification source, Certification.Type type, Section section, String groupId) {
        Certification target = new Certification();
        target.setTitre(source.getTitre());
        target.setOrganisme(source.getOrganisme());
        target.setDate(source.getDate());
        target.setTypes(type);
        target.setDescription(source.getDescription());
        target.setSection(section);
        target.setUrl(source.getUrl());
        target.setDateDexpiration(source.getDateDexpiration());
        target.setNiveau(source.getNiveau());
        target.setGroupId(groupId);

        if (type == Certification.Type.PORTFOLIO) {
            target.setPdf(source.getPdf());
            target.setLogo(source.getLogo());
        }

        return target;
    }
}

package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.model.Temoignage;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.TemoignageRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.TemoignageService;

import java.util.List;

@Service
public class TemoignageServiceImpl implements TemoignageService {

    private final TemoignageRepository temoignageRepository;
    private final SectionRepository sectionRepository;
    private final MessageSource messageSource;

    public TemoignageServiceImpl(TemoignageRepository temoignageRepository,
                                 SectionRepository sectionRepository,
                                 MessageSource messageSource) {
        this.temoignageRepository = temoignageRepository;
        this.sectionRepository = sectionRepository;
        this.messageSource = messageSource;
    }

    @Override
    public List<Temoignage> listTemoignage() {
        return temoignageRepository.findAll();
    }

    @Override
    public Temoignage getTemoignageById(Long id) {
        return temoignageRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("temoignage.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public Temoignage saveTemoignage(Temoignage temoignage) {
        Long sectionId = temoignage.getSection().getId();

        Section sectionFromDb = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{temoignage.getSection().getId()}, LocaleContextHolder.getLocale())
                ));

        temoignage.setSection(sectionFromDb);
        return temoignageRepository.save(temoignage);
    }

    @Override
    public Temoignage deleteTemoignage(Long id) {
        Temoignage temoignage = temoignageRepository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("temoignage.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
        temoignageRepository.delete(temoignage);
        return temoignage;
    }

    @Override
    public Temoignage updateTemoignage(Temoignage temoignage) {
        return temoignageRepository.save(temoignage);
    }
}

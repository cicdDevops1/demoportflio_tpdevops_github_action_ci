package com.example.demoportflio_tpdevops_github_action_ci.service.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.PlayList;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.repository.PlayListRpository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.PlayListService;

import java.util.List;

@Service
public class PlayListServiceImplement implements PlayListService {

    private final PlayListRpository playListRpository;
    private final SectionRepository sectionRepository;
    private final MessageSource messageSource;

    public PlayListServiceImplement(PlayListRpository playListRpository, SectionRepository sectionRepository, MessageSource messageSource) {
        this.playListRpository = playListRpository;
        this.sectionRepository = sectionRepository;
        this.messageSource = messageSource;
    }

    @Override
    public List<PlayList> getAllPlayLists() {
        return playListRpository.findAll();
    }

    @Override
    public PlayList getPlayListById(Long id) {
        return playListRpository.findById(id)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("playlist.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public PlayList savePlayList(PlayList playList) {
        Long sectionId = playList.getSection().getId();

        Section sectionFromDb = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{sectionId}, LocaleContextHolder.getLocale())
                ));

        playList.setSection(sectionFromDb);
        return playListRpository.save(playList);
    }

    @Override
    public PlayList deletePlayList(PlayList playList) {
        if (!playListRpository.existsById(playList.getId())) {
            throw new ApiExecptionHandler.UserNotFoundException(
                    messageSource.getMessage("playlist.notfound", new Object[]{playList.getId()}, LocaleContextHolder.getLocale())
            );
        }
        playListRpository.delete(playList);
        return playList;
    }

    @Override
    public PlayList updatePlayList(PlayList playList) {
        Long sectionId = playList.getSection().getId();

        Section sectionFromDb = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(
                        messageSource.getMessage("section.notfound", new Object[]{sectionId}, LocaleContextHolder.getLocale())
                ));

        playList.setSection(sectionFromDb);
        return playListRpository.save(playList);
    }
}

package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.PlayList;

public interface PlayListService {

     List<PlayList> getAllPlayLists();
     PlayList getPlayListById(Long id);
     PlayList savePlayList(PlayList playList);
     PlayList deletePlayList(PlayList playList);
     PlayList updatePlayList(PlayList playList);

}

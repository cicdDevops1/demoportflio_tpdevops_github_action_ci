package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.User;


public interface UserService {
     User createUser(User user);
     List<User> listUser();
    User activeDesactive(User user);
    User getUserById(Long id);
    String verify(User user);
}

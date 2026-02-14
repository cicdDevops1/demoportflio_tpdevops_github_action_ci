package com.example.demoportflio_tpdevops_github_action_ci.controller;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demoportflio_tpdevops_github_action_ci.model.User;

import com.example.demoportflio_tpdevops_github_action_ci.service.UserService;



@RestController
@CrossOrigin(origins="*")
public class UserController  extends BaseController{
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
    private final UserService userService;

    public UserController(UserService userService, MessageSource messageSource) {
        super(messageSource); // appelle le constructeur du parent
        this.userService = userService;
    }


 

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody User user,
                                           @RequestParam(name = "lang", required = false) String lang) {
        return buildResponse(
                "user.register.success" ,   userService.createUser(user) ,lang,
                HttpStatus.OK
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user ,  @RequestParam(name = "lang", required = false) String lang){
        return  buildResponse(
                "user.login.success" ,    userService.verify(user) ,lang,
                HttpStatus.OK
        );
    }

    @GetMapping("/listuser")
    public ResponseEntity<Object> listUser(@RequestParam(name = "lang", required = false) String lang) {
        return  buildResponse(
                "user.login.success" ,    userService.listUser() ,lang,
                HttpStatus.OK
        );
    }
}

package com.example.demoportflio_tpdevops_github_action_ci.controller;

import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demoportflio_tpdevops_github_action_ci.model.Atout;

import com.example.demoportflio_tpdevops_github_action_ci.service.AtoutService;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;



@RestController
@RequestMapping("{slug}/atouts")
public class AtoutController  extends BaseController{
      @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
    private final AtoutService atoutService;


    public AtoutController(AtoutService atoutService, MessageSource messageSource) {
        super(messageSource);
        this.atoutService = atoutService;

    }

    @GetMapping("/lists")
    public ResponseEntity<Object> listAtout(@RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return buildResponse(
                "atout.list-success" , atoutService.getAllSkills(slug)  ,lang,
                HttpStatus.OK
        );
    }

    @PostMapping("/add")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> addAtout(@Valid @RequestBody Atout atout,
                                           @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return buildResponse(
                "atout.create-success" ,  atoutService.addAtout(atout) ,lang,
                HttpStatus.OK
        );
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getAtoutById(@PathVariable Long id,
                                               @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return buildResponse(
                "atout.detail-success" ,atoutService.getAtoutById(id)  ,lang,
                HttpStatus.OK

        );
    }

    @PutMapping("/update")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> updateAtout(@Valid @RequestBody Atout atout,
                                              @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return buildResponse(
                "atout.update-success" , atoutService.updateAtout(atout)  ,lang,
                HttpStatus.OK

        );
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> deleteAtout(@PathVariable Long id,
                                              @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return buildResponse(
                "atout.delete-success" ,atoutService.deleteAtout(id)  ,lang,
                HttpStatus.OK

        );




    }
}

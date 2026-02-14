package com.example.demoportflio_tpdevops_github_action_ci.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;


import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demoportflio_tpdevops_github_action_ci.model.Experience;

import com.example.demoportflio_tpdevops_github_action_ci.service.ExperienceService;

@RestController
@RequestMapping("{slug}/experiences")
public class ExperienceController extends BaseController {
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
    private final ExperienceService experienceService;


    public ExperienceController(ExperienceService experienceService, MessageSource messageSource) {
        super(messageSource);
        this.experienceService = experienceService;

    }

    @PostMapping("/add")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> createExperience(@Valid  @RequestBody Experience experience, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return buildResponse(
                    "experience.create-success" , experienceService.createExperience(experience) ,lang,
                    HttpStatus.OK

            );
    }


    @PostMapping("/modify")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> updateExperience(@Valid @RequestBody Experience experience, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {

        return buildResponse(
                "experience.update-success", experienceService.updateExperience(experience), lang,
                HttpStatus.OK

        );

    }
    @GetMapping("list/{id}")

    public ResponseEntity<Object> getExperienceDetail(@PathVariable("id") Long id, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {

            return buildResponse(
                    "experience.detail-success" , experienceService.getExperienceById(id) ,lang,
                    HttpStatus.OK

            );


    }

    @GetMapping("list/{type}")
    public ResponseEntity<Object> findByType(@PathVariable("type") String type, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return buildResponse(
                "experience.list-by-type-success" , experienceService.findByType(type, slug) ,lang,
                HttpStatus.OK

        );



    }





    @DeleteMapping("/delete/{idGroup}")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> deleteExperience(@PathVariable String idGroup, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return buildResponse(
                    "experience.delete-success" ,experienceService.deleteExperience(idGroup) ,lang,
                    HttpStatus.OK

            );


    }


}

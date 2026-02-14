package com.example.demoportflio_tpdevops_github_action_ci.controller;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import com.example.demoportflio_tpdevops_github_action_ci.model.Competence;

import com.example.demoportflio_tpdevops_github_action_ci.service.CompetenceService;



@RestController
@RequestMapping("{slug}/competences")
public class CompetenceController extends BaseController  {
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
    private final CompetenceService competenceService;


    public CompetenceController(CompetenceService competenceService, MessageSource messageSource) {
        super(messageSource);
        this.competenceService = competenceService;

    }

    @PostMapping("/add")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> createCompetence(@Valid @RequestBody Competence competence, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return  buildResponse(
                "competence.create-success" ,competenceService.createCompetence(competence) ,lang,
                HttpStatus.OK
        );
    }


    @GetMapping("/list")
    public ResponseEntity<Object> listCompetences(@RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return  buildResponse(
                "competence.list-success" ,competenceService.findAllCompetence() ,lang,
                HttpStatus.OK

        );


    }

    @GetMapping("/list/{type}")
    public ResponseEntity<Object> listCompetencesByType(@PathVariable String type, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return  buildResponse(
                "competence.list-by-type-success" ,competenceService.findByType(type, slug)  ,lang,
                HttpStatus.OK

        );
    }

    @DeleteMapping("/delete/{idGroup}")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> deleteCompetence(@PathVariable String idGroup, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {


            return  buildResponse(
                    "competence.delete-success" , competenceService.deleteCompetence(idGroup) ,lang,
                    HttpStatus.OK

            );
    }
    @PostMapping("/update")
    @PreAuthorize("#slug == principal.username")
    public  ResponseEntity<Object> updateCompetence(@Valid @RequestBody Competence competence, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return  buildResponse(
                "competence.update-success" , competenceService.updateCompetence(competence) ,lang,
                HttpStatus.OK

        );
    }
    @GetMapping("/liste/{id}")
    public ResponseEntity<Object> getCompetenceById(@PathVariable Long id, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return  buildResponse(
                    "competence.detail-success" ,competenceService.findCompetenceById(id) ,lang,
                    HttpStatus.OK

            );

        }
    }



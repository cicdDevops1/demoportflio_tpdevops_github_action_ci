package com.example.demoportflio_tpdevops_github_action_ci.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;



import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demoportflio_tpdevops_github_action_ci.model.Formation;

import com.example.demoportflio_tpdevops_github_action_ci.service.FormationService;



@RestController
@RequestMapping("{slug}/formations")
public class FormationController extends BaseController {
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
    private final FormationService formationService;


    public FormationController(FormationService formationService, MessageSource messageSource) {
        super(messageSource);
        this.formationService = formationService;
    }
    @PostMapping("/add")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> addFormation(@Valid  @RequestBody Formation formation, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {

            return  buildResponse(
                    "formation.create-success" ,  formationService.createFormation(formation) ,lang,
                    HttpStatus.OK

            );
    }
    @PostMapping("/update")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> updateFormation(@Valid @RequestBody Formation formation, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return  buildResponse(
                    "formation.update-success" ,   formationService.updateFormation(formation) ,lang,
                    HttpStatus.OK

            );




    }
    @DeleteMapping("/delete/{groupId}")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> deleteFormation(@PathVariable String groupId, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return  buildResponse(
                    "formation.delete-success" ,   formationService.deleteFormation(groupId) ,lang,
                    HttpStatus.OK

            );
    }
    @GetMapping("detail/{id}")
    public ResponseEntity<Object> getDetail(@PathVariable("id") Long id, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {

        return buildResponse(
                "formation.detail-success", formationService.getFormationById(id), lang,
                HttpStatus.OK
        );
    }

    @GetMapping("/lists/{type}")
    public ResponseEntity<Object> getFormationsByType(@PathVariable String type, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return  buildResponse(
                "formation.list-success" , formationService.findByType(type, slug) ,lang,
                HttpStatus.OK

        );

    }

}

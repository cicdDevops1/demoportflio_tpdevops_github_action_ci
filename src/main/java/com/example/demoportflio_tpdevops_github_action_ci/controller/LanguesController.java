package com.example.demoportflio_tpdevops_github_action_ci.controller;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demoportflio_tpdevops_github_action_ci.model.Langues;

import com.example.demoportflio_tpdevops_github_action_ci.service.LanguesService;



@RestController
@RequestMapping("{slug}/langues")
public class LanguesController extends  BaseController {
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
    private  final LanguesService languesService;


    public LanguesController(LanguesService languesService, MessageSource messageSource) {
        super(messageSource);
        this.languesService = languesService;
    }
    @PostMapping("/add")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> createLangues(@Valid @RequestBody Langues langues , @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return buildResponse(
                    "langue.create-success", languesService.createLangues(langues) , lang,
                    HttpStatus.OK
            );
    }
    @GetMapping("/list")
    public ResponseEntity<Object> listLangues(@RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return buildResponse(
                "langue.list-success", languesService.getLanguesByUserSlug(slug), lang,
                HttpStatus.OK

        );

    }
    @PostMapping("/update")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> updateLangues(@RequestBody Langues langues, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
             return buildResponse(
                     "langue.update-success" ,  languesService.updateLangues(langues) ,lang,
                     HttpStatus.OK

             );
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Object> listLangueDetail(@PathVariable Long id, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return buildResponse(
                    "langue.detail-success" , languesService.getLanguesById(id) ,lang,
                    HttpStatus.OK

            );
    }

        @DeleteMapping("/delete/{id}")
        @PreAuthorize("#slug == principal.username")
        public ResponseEntity<Object> deleteLangue(@PathVariable Long id, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug){
                return buildResponse(
                        "langue.delete-success" , languesService.deleteLangues(id) ,lang,
                        HttpStatus.OK
                );
    }

}

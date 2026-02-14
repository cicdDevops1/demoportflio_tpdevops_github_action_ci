package com.example.demoportflio_tpdevops_github_action_ci.controller;


import jakarta.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import com.example.demoportflio_tpdevops_github_action_ci.model.Hobbies;
import com.example.demoportflio_tpdevops_github_action_ci.service.HobbiesService;




@RestController
@RequestMapping("/{slug}/hobbies")
public class HobbiesController extends BaseController {
    private final HobbiesService hobbiesService;

    public HobbiesController(HobbiesService hobbiesService, MessageSource messageSource) {
        super(messageSource);
        this.hobbiesService = hobbiesService;
    }

    @PostMapping("/add")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> addHobbies(@Valid @RequestBody Hobbies h,
                                             @RequestParam(name = "lang", required = false) String lang,
                                             @PathVariable String slug) {
        return buildResponse("hobby.create-success", hobbiesService.addHobbies(h), lang, HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> updateHobbies(@Valid @RequestBody Hobbies h,
                                                @RequestParam(name = "lang", required = false) String lang,
                                                @PathVariable String slug) {
        return buildResponse("hobby.update-success", hobbiesService.updateHobbies(h), lang, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> deleteHobbies(@PathVariable Long id,
                                                @RequestParam(name = "lang", required = false) String lang,
                                                @PathVariable String slug) {
        return buildResponse("hobby.delete-success", hobbiesService.deleteHobbies(id), lang, HttpStatus.OK);
    }

    // Liste publique accessible à tous
    @GetMapping("/list")
    public ResponseEntity<Object> listHobbies(@RequestParam(name = "lang", required = false) String lang,
                                              @PathVariable String slug) {
        return buildResponse("hobby.list-success", hobbiesService.getHobbiesByUserSlug(slug), lang, HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Object> listHobbiesById(@PathVariable Long id,
                                                  @RequestParam(name = "lang", required = false) String lang,
                                                  @PathVariable String slug) {
        return buildResponse("hobby.detail-success", hobbiesService.getHobbies(id), lang, HttpStatus.OK);
    }
}

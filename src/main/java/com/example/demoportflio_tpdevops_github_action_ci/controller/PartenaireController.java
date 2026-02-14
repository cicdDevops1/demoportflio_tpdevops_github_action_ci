package com.example.demoportflio_tpdevops_github_action_ci.controller;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demoportflio_tpdevops_github_action_ci.model.Partenaire;

import com.example.demoportflio_tpdevops_github_action_ci.service.PartenaireService;





@RestController
@RequestMapping("{slug}/partenaires")
public class PartenaireController extends BaseController {
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
    private final PartenaireService partenaireService;



    public PartenaireController(PartenaireService partenaireService, MessageSource messageSource) {
        super(messageSource);
        this.partenaireService = partenaireService;
    }
    @PostMapping("/add")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> createPartenaire(@Valid @RequestBody Partenaire partenaire,
                                                   @RequestParam(name = "lang", required = false) String lang,
                                                   @PathVariable String slug) {
        return buildResponse(
                "partenaire.create-success",
                partenaireService.createPartenaireForCurrentUser(partenaire, slug),
                lang,
                HttpStatus.OK
        );
    }

    @PostMapping("/update")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> updatePartenaire(@Valid @RequestBody Partenaire partenaire,
                                                   @RequestParam(name = "lang", required = false) String lang,
                                                   @PathVariable String slug) {
        return buildResponse(
                "partenaire.update-success",
                partenaireService.updatePartenaire(partenaire, slug),
                lang,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> deletePartenaire(@PathVariable Long id,
                                                   @RequestParam(name = "lang", required = false) String lang,
                                                   @PathVariable String slug) {
        return buildResponse(
                "partenaire.delete-success",
                partenaireService.deletePartenaire(id),
                lang,
                HttpStatus.OK
        );
    }

    // ================= Public =================
    @GetMapping("lists/")
    public ResponseEntity<Object> getAllPartenaire(@PathVariable String slug,
                                                   @RequestParam(name = "lang", required = false) String lang) {
        return buildResponse(
                "partenaire.list-success",
                partenaireService.findBySectionUserSlug(slug),
                lang,
                HttpStatus.OK
        );
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<Object> getPartenaireById(@PathVariable Long id,
                                                    @PathVariable String slug,
                                                    @RequestParam(name = "lang", required = false) String lang) {
        return buildResponse(
                "partenaire.detail-success",
                partenaireService.getPartenaireById(id),
                lang,
                HttpStatus.OK
        );
    }
    }



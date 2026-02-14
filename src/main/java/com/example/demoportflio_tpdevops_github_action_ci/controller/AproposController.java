package com.example.demoportflio_tpdevops_github_action_ci.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demoportflio_tpdevops_github_action_ci.model.Apropos;

import com.example.demoportflio_tpdevops_github_action_ci.service.AproposService;


@RestController
@RequestMapping("/{slug}/abouts")
public class AproposController extends BaseController {

    @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
    private final AproposService aproposService;

    //private final UserService userService;

    public AproposController(AproposService aproposService, MessageSource messageSource) {
        super(messageSource);
        this.aproposService = aproposService;

       // this.userService=userService;
    }

    @PostMapping("/add")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> createApropos(@PathVariable("slug") String slug,

                                                @Valid @RequestBody Apropos apropos,
                                                @RequestParam(name = "lang", required = false) String lang) {

        return buildResponse(
                "apropos.update-success" ,aproposService.createApropos(apropos)  ,lang,
                HttpStatus.OK
        );
    }

        // 🔹 Modifier
        @PostMapping("/modif")
        @PreAuthorize("#slug == principal.username")
        public ResponseEntity<Object> updateApropos(@PathVariable("slug") String slug,

                                                    @Valid @RequestBody Apropos apropos,
                                                    @RequestParam(name = "lang", required = false) String lang) {

            return buildResponse(
                    "apropos.update-success" ,aproposService.updateApropos(apropos)  ,lang,
                    HttpStatus.OK
            );
        }

        // 🔹 Supprimer
        @DeleteMapping("delete/{id}")
        @PreAuthorize("#slug == principal.username")
        public ResponseEntity<Object> deleteApropos(@PathVariable("slug") String slug,
                                                    @PathVariable("id") Long id,
                                                    @RequestParam(name = "lang", required = false) String lang) {
            return buildResponse(
                    "apropos.delete-success" ,aproposService.deleteApropos(id)  ,lang,
                    HttpStatus.OK

            );





        }

        // 🔹 Détail d’un about
        @GetMapping("detail/{id}")
        public ResponseEntity<Object> getDetail(@PathVariable("slug") String slug,
                                                @PathVariable("id") Long id,
                                                @RequestParam(name = "lang", required = false) String lang) {

            return buildResponse(
                    "apropos.detail-success" ,aproposService.getAproposBySlug(slug)  ,lang,
                    HttpStatus.OK

            );
        }

        // 🔹 Liste des abouts pour un user
        @GetMapping("/list")
        public ResponseEntity<Object> getAllApropos(@PathVariable("slug") String slug,
                                                    @RequestParam(name = "lang", required = false) String lang) {
            return buildResponse(
                    "apropos.list-success" ,aproposService.getAproposBySlug(slug)  ,lang,
                    HttpStatus.OK
            );
        }

        // Utilitaire pour la langue

    }



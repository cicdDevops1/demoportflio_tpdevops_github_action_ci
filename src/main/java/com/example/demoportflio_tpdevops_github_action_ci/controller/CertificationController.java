package com.example.demoportflio_tpdevops_github_action_ci.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;


import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import com.example.demoportflio_tpdevops_github_action_ci.model.Certification;

import com.example.demoportflio_tpdevops_github_action_ci.service.CertificationService;



@RestController
@RequestMapping("{slug}/certifications")
public class CertificationController  extends BaseController {

    @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
    private final CertificationService certificationService;

    public CertificationController(final CertificationService certificationService, MessageSource messageSource) {
        super(messageSource);
        this.certificationService = certificationService;

    }


    @PostMapping("/add")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> addCertification(@Valid @RequestBody Certification certification, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return  buildResponse(
                    "certification.create-success" , certificationService.saveCertification(certification)  ,lang,
                    HttpStatus.OK
            );
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> findCertificationById(@PathVariable   Long id, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return  buildResponse(
                "certification.detail-success" ,  certificationService.findCertificationById(id)  ,lang,
                HttpStatus.OK

        );
    }

    @PostMapping("/update")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> updateCertification(@RequestBody Certification certification, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return  buildResponse(
                    "certification.update-success" , certificationService.updateCertification(certification) ,lang,
                    HttpStatus.OK

            );
    }


    @GetMapping("/list/{type}")
    public ResponseEntity<Object> findCertificationsByType(@PathVariable String type, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return  buildResponse(
                "certification.list-by-type-success" ,certificationService.findByType(type, slug)  ,lang,
                HttpStatus.OK

        );



    }



    @DeleteMapping("/delete/{idGroupe}")
    @PreAuthorize("#slug == principal.username")
    public ResponseEntity<Object> deleteCertification(@PathVariable String idGroupe, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return  buildResponse(
                    "certification.delete-success" ,certificationService.deleteCertification(idGroupe)  ,lang,
                    HttpStatus.OK

            );

    }
}

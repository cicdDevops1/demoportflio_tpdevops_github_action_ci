package com.example.demoportflio_tpdevops_github_action_ci.controller;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import jakarta.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import com.example.demoportflio_tpdevops_github_action_ci.model.Section;

import com.example.demoportflio_tpdevops_github_action_ci.service.SectionService;



import java.util.Objects;

@RestController
@RequestMapping("/{slug}/sections")

public class SectionController extends BaseController {
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2")

    private final SectionService sectionService;
 




    public SectionController(  SectionService sectionService, MessageSource messageSource) {
        super(messageSource);
        this.sectionService = Objects.requireNonNull(sectionService);

    }




      /*  @GetMapping("/csrf-token")
        public CsrfToken getCsrfToken(HttpServletRequest request) {
            Object csrfAttr = request.getAttribute("_csrf");
            if (csrfAttr instanceof Supplier) {
                return ((Supplier<CsrfToken>) csrfAttr).get();
            }
            return (CsrfToken) csrfAttr;
        }*/



    @PostMapping("/add")
    @PreAuthorize("#slug ==  authentication.principal.username")
    public ResponseEntity<Object> createSection(@Valid @RequestBody Section section, @RequestParam(name = "lang", required = false) String lang, @P("slug") @PathVariable("slug") String slug) {

         return buildResponse(
                "section.add.success" , sectionService.createSectionForCurrentUser(section, slug) ,lang,
                HttpStatus.OK
        );
    }


    @PostMapping("/user/modify")
    @PreAuthorize("#slug ==  principal.username")
    public ResponseEntity<Object> updateSection(@Valid @RequestBody Section section ,
                                                @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return buildResponse(
                    "section.update.success" , sectionService.updateSection(section) ,lang,
                    HttpStatus.OK

            );
    }

    @GetMapping()

    public ResponseEntity<Object> getAllSection(@RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        // Vérifie si le slug existe avant de charger les sections
        if (!sectionService.slugExists(slug)) {
            return buildResponse(
                    "section.slug.notfound",
                    null,
                    lang,
                    HttpStatus.NOT_FOUND
            );
        }

        return buildResponse(
                "section.list.success",
                sectionService.getSectionsByUserSlug(slug),
                lang,
                HttpStatus.OK
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDetail(@PathVariable("id") Long id, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
        return buildResponse(
                "section.detail.success", sectionService.getSectionById(id), lang,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("#slug ==  principal.username")
    public ResponseEntity<Object> deleteSection(@PathVariable("id") Long id, @RequestParam(name = "lang", required = false) String lang, @PathVariable String slug) {
            return buildResponse(
                    "section.delete.success", sectionService.deleteSection(id),  lang,
                    HttpStatus.OK

            );
    }
}

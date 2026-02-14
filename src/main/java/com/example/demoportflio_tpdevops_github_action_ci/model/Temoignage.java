package com.example.demoportflio_tpdevops_github_action_ci.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Temoignage {

    @Id
    @SequenceGenerator(
            name = "temoi_seq",               // logique Hibernate
            sequenceName = "temoi_sequence",  // objet séquence dans PostgreSQL
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "temoi_seq"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @NotBlank(message = "{temoignage.title.notblank}")
    @Size(min = 3, max = 150, message = "{temoignage.title.size}")
    private String title;

    @NotBlank(message = "{temoignage.description.notblank}")
    @Size(min = 10, max = 500, message = "{temoignage.description.size}")
    private String description;

    @NotBlank(message = "{temoignage.nom.notblank}")
    private String nom;

    @NotBlank(message = "{temoignage.professional.notblank}")
    private String professional;

    @NotBlank(message = "{temoignage.url.notblank}")
    @Size(min = 4, max = 255, message = "{temoignage.url.size}")
    private String url;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )

    public Section getSection() {
        return section;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )
    public void setSection(Section section) {
        this.section = section;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

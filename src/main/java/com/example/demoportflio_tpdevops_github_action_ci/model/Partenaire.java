package com.example.demoportflio_tpdevops_github_action_ci.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Partenaire {
    @Id
    @SequenceGenerator(
            name = "partenaire_seq",               // logique Hibernate
            sequenceName = "partenaire_sequence",  // objet séquence dans PostgreSQL
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "partenaire_seq"
    )
    private Long id;

    @NotBlank(message="{partenaire.nom.notblank}")
    @Size(min=4, max=100, message="{partenaire.nom.size}")
    private String nom;

    @NotBlank(message = "{partenaire.url.notblank}")
    @Size(min=3, max=500, message="{partenaire.size.notblank}")
    private String url;

    @NotBlank(message = "{partenaire.logo.notblank}")
    @Size(min=3, max=255, message="{partenaire.nom.size}")
    private String logo;
    @Column(columnDefinition = "TEXT")
    @Size(min = 200, max = 500, message = "{partenaire.description.size}")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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
}

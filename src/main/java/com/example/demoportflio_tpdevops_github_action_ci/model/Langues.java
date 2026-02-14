package com.example.demoportflio_tpdevops_github_action_ci.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Langues {
    @Id
    @SequenceGenerator(
            name = "langue_seq",               // logique Hibernate
            sequenceName = "langue_sequence",  // objet séquence dans PostgreSQL
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "langue_seq"
    )
    private Long id;
    @NotBlank(message = "{langue.nom.notblank}")
    private String nom;
    private Niveau niveau;
    enum  Niveau {
        Debutant,
        Elementaire,
        Intermediaire,
         Avance,
         Maitrise
    }
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Section section;
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )
    public Section getSection() {
        return section;
    }



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

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )

    public void setSection(Section section) {
        this.section = section;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }
}

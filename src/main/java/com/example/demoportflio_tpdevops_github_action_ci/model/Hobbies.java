package com.example.demoportflio_tpdevops_github_action_ci.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Hobbies {
    @Id
    @SequenceGenerator(
            name = "hobby_seq",               // logique Hibernate
            sequenceName = "hobby_sequence",  // objet séquence dans PostgreSQL
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hobby_seq"
    )
    private Long id;
    @NotBlank(message = "{hobby.nom.notblank}")
    @Size(message = "{hobby.nom.size}", max = 200, min = 4)
    private String nom;


    private String description ;
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Section section;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )

    public Section getSection() {
        return section;
    }
    private Type types;
    enum Type{
        CV, PORTFOLIO, BOTH
    }

    public Type getTypes() {
        return types;
    }

    public void setTypes(Type types) {
        this.types = types;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )
    public void setSection(Section section) {
        this.section = section;
    }
}

package com.example.demoportflio_tpdevops_github_action_ci.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;

@Entity

public class Formation {

    @Id
    @SequenceGenerator(
            name = "formation_seq",               // logique Hibernate
            sequenceName = "formation_sequence",  // objet séquence dans PostgreSQL
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "formation_seq"
    )
    private Long id;


    @NotBlank(message = "{formation.titre.notblank}")
    @Size(min = 3, max = 150, message = "{formation.titre.size}")
    private String titre;

    @NotBlank(message = "{formation.organisme.notblank}")
    @Size(min = 3, max = 200, message = "{formation.organisme.size}")
    private String organisme;

    @NotBlank(message = "{formation.lieu.notblank}")
    @Size(min = 3, max = 150, message = "{ formation.lieu.size}")
    private String lieu;

    @NotNull(message = "{formation.dateDebut.notnull}")
    private LocalDate dateDebut;


    private LocalDate dateFin;

    @NotBlank(message = "{formation.description.notblank}")
    @Size(min = 50, max = 500, message = "{formation.description.size}")
    @Column(columnDefinition = "TEXT")
    private String description;



   public enum Type {
        CV, PORTFOLIO, BOTH
    }
    private Type types;



    @Size(min = 3, max = 300, message = "{formation.logo.size}")
    private String logo;
    private String sigle;
    private boolean certificat;
    private boolean diplomate;
    private String groupId;



    @ManyToOne
    @JoinColumn(name = "section_id")

    private Section section;


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getOrganisme() {
        return organisme;
    }

    public void setOrganisme(String organisme) {
        this.organisme = organisme;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getTypes() {
        return types;
    }

    public void setTypes(Type types) {
        this.types = types;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public boolean isCertificat() {
        return certificat;
    }

    public void setCertificat(boolean certificat) {this.certificat = certificat;
    }

    public boolean isDiplomate() {
        return diplomate;
    }

    public void setDiplomate(boolean diplomate) {
        this.diplomate = diplomate;
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

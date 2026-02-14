package com.example.demoportflio_tpdevops_github_action_ci.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.time.LocalDate;

@Entity
public class Certification {

    @Id
    @SequenceGenerator(
            name = "certificat_seq",
            sequenceName = "certificat_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "certificat_seq"
    )
    private Long id;

    @Size(max = 200, message = "{certification.pdf.size}")
    private String pdf;

    @Size(max = 200, message = "{certification.logo.size}")
    private String logo;

    @NotBlank(message = "{certification.titre.notblank}")
    private String titre;

    @NotBlank(message = "{certification.organisme.notblank}")
    @Size(min = 2, max = 100, message = "{certification.organisme.size}")
    private String organisme;

    private String groupId;

    @NotNull(message = "{certification.date.notnull}")
    private Date date;

    @NotBlank(message = "{certification.description.notblank}")
    @Size(min = 100, max = 500, message = "{certification.description.size}")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Size(max = 200, message = "{certification.url.size}")
    private String url;

    private LocalDate dateDexpiration;

    @NotBlank(message = "{certification.niveau.notblank}")
    private String niveau;

    @Enumerated(EnumType.STRING)
    private Type types;
    public enum Type {
        CV, PORTFOLIO, BOTH
    }

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Section section;

    // Getters & Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getPdf() {
        return pdf;
    }
    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )
    public Date getDate() {
        return date;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )
    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getDateDexpiration() {
        return dateDexpiration;
    }
    public void setDateDexpiration(LocalDate dateDexpiration) {
        this.dateDexpiration = dateDexpiration;
    }

    public String getNiveau() {
        return niveau;
    }
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Type getTypes() {
        return types;
    }
    public void setTypes(Type types) {
        this.types = types;
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

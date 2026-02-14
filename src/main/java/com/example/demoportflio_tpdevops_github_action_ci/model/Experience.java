package com.example.demoportflio_tpdevops_github_action_ci.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.LocalDate;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Experience {
    @Id
    @SequenceGenerator(
            name = "experience_seq",               // logique Hibernate
            sequenceName = "experience_sequence",  // objet séquence dans PostgreSQL
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "experience_seq"
    )

    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Section section;

    private  String logo;
    private String media;


    // For long URLs


    private String groupId;


    private String lien;


    @Size(min = 2, max = 200)
    @NotBlank(message = "Le nom entreprise requis")
    private String entreprise;

    @NotBlank(message = "Localisation requise")
    private String localisation;



    @Size(min = 4, max = 200)
    @NotBlank(message = "Titre requis")
    private String title;


    private LocalDate dateDebut;
      @NotNull(message = "La date est requise")

    private LocalDate dateFin;
      @Size(min = 50, max =400,message = "Description est entre 50/350")
     @NotBlank(message = "Description requise")
    @Column(columnDefinition = "TEXT") // For long descriptions
    private String description;

    @Column(columnDefinition = "TEXT")
    @Size( max = 300, message = "Mission Au maximum 300")
   private  String mession ;
    @Column(columnDefinition = "TEXT")
    @Size( max = 300, message = "{experience.realisation.size}")
   private  String realisation;

    // Audit fields
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    // Constructors


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    private Type types;
   public enum Type{
        CV, PORTFOLIO, BOTH
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )

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

    public String getMession() {
        return mession;
    }

    public void setMession(String mession) {
        this.mession = mession;
    }

    public String getRealisation() {
        return realisation;
    }

    public void setRealisation(String realisation) {
        this.realisation = realisation;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )

    public Date getCreatedAt() {
        return createdAt;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )
    public Date getUpdatedAt() {
        return updatedAt;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}

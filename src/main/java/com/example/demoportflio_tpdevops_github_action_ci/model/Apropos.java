package com.example.demoportflio_tpdevops_github_action_ci.model;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.sql.Date;

@Entity
public class Apropos {
    @Id
    @SequenceGenerator(
            name = "propos_seq",
            sequenceName = "propos_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "propos_seq"
    )
    private Long id;
    @NotBlank(message = "{apropos.nom.notblank}")
    @Size(min = 4, max = 100, message = "{apropos.nom.size}")
    private String nom;
  

    @Size(max = 200, message = "{apropos.photo.size}")
    private String photo;

    @Column(name = "git_url")
    @Size(max = 200, message = "{apropos.git.size}")
    private String git;

    @Column(name = "linkedin_url")
    @Size(max = 200, message = "{apropos.linkedin.size}")
    private String linkedin;

    @NotBlank(message = "{apropos.email.notblank}")
    @Email(message = "{apropos.email.invalid}")
    @Size(min = 5, max = 150, message = "{apropos.email.size}")
    private String email;

    private Type types;
    public enum Type {
        CV, PORTFOLIO, BOTH
    }

    public Type getTypes() {
        return types;
    }

    public void setTypes(Type types) {
        this.types = types;
    }

    @Pattern(regexp = "^\\+?[0-9\\s-]{8,15}$", message = "{apropos.telephone.invalid}")
    private String telephone;
   
    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @NotBlank(message = "{apropos.localisation.notblank}")
    @Size(min = 2, max = 100, message = "{apropos.localisation.size}")
    private String localisation;

    @NotBlank(message = "{apropos.niveau.notblank}")
    @Size(min = 4, max = 100, message = "{apropos.niveau.size}")
    private String niveau;

    @NotBlank(message = "{apropos.profile.notblank}")
    @Size(min = 50, max = 300, message = "{apropos.profile.size}")
    private String profile;

    @NotBlank(message = "{apropos.titre.notblank}")
    @Size(min = 4, max = 200, message = "{apropos.titre.size}")
    private String titre;

    // Audit fields
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;



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

    public String getPhoto() {
        return photo;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public String getLinkedin() {
        return linkedin;
    }



    public void setLinkedin(String linkedIn) {
        this.linkedin = linkedIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )

    public Section getSection() {
        return section ;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )

    public void setSection(Section section) {
        this.section =section;
    }

}

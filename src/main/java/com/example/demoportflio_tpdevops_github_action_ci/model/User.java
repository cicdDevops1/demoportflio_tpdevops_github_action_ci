package com.example.demoportflio_tpdevops_github_action_ci.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {


    /*@Id
    @SequenceGenerator(
            name = "user_seq",               // logique Hibernate
            sequenceName = "user_sequence",  // objet séquence dans PostgreSQL
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq"
    )*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "{user.name.notblank}")
    @Size(min = 4, max = 50)
    private String name;

    private boolean active;
     private String slug;
     private String lang;



    @NotBlank(message = "{user.password.notblank}")
    @Size(min = 8, max = 100)
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,100}$",
            message = "{user.password.pattern}"
    )
   // @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "{user.email.notblank}")
    @Size(min = 4, max = 100)
   // @Column(unique = true)
    //@Column(name = "email", nullable = false, unique = true)

    private  String email;

    @NotBlank(message = "{user.domaine.notblank}")
     @Size(min = 2, max = 80, message = "{user.domaine.size}")
    private String domaine;

    @NotBlank(message = "{user.localisation.notblank}")
    @Size(min = 2, max = 100)
    private String localisation;


    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }


    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String location) {
        this.localisation = location;
    }
}

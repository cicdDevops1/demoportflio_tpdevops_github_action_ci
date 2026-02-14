package com.example.demoportflio_tpdevops_github_action_ci.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Section {

    @Id
    @SequenceGenerator(
            name = "atout_seq",               // logique Hibernate
            sequenceName = "atout_sequence",  // objet séquence dans PostgreSQL
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "atout_seq"
    )
    private Long id;



   //@NotNull(message = "La section est réquise")

   // @Size(min=3, max=20)
   @Enumerated(EnumType.STRING)
    private Sections section;




    public enum Sections {
         Experiences, Temoignages, Apropos, Formations,
        Competences , Langues, Loisirs,
        Certifications, Partenaire, Blogs , Playlist
    }


    @ManyToOne
    @JoinColumn(name = "user_id")
   // @NotBlank(message = "{user.notnull}")
    private User user;



    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sections getSection() {
        return section;
    }

    public void setSection(Sections section) {
        this.section = section;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )
    public User getUser() {
        return user;
    }
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Relation JPA : référence mutable nécessaire pour Hibernate"
    )
    public void setUser(User user) {
        this.user = user;
    }
}

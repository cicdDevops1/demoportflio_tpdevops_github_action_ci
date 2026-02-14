package com.example.demoportflio_tpdevops_github_action_ci.model;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



@Entity
public class Atout {
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
    @Size(message = "{atout.titre.size}", min = 2, max = 200)
    @NotBlank(message = "{atout.titre.notblank}")
    private String title;

    @NotBlank(message = "{atout.description.notblank}")
    @Size(message = "{atout.description.size}", min = 200, max = 500)
    private String description;
    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

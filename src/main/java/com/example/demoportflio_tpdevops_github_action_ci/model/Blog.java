package com.example.demoportflio_tpdevops_github_action_ci.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

    @Entity
    public class Blog {

        @Id
        @SequenceGenerator(
                name = "blog_seq",
                sequenceName = "blog_sequence",
                allocationSize = 1
        )
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_seq")
        private Long id;

        @NotBlank(message = "{blog.titre.notblank}")
        @Size(min = 10, max = 30, message = "{blog.titre.size}")
        private String titre;

        private LocalDate datePublication;

        @NotBlank(message = "{blog.resume.notblank}")
        @Size(min = 20, max = 200, message = "{blog.resume.size}")
        private String resume;

        @Column(columnDefinition = "TEXT")
        @Size(min = 200, max = 500, message = "{blog.contenu.size}")
        private String contenu;

        private String imagePrincipale;
        private String categorie;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private Section section;

        @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Relation JPA : référence mutable nécessaire pour Hibernate")
        public Section getSection() {
            return section;
        }

        @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Relation JPA : référence mutable nécessaire pour Hibernate")
        public void setSection(Section section) {
            this.section = section;
        }

        // Getters et Setters pour tous les champs
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitre() { return titre; }
        public void setTitre(String titre) { this.titre = titre; }

        public LocalDate getDatePublication() { return datePublication; }
        public void setDatePublication(LocalDate datePublication) { this.datePublication = datePublication; }

        public String getResume() { return resume; }
        public void setResume(String resume) { this.resume = resume; }

        public String getContenu() { return contenu; }
        public void setContenu(String contenu) { this.contenu = contenu; }

        public String getImagePrincipale() { return imagePrincipale; }
        public void setImagePrincipale(String imagePrincipale) { this.imagePrincipale = imagePrincipale; }

        public String getCategorie() { return categorie; }
        public void setCategorie(String categorie) { this.categorie = categorie; }


}



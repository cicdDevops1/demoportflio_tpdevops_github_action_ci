package com.example.demoportflio_tpdevops_github_action_ci.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Experience;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;

import java.time.LocalDate;
import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByTypesAndSection_User_Slug(Experience.Type expType,String slug);
     List<Experience> findByGroupId(String groupId);
    boolean existsByTitleAndTypesAndDescriptionAndLocalisationAndDateDebutAndDateFinAndMessionAndRealisationAndEntrepriseAndSection(@Size(min = 4, max = 200) @NotBlank(message = "Titre requis") String title, Experience.Type types, @Size(min = 50, max = 400, message = "Description est entre 50/350") @NotBlank(message = "Description requise") String description, @NotBlank(message = "Localisation requise") String localisation, LocalDate dateDebut, @NotNull(message = "La date est requise") LocalDate dateFin, @Size(max = 300, message = "Mission Au maximum 300") String mession, @Size(max = 300, message = "Realisation au maximum 300") String realisation, @Size(min = 2, max = 200) @NotBlank(message = "Le nom entreprise requis") String entreprise, Section section);
}

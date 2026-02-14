package com.example.demoportflio_tpdevops_github_action_ci.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Formation;

import java.time.LocalDate;
import java.util.List;

public interface FormationRepository  extends JpaRepository<Formation, Long> {
   ;
     List<Formation> findByGroupId(String groupId);
    List<Formation> findByTypesAndSection_User_Slug(Formation.Type type, String slug);

    boolean existsByTitreAndDescriptionAndOrganismeAndLieuAndDateDebutAndDateFinAndSigleAndCertificatIsAndDiplomate(@NotBlank(message = "Le titre est requis") @Size(min = 3, max = 150, message = "Le titre doit contenir entre 3/150") String titre, @NotBlank(message = "La description est requise") @Size(min = 50, max = 500, message = "La description doit contenir entre 50/500") String description, @NotBlank(message = "L'organisme est requis") @Size(min = 3, max = 200, message = "L'organisme doit contenir entre 3/200 ") String organisme, @NotBlank(message = "Le lieu est requis") @Size(min = 3, max = 150, message = "Le lieu doit contenir entre 3/150") String lieu, @NotNull(message = "La date de début est requise") LocalDate dateDebut, @NotNull(message = "La date de fin est requise") LocalDate dateFin, String sigle, boolean certificat, boolean diplomate);
}

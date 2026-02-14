package com.example.demoportflio_tpdevops_github_action_ci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoportflio_tpdevops_github_action_ci.model.Certification;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;

import java.util.List;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findByGroupId(String groupId);


     List<Certification> findByTypes(Certification.Type type);
    boolean  existsByTitreAndOrganismeAndDateAndTypesAndSection(
            String titre,
            String organisme,
            java.sql.Date date,
            Certification.Type types,
            Section section
    );

    List<Certification> findByTypesAndSection_User_Slug(Certification.Type certType, String slug);
}

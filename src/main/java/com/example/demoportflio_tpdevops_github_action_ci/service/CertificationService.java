package com.example.demoportflio_tpdevops_github_action_ci.service;

import java.util.List;

import com.example.demoportflio_tpdevops_github_action_ci.model.Certification;

public interface CertificationService {
    Certification findCertificationById(Long id);

    List<Certification> saveCertification(Certification certification);
     List<Certification>  deleteCertification(String id);
     List<Certification> findByType(String type, String slug);
     Certification updateCertification(Certification certification);


}

package com.example.demoportflio.test.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Experience;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.repository.ExperienceRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.implement.ExperienceServiceImplement;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExperienceServiceImplementTest {

    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private ExperienceServiceImplement experienceService;

    private Experience experience;
    private Section section;

    @BeforeEach
    void setUp() {
        section = new Section();
        section.setId(1L);

        experience = new Experience();
        experience.setId(1L);
        experience.setTitle("Développeur Java");
        experience.setEntreprise("OpenAI");
        experience.setTypes(Experience.Type.CV);
        experience.setSection(section);
    }

    // ---------------- findByType ----------------
    @Test
    void testFindByType_Success() {
        when(experienceRepository.findByTypesAndSection_User_Slug(Experience.Type.CV,"ahmadou_macka_diallo" ))
                .thenReturn(Collections.singletonList(experience));

        List<Experience> result = experienceService.findByType("CV","ahmadou_macka_diallo");

        assertEquals(1, result.size());
        assertEquals("Développeur Java", result.get(0).getTitle());
    }

    @Test
    void testFindByType_InvalidType() {
        assertThrows(ApiExecptionHandler.UserNotFoundException.class,
                () -> experienceService.findByType("INVALID", "ahmadou_macka_diallo"));
    }

    @Test
    void testFindByType_EmptyList() {
        when(experienceRepository.findByTypesAndSection_User_Slug(Experience.Type.CV, "ahmadou_macka_diallo" ))
                .thenReturn(Collections.emptyList());

        assertThrows(ApiExecptionHandler.UserNotFoundException.class,
                () -> experienceService.findByType("CV", "ahmadou_macka_diallo"));
    }

    // ---------------- createExperience ----------------
    @Test
    void testCreateExperience_Success() {
        when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));
        when(experienceRepository.existsByTitleAndTypesAndDescriptionAndLocalisationAndDateDebutAndDateFinAndMessionAndRealisationAndEntrepriseAndSection(
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(false);
        when(experienceRepository.save(any(Experience.class))).thenReturn(experience);

        List<Experience> result = experienceService.createExperience(experience);

        assertEquals(1, result.size());
        verify(experienceRepository, times(1)).save(any(Experience.class));
    }

    @Test
    void testCreateExperience_BothType() {
        experience.setTypes(Experience.Type.BOTH);

        when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));
        when(experienceRepository.existsByTitleAndTypesAndDescriptionAndLocalisationAndDateDebutAndDateFinAndMessionAndRealisationAndEntrepriseAndSection(
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(false);
        when(experienceRepository.save(any(Experience.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<Experience> result = experienceService.createExperience(experience);

        assertEquals(2, result.size()); // une pour CV et une pour PORTFOLIO
    }

    @Test
    void testCreateExperience_Duplicate() {
        when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));
        when(experienceRepository.existsByTitleAndTypesAndDescriptionAndLocalisationAndDateDebutAndDateFinAndMessionAndRealisationAndEntrepriseAndSection(
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(true);

        assertThrows(ApiExecptionHandler.UserAlreadyExistsException.class,
                () -> experienceService.createExperience(experience));
    }

    // ---------------- getExperienceById ----------------
    @Test
    void testGetExperienceById_Success() {
        when(experienceRepository.findById(1L)).thenReturn(Optional.of(experience));

        Experience result = experienceService.getExperienceById(1L);

        assertNotNull(result);
        assertEquals("Développeur Java", result.getTitle());
    }

    @Test
    void testGetExperienceById_NotFound() {
        when(experienceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApiExecptionHandler.UserNotFoundException.class,
                () -> experienceService.getExperienceById(1L));
    }

    // ---------------- updateExperience ----------------
    @Test
    void testUpdateExperience_Success() {
        when(experienceRepository.existsById(1L)).thenReturn(true);
        when(sectionRepository.existsById(1L)).thenReturn(true);
        when(experienceRepository.save(experience)).thenReturn(experience);

        Experience updated = experienceService.updateExperience(experience);

        assertNotNull(updated);
        assertEquals("Développeur Java", updated.getTitle());
    }

    @Test
    void testUpdateExperience_NotFound() {
        when(experienceRepository.existsById(1L)).thenReturn(false);

        assertThrows(ApiExecptionHandler.UserNotFoundException.class,
                () -> experienceService.updateExperience(experience));
    }

    // ---------------- deleteExperience ----------------
    @Test
    void testDeleteExperience_Success() {
        experience.setGroupId("group-123");
        when(experienceRepository.findByGroupId("group-123"))
                .thenReturn(Collections.singletonList(experience));

        List<Experience> deleted = experienceService.deleteExperience("group-123");

        assertEquals(1, deleted.size());
        verify(experienceRepository, times(1)).deleteAll(anyList());
    }

    @Test
    void testDeleteExperience_InvalidGroupId() {
        assertThrows(ApiExecptionHandler.UserNotFoundException.class,
                () -> experienceService.deleteExperience(""));
    }

    @Test
    void testDeleteExperience_NotFound() {
        when(experienceRepository.findByGroupId("group-123"))
                .thenReturn(Collections.emptyList());

        assertThrows(ApiExecptionHandler.UserNotFoundException.class,
                () -> experienceService.deleteExperience("group-123"));
    }
}

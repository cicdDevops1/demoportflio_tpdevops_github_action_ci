package com.example.demoportflio.test.service;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.model.User;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.UserRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.implement.SectionServiceImplement;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SectionServiceImplementTest {

    @InjectMocks
    private SectionServiceImplement sectionService;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private UserRepository userRepository;


    @Test
    void testCreateSection_Success() {
        User user = new User();
        user.setId(1L);

        Section section = new Section();
        section.setUser(user);
        section.setSection(Section.Sections.Formations);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(sectionRepository.save(section)).thenReturn(section);

        Section result = sectionService.createSectionForCurrentUser(section, user.getSlug());

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(Section.Sections.Formations, result.getSection());


        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(sectionRepository).save(section);
    }

    @Test
    void testCreateSection_UserNotFound() {
        User user = new User();
        user.setId(99L);

        Section section = new Section();
        section.setUser(user);

        Mockito.when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ApiExecptionHandler.UserNotFoundException.class, () -> {
            sectionService.createSectionForCurrentUser(section, user.getSlug());
        });

        assertTrue(exception.getMessage().contains("Utilisateur introuvable"));
    }

    // ===============================
    // Test getSections
    // ===============================
    @Test
    void testGetSections() {
        List<Section> sections = new ArrayList<>();
        Section s = new Section();
        s.setSection(Section.Sections.Certifications);

        sections.add(s);

        Mockito.when(sectionRepository.findAll()).thenReturn(sections);

        List<Section> result = sectionService.getSections();

        assertEquals(1, result.size());
        assertEquals(Section.Sections.Certifications, result.get(0).getSection());


        Mockito.verify(sectionRepository).findAll();
    }

    // ===============================
    // Test getSectionById
    // ===============================
    @Test
    void testGetSectionById_Success() {
        Section section = new Section();
        section.setId(1L);

        Mockito.when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));

        Section result = sectionService.getSectionById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetSectionById_NotFound() {
        Mockito.when(sectionRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ApiExecptionHandler.UserNotFoundException.class, () -> {
            sectionService.getSectionById(99L);
        });

        assertTrue(exception.getMessage().contains("Utilisateur introuvable"));
    }

    // ===============================
    // Test updateSection
    // ===============================
    @Test
    void testUpdateSection_Success() {
        Section section = new Section();
        section.setId(1L);

        Mockito.when(sectionRepository.existsById(1L)).thenReturn(true);
        Mockito.when(sectionRepository.save(section)).thenReturn(section);

        Section result = sectionService.updateSection(section);

        assertEquals(1L, result.getId());
        Mockito.verify(sectionRepository).save(section);
    }

    @Test
    void testUpdateSection_NotExist() {
        Section section = new Section();
        section.setId(99L);

        Mockito.when(sectionRepository.existsById(99L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sectionService.updateSection(section);
        });

        assertTrue(exception.getMessage().contains("n'existe pas"));
    }

    // ===============================
    // Test deleteSection
    // ===============================
    @Test
    void testDeleteSection_Success() {
        Section section = new Section();
        section.setId(1L);

        Mockito.when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));

        Section result = sectionService.deleteSection(1L);

        assertEquals(1L, result.getId());
        Mockito.verify(sectionRepository).delete(section);
    }

    @Test
    void testDeleteSection_NotFound() {
        Mockito.when(sectionRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ApiExecptionHandler.UserNotFoundException.class, () -> {
            sectionService.deleteSection(99L);
        });

        assertTrue(exception.getMessage().contains("Utilisateur introuvable"));
    }
}

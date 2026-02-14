package com.example.demoportflio.test.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Apropos;
import com.example.demoportflio_tpdevops_github_action_ci.model.Section;
import com.example.demoportflio_tpdevops_github_action_ci.repository.AproposRepository;
import com.example.demoportflio_tpdevops_github_action_ci.repository.SectionRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.implement.AproposServiceImplement;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AproposServiceImplementTest {

    @InjectMocks
    private AproposServiceImplement aproposService;

    @Mock
    private AproposRepository aproposRepository;

    @Mock
    private SectionRepository sectionRepository;


    @Test
    void testCreateApropos_Success() {
        Section section = new Section();
        section.setId(1L);

        Apropos apropos = new Apropos();
        apropos.setSection(section);

        Mockito.when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));
        Mockito.when(aproposRepository.save(apropos)).thenReturn(apropos);

        Apropos result = aproposService.createApropos(apropos);

        assertNotNull(result);
        assertEquals(section, result.getSection());

        Mockito.verify(sectionRepository).findById(1L);
        Mockito.verify(aproposRepository).save(apropos);
    }

    @Test
    void testCreateApropos_SectionNotFound() {
        Section section = new Section();
        section.setId(99L);

        Apropos apropos = new Apropos();
        apropos.setSection(section);

        Mockito.when(sectionRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ApiExecptionHandler.UserNotFoundException.class, () -> {
            aproposService.createApropos(apropos);
        });

        assertTrue(exception.getMessage().contains("Section introuvable"));
    }


    @Test
    void testGetAllApropos() {
        Apropos apropos = new Apropos();
        apropos.setId(1L);

        Mockito.when(aproposRepository.findAll()).thenReturn(List.of(apropos));

        Apropos result = aproposService.getAproposBySlug("macka_diallo");

        assertEquals(1, result);
        assertEquals(1L, result.getId());
    }

    // ===============================
    // Test getAproposById
    // ===============================
    @Test
    void testGetAproposById_Success() {
        Apropos apropos = new Apropos();
        apropos.setId(1L);

        Mockito.when(aproposRepository.findById(1L)).thenReturn(Optional.of(apropos));

        Apropos result = aproposService.getAproposBySlug("ahamadou_macka_diallo");

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetAproposById_NotFound() {
        Mockito.when(aproposRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ApiExecptionHandler.UserNotFoundException.class, () -> {
            aproposService.getAproposBySlug("sow_ha");
        });

        assertTrue(exception.getMessage().contains("non trouvée"));
    }

    // ===============================
    // Test updateApropos
    // ===============================
    @Test
    void testUpdateApropos_Success() {
        Apropos apropos = new Apropos();
        apropos.setId(1L);

        Mockito.when(aproposRepository.findById(1L)).thenReturn(Optional.of(apropos));
        Mockito.when(aproposRepository.save(apropos)).thenReturn(apropos);

        Apropos result = aproposService.updateApropos(apropos);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        Mockito.verify(aproposRepository).save(apropos);
    }

    @Test
    void testUpdateApropos_NotFound() {
        Apropos apropos = new Apropos();
        apropos.setId(99L);

        Mockito.when(aproposRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ApiExecptionHandler.UserNotFoundException.class, () -> {
            aproposService.updateApropos(apropos);
        });

        assertTrue(exception.getMessage().contains("n'existe pas"));
    }

    // ===============================
    // Test deleteApropos
    // ===============================
    @Test
    void testDeleteApropos_Success() {
        Apropos apropos = new Apropos();
        apropos.setId(1L);

        Mockito.when(aproposRepository.findById(1L)).thenReturn(Optional.of(apropos));

        Apropos result = aproposService.deleteApropos(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        Mockito.verify(aproposRepository).delete(apropos);
    }

    @Test
    void testDeleteApropos_NotFound() {
        Mockito.when(aproposRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ApiExecptionHandler.UserNotFoundException.class, () -> {
            aproposService.deleteApropos(99L);
        });

        assertTrue(exception.getMessage().contains("introuvable"));
    }
}

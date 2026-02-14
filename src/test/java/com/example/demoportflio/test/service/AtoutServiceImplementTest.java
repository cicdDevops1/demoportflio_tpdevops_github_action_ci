package com.example.demoportflio.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.Atout;
import com.example.demoportflio_tpdevops_github_action_ci.repository.AtoutRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.implement.AtoutServiceImplement;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtoutServiceImplementTest {

    @Mock
    private AtoutRepository atoutRepository;

    @InjectMocks
    private AtoutServiceImplement atoutService;

    private Atout atout;

    @BeforeEach
    void setUp() {
        atout = new Atout();
        atout.setId(1L);
    }

    @Test
    void testAddAtout() {
        when(atoutRepository.save(atout)).thenReturn(atout);

        Atout result = atoutService.addAtout(atout);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(atoutRepository, times(1)).save(atout);
    }

    @Test
    void testGetAtoutById_Success() {
        when(atoutRepository.findById(1L)).thenReturn(Optional.of(atout));

        Atout result = atoutService.getAtoutById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetAtoutById_NotFound() {
        when(atoutRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApiExecptionHandler.UserNotFoundException.class,
                () -> atoutService.getAtoutById(1L));
    }

    @Test
    void testGetAllSkills() {
        Atout a2 = new Atout();
        a2.setId(2L);

        when(atoutRepository.findAll()).thenReturn(Arrays.asList(atout, a2));

        List<Atout> result = atoutService.getAllSkills("ahmadou_macka_diallo");

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    void testUpdateAtout_Success() {
        when(atoutRepository.existsById(1L)).thenReturn(true);
        when(atoutRepository.save(atout)).thenReturn(atout);

        Atout updated = atoutService.updateAtout(atout);

        assertNotNull(updated);
        assertEquals(1L, updated.getId());
    }

    @Test
    void testUpdateAtout_NotFound() {
        when(atoutRepository.existsById(1L)).thenReturn(false);

        assertThrows(ApiExecptionHandler.UserNotFoundException.class,
                () -> atoutService.updateAtout(atout));
    }

    @Test
    void testDeleteAtout_Success() {
        when(atoutRepository.findById(1L)).thenReturn(Optional.of(atout));

        long start = System.nanoTime();
        Atout deleted = atoutService.deleteAtout(1L);
        long end = System.nanoTime();
        System.out.println("Temps : " + (end - start)/1_000_000 + " ms");

        assertNotNull(deleted);
        assertEquals(1L, deleted.getId());
        verify(atoutRepository, times(1)).delete(atout);
    }

    @Test
    void testDeleteAtout_NotFound() {
        when(atoutRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApiExecptionHandler.UserNotFoundException.class,
                () -> atoutService.deleteAtout(1L));
    }
}

package com.example.demoportflio.test.service;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.model.User;
import com.example.demoportflio_tpdevops_github_action_ci.repository.UserRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.implement.UserServiceImplement;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementTest {

    @InjectMocks
    private UserServiceImplement userService;

    @Mock
    private UserRepository userRepository;

    // ===============================
    // Test CreateUser
    // ===============================
    @Test
    void testCreateUser_Success() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Mockito.when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());

        Mockito.verify(userRepository).existsByEmail("test@example.com");
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        User user = new User();
        user.setEmail("exists@example.com");

        Mockito.when(userRepository.existsByEmail("exists@example.com")).thenReturn(true);

        Exception exception = assertThrows(ApiExecptionHandler.UserAlreadyExistsException.class, () -> {
            userService.createUser(user);
        });

        assertTrue(exception.getMessage().contains("Un utilisateur avec cet email existe déjà."));
    }

    // ===============================
    // Test listUser
    // ===============================
    @Test
    void testListUser() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        users.add(user);

        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.listUser();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());

        Mockito.verify(userRepository).findAll();
    }

    // ===============================
    // Test activeDesactive
    // ===============================
    @Test
    void testActiveDesactive_Success() {
        User user = new User();
        user.setId(1L);
        user.setActive(true);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenAnswer(i -> i.getArgument(0));

        long start = System.currentTimeMillis();
        User result = userService.activeDesactive(user);
        long end = System.currentTimeMillis();

        assertFalse(result.isActive());
        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(userRepository).save(user);

        System.out.println("Temps testActiveDesactive : " + (end - start) + " ms");
    }


    @Test
    void testActiveDesactive_UserNotFound() {
        User user = new User();
        user.setId(99L);

        Mockito.when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ApiExecptionHandler.UserNotFoundException.class, () -> {
            userService.activeDesactive(user);
        });

        assertTrue(exception.getMessage().contains("Utilisateur non trouvé"));
    }
}

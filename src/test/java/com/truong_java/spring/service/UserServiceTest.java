package com.truong_java.spring.service;

import com.truong_java.spring.dto.ChangePasswordDto;
import com.truong_java.spring.dto.UserDto;
import com.truong_java.spring.entity.UserEntity;
import com.truong_java.spring.repository.UserRepository;
import com.truong_java.spring.jwt.JWTGenerator;
import com.truong_java.spring.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTGenerator jwtGenerator;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void register_ShouldReturnSavedUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setName("John Doe");
        userDto.setUsername("johndoe");
        userDto.setPassword("password123");

        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setEmail("test@example.com");
        savedUser.setUsername("johndoe");
        savedUser.setName("John Doe");
        savedUser.setPassword("encodedPassword");

        Mockito.when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(savedUser);

        // Act
        UserEntity result = userService.register(userDto);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("test@example.com", result.getEmail());
        Assertions.assertEquals("John Doe", result.getName());
        Assertions.assertEquals("johndoe", result.getUsername());
        Assertions.assertEquals("encodedPassword", result.getPassword());

        Mockito.verify(passwordEncoder).encode("password123");
        Mockito.verify(userRepository).save(Mockito.any(UserEntity.class));
    }

    @Test
    void findAllUsers_ShouldReturnListOfUserDto() {
        // Arrange
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setEmail("john@example.com");
        user1.setUsername("johndoe");

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setName("Jane Smith");
        user2.setEmail("jane@example.com");
        user2.setUsername("janesmith");

        List<UserEntity> mockUsers = List.of(user1, user2);

        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        List<UserDto> result = userService.findAllUsers();

        // Assert
        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals("John Doe", result.get(0).getName());
        Assertions.assertEquals("johndoe", result.get(0).getUsername());

        Assertions.assertEquals("Jane Smith", result.get(1).getName());
        Assertions.assertEquals("janesmith", result.get(1).getUsername());

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void authenticateAndGenerateToken_ShouldReturnJwtToken() {
        // Arrange
        String username = "testuser";
        String password = "testpass";

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        String expectedToken = "jwt-token";

        Mockito.when(authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)))
                .thenReturn(authenticationMock);

        Mockito.when(jwtGenerator.generateToken(authenticationMock)).thenReturn(expectedToken);

        // Act
        String actualToken = userService.authenticateAndGenerateToken(username, password);

        // Assert
        Assertions.assertEquals(expectedToken, actualToken);

        Mockito.verify(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        Mockito.verify(jwtGenerator).generateToken(authenticationMock);
    }

    @Test
    void testChangePassword_InvalidUsername() {
        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setUsername("nonexistent");
        dto.setOldPassword("oldPass");
        dto.setNewPassword("newPass");
        dto.setConfirmPassword("newPass");

        Mockito.when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        String result = userService.changePassword(dto);

        Assertions.assertEquals("Invalid username", result);
    }

    @Test
    void testChangePassword_InvalidOldPassword() {
        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setUsername("john");
        dto.setOldPassword("wrongOldPass");
        dto.setNewPassword("newPass");
        dto.setConfirmPassword("newPass");

        UserEntity user = new UserEntity();
        user.setUsername("john");
        user.setPassword("encodedOldPass");

        Mockito.when(userRepository.findByUsername("john")).thenReturn(user);
        Mockito.when(passwordEncoder.matches("wrongOldPass", "encodedOldPass")).thenReturn(false);

        String result = userService.changePassword(dto);

        Assertions.assertEquals("Invalid old password", result);
    }

    @Test
    void testChangePassword_PasswordsDoNotMatch() {
        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setUsername("john");
        dto.setOldPassword("oldPass");
        dto.setNewPassword("newPass1");
        dto.setConfirmPassword("newPass2");

        UserEntity user = new UserEntity();
        user.setUsername("john");
        user.setPassword("encodedOldPass");

        Mockito.when(userRepository.findByUsername("john")).thenReturn(user);
        Mockito.when(passwordEncoder.matches("oldPass", "encodedOldPass")).thenReturn(true);

        String result = userService.changePassword(dto);

        Assertions.assertEquals("Passwords do not match", result);
    }

    @Test
    void testChangePassword_Success() {
        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setUsername("john");
        dto.setOldPassword("oldPass");
        dto.setNewPassword("newPass");
        dto.setConfirmPassword("newPass");

        UserEntity user = new UserEntity();
        user.setUsername("john");
        user.setPassword("encodedOldPass");

        Mockito.when(userRepository.findByUsername("john")).thenReturn(user);
        Mockito.when(passwordEncoder.matches("oldPass", "encodedOldPass")).thenReturn(true);
        Mockito.when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");

        String result = userService.changePassword(dto);

        Assertions.assertEquals("Change password successful", result);
        Assertions.assertEquals("encodedNewPass", user.getPassword());
        Mockito.verify(userRepository).save(user);
    }
}

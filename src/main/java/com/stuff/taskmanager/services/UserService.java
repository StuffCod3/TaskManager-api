package com.stuff.taskmanager.services;

import com.stuff.taskmanager.details.CustomUserDetailsService;
import com.stuff.taskmanager.dtos.AuthDto;
import com.stuff.taskmanager.dtos.TokenDto;
import com.stuff.taskmanager.models.User;
import com.stuff.taskmanager.repositories.RoleRepository;
import com.stuff.taskmanager.repositories.UserRepository;
import com.stuff.taskmanager.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByToken(String token){
        Optional<User> user = userRepository.findByEmail(jwtTokenUtils.getUsernameFromToken(token));
        return userRepository.findByEmail(user.get().getEmail());
    }

    public User signUp(AuthDto authDto){
        User user = new User();
        if (findByEmail(authDto.getEmail()).isEmpty()){
            user.setEmail(authDto.getEmail());
            user.setPassword(passwordEncoder.encode(authDto.getPassword()));
            user.setRole(roleRepository.findByName("ROLE_USER").get());
        }
        return userRepository.save(user);
    }

    public ResponseEntity<?> createAuthToken(AuthDto authDto){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authDto.getEmail());
            String token = jwtTokenUtils.generateToken(userDetails);
            return ResponseEntity.ok(new TokenDto(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверное имя пользователя или пароль");
        }
    }
}

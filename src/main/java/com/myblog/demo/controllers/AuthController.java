package com.myblog.demo.controllers;

import com.myblog.demo.dtos.LoginDTO;
import com.myblog.demo.dtos.RegisterDTO;
import com.myblog.demo.models.Role;
import com.myblog.demo.models.User;
import com.myblog.demo.repositories.RoleRepository;
import com.myblog.demo.repositories.UserRepository;
import com.myblog.demo.security.JwtAuthResponseDTO;
import com.myblog.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(
            @RequestBody LoginDTO loginDTO
            )
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthResponseDTO(token));
    }

    @PostMapping("/register")
    public  ResponseEntity<?> register(
            @RequestBody RegisterDTO registerDTO
            )
    {
        if(userRepository.existsByUsername(registerDTO.getUsername())) {
            return  new ResponseEntity<>("ese usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(registerDTO.getEmail())) {
            return  new ResponseEntity<>("ese usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return  new ResponseEntity<>("usuario registrado exitosamente", HttpStatus.OK);
    }

}

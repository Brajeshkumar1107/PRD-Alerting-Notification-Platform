package com.brajesh.controller;

import com.brajesh.dto.AuthResponse;
import com.brajesh.dto.LoginRequest;
import com.brajesh.dto.RegisterRequest;
import com.brajesh.model.Team;
import com.brajesh.model.User;
import com.brajesh.repository.TeamRepository;
import com.brajesh.repository.UserRepository;
import com.brajesh.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
                          UserRepository userRepository, TeamRepository teamRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .map(r -> r.replace("ROLE_", ""))
                .toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        String token = jwtService.generateToken(request.getUsername(), claims);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (userRepository.findAll().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(request.getEmail()))) {
            return ResponseEntity.badRequest().build();
        }
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setTeam(team);
        userRepository.save(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of("USER"));
        String token = jwtService.generateToken(user.getEmail(), claims);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}



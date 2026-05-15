package com.edutech.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import com.edutech.dto.CaptchaResponse;
import com.edutech.dto.LoginRequest;
import com.edutech.dto.LoginResponse;
import com.edutech.entity.User;
import com.edutech.service.CaptchaService;
import com.edutech.service.UserService;
import com.edutech.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    /*
     * Generate CAPTCHA for login page.
     */
    @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> getCaptcha() {
        return ResponseEntity.ok(captchaService.generateCaptcha());
    }

    /*
     * Register user.
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    /*
     * Login with CAPTCHA and account lock after 3 failed attempts.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        boolean captchaValid = captchaService.validateCaptcha(
                loginRequest.getCaptchaId(),
                loginRequest.getCaptchaAnswer()
        );

        if (!captchaValid) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid captcha. Please try again.");
            response.put("type", "CAPTCHA_ERROR");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        User user = userService.getUserByUsername(loginRequest.getUsername());

        if (userService.isAccountLocked(user)) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Account is locked due to 3 failed login attempts. Please try again after 5 minutes.");
            response.put("type", "ACCOUNT_LOCKED");
            response.put("attemptsLeft", 0);
            response.put("locked", true);

            return ResponseEntity.status(HttpStatus.LOCKED).body(response);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            userService.resetFailedLoginAttempts(user);

            String token = jwtUtil.generateToken(authentication.getName());

            String roleName = null;
            if (user.getRole() != null) {
                roleName = user.getRole().name();
            }

            LoginResponse response = new LoginResponse(
                    token,
                    roleName,
                    user.getId(),
                    user.getUsername()
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException ex) {

            int attemptsLeft = userService.recordFailedLoginAttempt(loginRequest.getUsername());

            Map<String, Object> response = new HashMap<>();

            if (attemptsLeft <= 0) {
                response.put("message", "Invalid username or password. Account locked for 5 minutes.");
                response.put("type", "ACCOUNT_LOCKED");
                response.put("attemptsLeft", 0);
                response.put("locked", true);

                return ResponseEntity.status(HttpStatus.LOCKED).body(response);
            }

            response.put("message", "Invalid username or password. Attempts left: " + attemptsLeft);
            response.put("type", "INVALID_CREDENTIALS");
            response.put("attemptsLeft", attemptsLeft);
            response.put("locked", false);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /*
     * Get logged-in user.
     */
    @GetMapping("/user")
    public ResponseEntity<User> getLoggedInUser(Principal principal) {
        User user = userService.getLoggedInUser(principal.getName());
        return ResponseEntity.ok(user);
    }
}
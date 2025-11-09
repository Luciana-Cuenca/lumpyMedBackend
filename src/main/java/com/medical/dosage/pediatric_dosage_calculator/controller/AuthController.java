package com.medical.dosage.pediatric_dosage_calculator.controller;

import com.medical.dosage.pediatric_dosage_calculator.model.User;
import com.medical.dosage.pediatric_dosage_calculator.model.Rol;
import com.medical.dosage.pediatric_dosage_calculator.security.JwtUtil;
import com.medical.dosage.pediatric_dosage_calculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;




import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> responseEntity(@RequestBody Map<String, String> userData ) {
        try{
            String username = userData.get("username");
            String email = userData.get("email");
            String password = userData.get("password");
            String role = userData.get("rol");

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setRol(role != null && role.equalsIgnoreCase("ADMIN") ? Rol.ADMIN: Rol.USER);

            userService.registerUser(newUser);
            return ResponseEntity.ok("usuario registrado");

        } catch(Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData){
        String username = loginData.get("username");
        String password = loginData.get("password");

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // si pasa la autenticacion, generar token
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of(
                    "message", "inicio de sesion exitoso",
                    "token", token
            ));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(Map.of("error", "credenciales incorrectas"));
        }
    }
}


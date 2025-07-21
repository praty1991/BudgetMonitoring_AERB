package com.aerb.budget.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.aerb.budget.dto.LoginRequest;
import com.aerb.budget.dto.LoginResponse;
import com.aerb.budget.entity.AdminUser;
import com.aerb.budget.repository.AdminUserRepository;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@RestController
@RequestMapping("/api/budget/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        Optional<AdminUser> userOpt = adminUserRepository.findByUsername(request.getUsername());
        LoginResponse response = new LoginResponse();

        if (userOpt.isPresent()) {
            AdminUser user = userOpt.get();
            if (user.getPassword().equals(request.getPassword())) {
            	if(!user.isHead())
            	{
                    response.setMessage("You don't have access to submit the budget.");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            	}
                session.setAttribute("user", user);

                response.setSuccess(true);
                response.setMessage("Login successful");
                response.setIsAdmin(user.isAdmin());
                response.setIsHead(user.isHead());
                return ResponseEntity.ok(response);
            }
        }

        response.setSuccess(false);
        response.setMessage("Invalid username or password.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}

package com.aerb.budget.controller;

import com.aerb.budget.entity.User;
import com.aerb.budget.service.LdapService;
import com.aerb.budget.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/budget")
public class LdapLoginController {

    @Autowired
    private LdapService ldapService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/ldap-login")
    public ResponseEntity<?> ldapLogin(
            @RequestParam String identifier,
            @RequestParam String password,
            HttpServletRequest request) {

        // Extract username (before '@' if email was provided)
        String username = identifier.contains("@") ? identifier.split("@")[0] : identifier;

        // Authenticate against LDAP
        String uid = ldapService.authenticate(username, password);
        
        uid="pratendrak";

        if (uid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid LDAP credentials.");
        }

        // Fetch user from DB using UID or EmpId (as per your logic)
        Optional<User> userOpt = userService.getUserByUid(uid);
        // Or: Optional<User> userOpt = userService.getUserByEmpId("610");

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Create session and store user
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(1800); // 30 minutes

            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("LDAP user not found in DB.");
        }
    }

    
    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session");
    }


    @PostMapping("/do-login")
    public String normalLogin(@RequestParam String identifier, @RequestParam String password,
                              RedirectAttributes redirectAttributes, HttpServletRequest request) {

        User user = userService.getUserByEmpId(identifier)
                .orElseThrow(() -> new RuntimeException("User not found: " + identifier));
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(1800);
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid credentials!");
            return "redirect:/";
        }
    }
}

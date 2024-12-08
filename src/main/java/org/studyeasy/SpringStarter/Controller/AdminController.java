package org.studyeasy.SpringStarter.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.studyeasy.SpringStarter.Model.Theme;
import org.studyeasy.SpringStarter.Model.User;
import org.studyeasy.SpringStarter.Repository.ThemeRepository;
import org.studyeasy.SpringStarter.Request.LoginRequest;
import org.studyeasy.SpringStarter.Response.LoginResponse;
import org.studyeasy.SpringStarter.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:3000") // Allow cross-origin requests from React
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Log the incoming login request for debugging
        System.out.println("Received login request with email: " + email);

        // Validate credentials against the database.
        User user = userService.findByEmail(email);

        if (user == null) {
            // No user found with the provided email
            System.out.println("No user found with email: " + email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error: No user found with this email.");
        }

        // Check if the provided password matches the one in the database
        if (!user.getPassword().equals(password)) {
            // Incorrect password
            System.out.println("Incorrect password for email: " + email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error: Incorrect password for this email.");
        }

        // If the user exists and the password matches, return a successful response
        System.out.println("Login successful for email: " + email);
        return ResponseEntity.ok(new LoginResponse("Login successful", user.getRole()));
    }
    @Autowired
    private ThemeRepository themeRepository;

    @GetMapping("/themes")
    @ResponseBody
    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    @PostMapping("/add-theme")
    public ResponseEntity<String> addTheme(@RequestBody Theme theme) {
        if (theme.getName() == null || theme.getImageUrl() == null) {
            return ResponseEntity.badRequest().body("Theme name and image URL are required!");
        }
        themeRepository.save(theme);
        return ResponseEntity.ok("Theme added successfully!");
    }

    @PutMapping("/edit-theme/{id}")
    @ResponseBody
    public ResponseEntity<String> editTheme(@PathVariable Long id, @RequestBody Theme updatedTheme) {
        Optional<Theme> themeOptional = themeRepository.findById(id);
        if (themeOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Theme not found");
        }

        Theme theme = themeOptional.get();
        theme.setName(updatedTheme.getName());
        theme.setImageUrl(updatedTheme.getImageUrl());
        themeRepository.save(theme);
        return ResponseEntity.ok("Theme updated successfully!");
    }
    @DeleteMapping("/themes/{id}")
public ResponseEntity<String> deleteTheme(@PathVariable Long id) {
    if (!themeRepository.existsById(id)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theme not found");
    }
    themeRepository.deleteById(id);
    return ResponseEntity.noContent().build();
}

    
    
}

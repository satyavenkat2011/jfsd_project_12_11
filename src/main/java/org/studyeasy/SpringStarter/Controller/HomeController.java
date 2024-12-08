package org.studyeasy.SpringStarter.Controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.studyeasy.SpringStarter.Model.User;
import org.studyeasy.SpringStarter.Repository.UserRepository;
import org.studyeasy.SpringStarter.service.EmailService;
import org.studyeasy.SpringStarter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.studyeasy.SpringStarter.Model.Theme;
import org.studyeasy.SpringStarter.Repository.ThemeRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        model.addAttribute("isLoggedIn", user != null); // Set isLoggedIn in the model
        return "home";
    }

    @GetMapping("/book")
    public String book(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("isLoggedIn", user != null);
        return "book";
    }

    @GetMapping("/buy")
    public String buy(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("isLoggedIn", user != null);
        return "buy";
    }
    @GetMapping("/bedroom-theme")
    public String bedroomtheme(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("isLoggedIn", user != null);
        return "bedroom-theme";
        
    }
    @GetMapping("/drawing-room-theme")
    public String drawingroomtheme(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("isLoggedIn", user != null);
        return "drawing-room-theme";
        
    }
    @GetMapping("/kitchen-theme")
public String kitchentheme(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    model.addAttribute("isLoggedIn", user != null);

    // Fetch themes from the repository
    List<Theme> themes = themeRepository.findAll(); 
    model.addAttribute("themes", themes); // Add themes to the model

    return "kitchen-theme"; // Return the Thymeleaf template
}
@PutMapping("/admin/edit-theme/{id}")
@ResponseBody
public ResponseEntity<String> editTheme(@PathVariable Long id, @RequestBody Theme updatedTheme) {
    Theme theme = themeRepository.findById(id).orElse(null);
    if (theme == null) {
        return ResponseEntity.status(404).body("Theme not found");
    }
    theme.setName(updatedTheme.getName());
    theme.setImageUrl(updatedTheme.getImageUrl());
    themeRepository.save(theme);
    return ResponseEntity.ok("Theme updated successfully");
}

@DeleteMapping("/themes/{id}")
public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
    themeRepository.deleteById(id);
    return ResponseEntity.noContent().build();
}

    @GetMapping("/login")
    public String homeLogin(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user); // Store user in session
            return "redirect:/profile"; // Redirect to profile page
        } else {
            model.addAttribute("error", "Invalid credentials!");
            return "login"; // Return to login page with error
        }
    }
    
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    @PostMapping("/profile/update")
public String updateProfile(
        @RequestParam("kitchenSize") String kitchenSize,
        @RequestParam("woodPreference") String woodPreference,
        @RequestParam("flooringType") String flooringType,
        @RequestParam("numberOfBedrooms") Integer numberOfBedrooms,
        @RequestParam("wallColor") String wallColor,
        @RequestParam(value = "openFloorPlan", defaultValue = "false") Boolean openFloorPlan,
        @RequestParam("remodelingBudget") Double remodelingBudget,
        HttpSession session) {
    
    User user = (User) session.getAttribute("user");
    if (user != null) {
        user.setKitchenSize(kitchenSize);
        user.setWoodPreference(woodPreference);
        user.setFlooringType(flooringType);
        user.setNumberOfBedrooms(numberOfBedrooms);
        user.setWallColor(wallColor);
        user.setRemodelingBudget(remodelingBudget);
        
        userRepository.save(user);
        session.setAttribute("user", user); // Update session with new data
    }
    return "redirect:/profile";
}
@Autowired
private ThemeRepository themeRepository;

// Add Theme
@PostMapping("/admin/add-theme")
public String addTheme(@RequestBody Theme theme, Model model) {
    if (theme.getName() == null || theme.getName().isEmpty()) {
        model.addAttribute("message", "Theme name is required!");
        return "redirect:/admin/themes";
    }
    if (theme.getImageUrl() == null || theme.getImageUrl().isEmpty()) {
        model.addAttribute("message", "Image URL is required!");
        return "redirect:/admin/themes";
    }

    themeRepository.save(theme);
    model.addAttribute("message", "Theme added successfully!");
    return "redirect:/admin/themes";
}
 // Get all themes
 @GetMapping("/themes")
@ResponseBody
public List<Theme> getAllThemes() {
    return themeRepository.findAll(); // This will return all themes stored in the database
}
  

// Admin page for managing themes (list themes and add new theme)
@GetMapping("/admin/themes")
public String getAdminThemesPage(Model model) {
    List<Theme> themes = themeRepository.findAll(); // Fetch all themes from the database
    model.addAttribute("themes", themes); // Add themes to the model to display in the view
    return "admin/themes"; // Return the themes management page
}

// Display theme form for admin to add a theme
@GetMapping("/admin/add-theme")
public String getAddThemePage(Model model) {
    return "admin/add-theme"; // Return the page where the admin can add a new theme
}

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email is already registered!");
            return "signup";
        }

        // Validate full name
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            model.addAttribute("error", "Full name is required.");
            return "signup";
        }
        if (user.getRole() == null) {
            user.setRole("USER"); // Assign a default role
        }
        // Save user and redirect to login
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/smarthome")
    public String smarthome(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("isLoggedIn", user != null);
        return "smarthome";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear the session
        return "redirect:/login"; // Redirect to login page after logout
    }
    @Autowired
    private EmailService emailService;

    @PostMapping("/book")
    public String bookMeeting(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("date") String date
    ) {
        // Compose the email content
        String subject = "Meeting Booking Confirmation";
        String body = "Hello " + name + ",\n\n" +
                      "Thank you for booking a meeting.\n\n" +
                      "Details:\n" +
                      "Name: " + name + "\n" +
                      "Phone: " + phone + "\n" +
                      "Email: " + email + "\n" +
                      "Date: " + date + "\n\n" +
                      "We look forward to meeting you!";

        // Send the email
        emailService.sendBookingConfirmation(email, subject, body);

        // Redirect to a success page or show a confirmation message
        return "confirmation";

    }
    
@GetMapping("/confirmation")
public String confirmationPage(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    model.addAttribute("isLoggedIn", user != null);
    return "confirmation";
}



    // Profile page mapping
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }
        return "profile"; // Ensure this matches the profile.html file name
    }
    
    

}

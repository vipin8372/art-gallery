package com.example.userRegistrationAndLogin.controller;

import com.example.userRegistrationAndLogin.entity.*;
import com.example.userRegistrationAndLogin.service.CategoryService;
import com.example.userRegistrationAndLogin.service.OrderService;
import com.example.userRegistrationAndLogin.service.ProductService;
import com.example.userRegistrationAndLogin.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, @RequestParam String role, Model model) {
        if (!isEmailValid(user.getEmail())) {
            model.addAttribute("error", "Invalid email format.");
            return "register";
        }

        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email is already registered.");
            return "register";
        }

        if (!isPasswordValid(user.getPassword())) {
            model.addAttribute("error", "Password requirements not met.");
            return "register";
        }

        user.setRole(role);
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model, HttpSession session) {
        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            if ("admin".equals(user.getRole())) {
                return "redirect:/admin";
            } else {
                return "redirect:/home";
            }
        } else {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }
    }

    @GetMapping("/home")
    public String userHome(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals("user")) {
            return "redirect:/login";
        }

        List<Category> categories = categoryService.getAllCategories();
        Map<Category, List<Product>> categorizedProducts = new HashMap<>();

        for (Category category : categories) {
            List<Product> products = productService.getProductsByCategory(category.getName());
            categorizedProducts.put(category, products);
        }

        model.addAttribute("user", loggedInUser);
        model.addAttribute("categorizedProducts", categorizedProducts);
        return "home";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
    @GetMapping("/orders")
    public String getUserOrders(Model model, HttpSession session) {
        // Get the logged-in user from the session
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // Check if the user is logged in
        if (loggedInUser == null) {
            // If not logged in, redirect to the login page
            return "redirect:/login";
        }

        // Retrieve orders for the logged-in user
        List<Order> userOrders = orderService.getOrdersByUserId(loggedInUser.getId());

        // Add user orders to the model
        model.addAttribute("userOrders", userOrders);

        // Return the view name for user orders
        return "user-orders";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    @GetMapping("/catogery")
    public String categoryPage() {
        return "catogery";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("passwordResetRequest", new PasswordResetRequest());
        return "forgot-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute("passwordResetRequest") PasswordResetRequest passwordResetRequest,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "forgot-password";
        }

        String email = passwordResetRequest.getEmail();
        String newPassword = passwordResetRequest.getNewPassword();
        String confirmPassword = passwordResetRequest.getConfirmPassword();

        if (!isEmailValid(email)) {
            model.addAttribute("error", "Invalid email format.");
            return "forgot-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "forgot-password";
        }

        if (!isPasswordValid(newPassword)) {
            model.addAttribute("error", "Password requirements not met.");
            return "forgot-password";
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Email not registered.");
            return "forgot-password";
        }

        user.setPassword(newPassword);
        userService.updateUser(user);

        model.addAttribute("message", "Password changed successfully. Please login with your new password.");
        return "login";
    }

    private boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";
        return Pattern.matches(passwordPattern, password);
    }

    private boolean isEmailValid(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailPattern, email);
    }

    @GetMapping("/category/{categoryName}")
    public String getProductsByCategory(@PathVariable String categoryName, Model model) {
        List<Product> products = productService.getProductsByCategory(categoryName);
        model.addAttribute("products", products);
        model.addAttribute("categoryName", categoryName);
        return "category";
    }

    @GetMapping("/oilpaint")
    public String getOilpaint(Model model) {
        List<Product> oilpaint = productService.getProductsByCategory("oilpaint");
        model.addAttribute("products", oilpaint);
        return "oilpaint";
    }

    @GetMapping("/abstractpaint")
    public String getAbstract(Model model) {
        List<Product> abstractpaint = productService.getProductsByCategory("abstractpaint");
        model.addAttribute("products", abstractpaint);
        return "abstractpaint";
    }

    @GetMapping("/watercolor")
    public String getWatercolor(Model model) {
        List<Product> watercolor = productService.getProductsByCategory("watercolor");
        model.addAttribute("products", watercolor);
        return "watercolor";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/admin/delete-user/{id}")
    public String deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


    }


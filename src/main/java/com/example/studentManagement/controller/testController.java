package com.example.studentManagement.controller;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class testController {
    @GetMapping("/firebase-test")
    public String testFirebase() {
        try {
            // Get default FirebaseApp instance
            String appName = FirebaseApp.getInstance().getName(); // <-- corrected
            return "Firebase is connected! App name: " + appName;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to connect Firebase: " + e.getMessage();
        }
    }
}

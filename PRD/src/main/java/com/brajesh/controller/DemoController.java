package com.brajesh.controller;

import com.brajesh.model.Alert;
import com.brajesh.model.Team;
import com.brajesh.model.User;
import com.brajesh.model.enums.DeliveryType;
import com.brajesh.model.enums.Severity;
import com.brajesh.model.enums.VisibilityType;
import com.brajesh.repository.AlertRepository;
import com.brajesh.repository.TeamRepository;
import com.brajesh.repository.UserRepository;
import com.brajesh.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AlertRepository alertRepository;
    
    @Autowired
    private AlertService alertService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to PRD: Alerting & Notification Platform";
    }

    @PostMapping("/seed")
    public Map<String, Object> seedData() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Create Teams
            Team engineering = new Team();
            engineering.setName("Engineering");
            engineering = teamRepository.save(engineering);
            
            Team marketing = new Team();
            marketing.setName("Marketing");
            marketing = teamRepository.save(marketing);
            
            Team sales = new Team();
            sales.setName("Sales");
            sales = teamRepository.save(sales);
            
            // Create Users
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@example.com");
            admin.setPassword("$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi"); // password
            admin.setTeam(engineering);
            userRepository.save(admin);
            
            User dev1 = new User();
            dev1.setName("John Developer");
            dev1.setEmail("john@example.com");
            dev1.setPassword("$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi"); // password
            dev1.setTeam(engineering);
            userRepository.save(dev1);
            
            User dev2 = new User();
            dev2.setName("Jane Coder");
            dev2.setEmail("jane@example.com");
            dev2.setPassword("$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi"); // password
            dev2.setTeam(engineering);
            userRepository.save(dev2);
            
            User marketer1 = new User();
            marketer1.setName("Alice Marketer");
            marketer1.setEmail("alice@example.com");
            marketer1.setPassword("$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi"); // password
            marketer1.setTeam(marketing);
            userRepository.save(marketer1);
            
            User marketer2 = new User();
            marketer2.setName("Bob Promoter");
            marketer2.setEmail("bob@example.com");
            marketer2.setPassword("$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi"); // password
            marketer2.setTeam(marketing);
            userRepository.save(marketer2);
            
            User sales1 = new User();
            sales1.setName("Charlie Sales");
            sales1.setEmail("charlie@example.com");
            sales1.setPassword("$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi"); // password
            sales1.setTeam(sales);
            userRepository.save(sales1);
            
            // Create Sample Alerts
            Alert alert1 = new Alert();
            alert1.setTitle("System Maintenance");
            alert1.setMessage("Scheduled maintenance on Sunday 2AM-4AM. Please save your work.");
            alert1.setSeverity(Severity.WARNING);
            alert1.setDeliveryType(DeliveryType.IN_APP);
            alert1.setStartTime(LocalDateTime.now().minusHours(1));
            alert1.setExpiryTime(LocalDateTime.now().plusDays(3));
            alert1.setRemindersEnabled(true);
            alert1.setVisibilityType(VisibilityType.ORGANIZATION);
            alertRepository.save(alert1);
            
            Alert alert2 = new Alert();
            alert2.setTitle("Team Meeting");
            alert2.setMessage("Engineering team meeting at 3PM today in Conference Room A.");
            alert2.setSeverity(Severity.INFO);
            alert2.setDeliveryType(DeliveryType.IN_APP);
            alert2.setStartTime(LocalDateTime.now().minusMinutes(30));
            alert2.setExpiryTime(LocalDateTime.now().plusHours(6));
            alert2.setRemindersEnabled(true);
            alert2.setVisibilityType(VisibilityType.TEAM);
            alert2.setTeams(List.of(engineering));
            alertRepository.save(alert2);
            
            Alert alert3 = new Alert();
            alert3.setTitle("Security Update Required");
            alert3.setMessage("Critical security update available. Please update immediately.");
            alert3.setSeverity(Severity.CRITICAL);
            alert3.setDeliveryType(DeliveryType.IN_APP);
            alert3.setStartTime(LocalDateTime.now());
            alert3.setExpiryTime(LocalDateTime.now().plusDays(1));
            alert3.setRemindersEnabled(true);
            alert3.setVisibilityType(VisibilityType.USER);
            alert3.setUsers(List.of(admin, dev1));
            alertRepository.save(alert3);
            
            Alert alert4 = new Alert();
            alert4.setTitle("Marketing Campaign Launch");
            alert4.setMessage("New product campaign launches tomorrow. All marketing materials ready.");
            alert4.setSeverity(Severity.INFO);
            alert4.setDeliveryType(DeliveryType.IN_APP);
            alert4.setStartTime(LocalDateTime.now().minusHours(2));
            alert4.setExpiryTime(LocalDateTime.now().plusDays(2));
            alert4.setRemindersEnabled(false);
            alert4.setVisibilityType(VisibilityType.TEAM);
            alert4.setTeams(List.of(marketing));
            alertRepository.save(alert4);
            
            Alert alert5 = new Alert();
            alert5.setTitle("Sales Target Update");
            alert5.setMessage("Q1 sales targets have been updated. Check your dashboard for details.");
            alert5.setSeverity(Severity.INFO);
            alert5.setDeliveryType(DeliveryType.IN_APP);
            alert5.setStartTime(LocalDateTime.now().minusMinutes(15));
            alert5.setExpiryTime(LocalDateTime.now().plusDays(7));
            alert5.setRemindersEnabled(true);
            alert5.setVisibilityType(VisibilityType.TEAM);
            alert5.setTeams(List.of(sales));
            alertRepository.save(alert5);
            
            result.put("message", "Database seeded successfully");
            result.put("teamsCreated", 3);
            result.put("usersCreated", 6);
            result.put("alertsCreated", 5);
            result.put("credentials", Map.of(
                "admin", "admin@example.com / password",
                "users", "john@example.com, jane@example.com, alice@example.com, bob@example.com, charlie@example.com / password"
            ));
            
        } catch (Exception e) {
            result.put("error", "Failed to seed data: " + e.getMessage());
        }
        
        return result;
    }
    
    @PostMapping("/trigger-reminders")
    public Map<String, Object> triggerReminders() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            alertService.triggerReminders();
            result.put("message", "Reminders triggered successfully");
            result.put("timestamp", LocalDateTime.now());
        } catch (Exception e) {
            result.put("error", "Failed to trigger reminders: " + e.getMessage());
        }
        
        return result;
    }
}

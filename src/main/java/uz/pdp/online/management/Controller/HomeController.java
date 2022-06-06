package uz.pdp.online.management.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String getInformation() {
        return "/api/auth/addHrManager  -- add new hr manager for director\n" +
                "/api/auth/addManager  -- add new manager for director\n" +
                "/api/auth/addEmployee   -- add new employee for director and hr_manager\n" +
                "/api/auth/login  -- login into cabinet for everone\n" +
                "/api/auth/changePassword -- change own password for everyone who is logged in\n";
    }
}

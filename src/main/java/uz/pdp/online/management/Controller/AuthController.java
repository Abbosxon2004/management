package uz.pdp.online.management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.management.Payload.ApiResponse;
import uz.pdp.online.management.Payload.LoginDto;
import uz.pdp.online.management.Payload.RegistreDto;
import uz.pdp.online.management.Repository.RoleRepo;
import uz.pdp.online.management.Service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    RoleRepo roleRepo;

    @PostMapping("/register")
    public ResponseEntity registerForDirector(@RequestBody RegistreDto registreDto){
        return authService.registerForDirector(registreDto);
    }

    @PreAuthorize(value = "hasRole('ROLE_DIRECTOR')")
    @PostMapping("/addHrManager")
    public ResponseEntity addHrManager(@RequestBody RegistreDto registreDto) {
        ApiResponse apiResponse = authService.addHRManagerByDirector(registreDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('ROLE_DIRECTOR')")
    @PostMapping("/addManager")
    public ResponseEntity addManager(@RequestBody RegistreDto registreDto) {
        ApiResponse apiResponse = authService.addManagerByDirector(registreDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasAnyRole('ROLE_HR_MANAGEMENT','ROLE_DIRECTOR')")
    @PostMapping("/addEmployee")
    public ResponseEntity addEmployee(@RequestBody RegistreDto registreDto) {
        ApiResponse apiResponse = authService.addEmployee(registreDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = authService.login(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }


    @GetMapping("/verifyEmail")
    public ResponseEntity verfiyEmail(@RequestParam String emailCode, @RequestParam String email) {
        ApiResponse apiResponse = authService.verifyEmail(email, emailCode);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse=authService.changePassword(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }
}

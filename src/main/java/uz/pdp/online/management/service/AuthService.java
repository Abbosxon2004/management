package uz.pdp.online.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.online.management.entity.Employees;
import uz.pdp.online.management.entity.RoleNames;
import uz.pdp.online.management.payload.ApiResponse;
import uz.pdp.online.management.payload.LoginDto;
import uz.pdp.online.management.payload.RegisterDto;
import uz.pdp.online.management.repository.EmployeesRepository;
import uz.pdp.online.management.repository.RoleRepository;
import uz.pdp.online.management.security.JwtProvider;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeesRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ApiResponse registerEmployee(RegisterDto registerDto) {
        boolean existsByEmail = employeesRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("That employee already exists", false);
        Employees employees = new Employees();
        employees.setFirstName(registerDto.getFirstName());
        employees.setLastName(registerDto.getLastName());
        employees.setEmail(registerDto.getEmail());
        employees.setPhoneNumber(registerDto.getPhoneNumber());
        employees.setRoles(Collections.singleton(roleRepository.findByRoleNames(RoleNames.ROLE_EMPLOYEE)));
        employees.setEmailCode(UUID.randomUUID().toString());
        employeesRepository.save(employees);
        sendEmailForVerification(employees.getEmail(), employees.getEmailCode());
        return new ApiResponse("We sent email to " + registerDto.getEmail() + ". Please verify your email", true);

    }

    public ApiResponse registerManager(RegisterDto registerDto) {
        boolean existsByEmail = employeesRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("That employee already exists", false);
        Employees employees = new Employees();
        employees.setFirstName(registerDto.getFirstName());
        employees.setLastName(registerDto.getLastName());
        employees.setEmail(registerDto.getEmail());
        employees.setPhoneNumber(registerDto.getPhoneNumber());
        employees.setRoles(Collections.singleton(roleRepository.findByRoleNames(RoleNames.ROLE_MANAGER)));
        employees.setEmailCode(UUID.randomUUID().toString());
        employeesRepository.save(employees);
        sendEmailForVerification(employees.getEmail(), employees.getEmailCode());
        return new ApiResponse("We sent email to " + registerDto.getEmail() + ". Please verify your email", true);
    }
    public ApiResponse registerDirector(RegisterDto registerDto) {
        boolean existsByEmail = employeesRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("That employee already exists", false);
        Employees employees = new Employees();
        employees.setFirstName(registerDto.getFirstName());
        employees.setLastName(registerDto.getLastName());
        employees.setEmail(registerDto.getEmail());
        employees.setPhoneNumber(registerDto.getPhoneNumber());
        employees.setRoles(Collections.singleton(roleRepository.findByRoleNames(RoleNames.ROLE_DIRECTOR)));
        employees.setEmailCode(UUID.randomUUID().toString());
        employeesRepository.save(employees);
        sendEmailForVerification(employees.getEmail(), employees.getEmailCode());
        return new ApiResponse("We sent email to " + registerDto.getEmail() + ". Please verify your email", true);

    }

    public void sendEmailForVerification(String email, String emailCode) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("Test@pdp.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Verify your email and create a new password via the link");
        simpleMailMessage.setText("http://localhost:8080/api/auth/verifyEmail?email=" + email + "&emailCode=" + emailCode);
        javaMailSender.send(simpleMailMessage);
    }
    public void sendEmail(String email, Date deadline) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("Test@pdp.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("New Assignment");
        simpleMailMessage.setText("You have been assigned a task. Task deadline by "+simpleDateFormat.format(deadline));
        javaMailSender.send(simpleMailMessage);
    }

    public void sendEmailAboutTaskStatus(String email,String employeeName,String taskStatus) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("nsakhobiddin@mail.ru");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Task status has been changed");
        simpleMailMessage.setText("Task status changed by "+employeeName+" to "+taskStatus);
        javaMailSender.send(simpleMailMessage);
    }

    public ApiResponse verifyEmail(String email, String emailCode, String password) {
        Optional<Employees> byEmail = employeesRepository.findByEmailAndEmailCode(email, emailCode);
        if (byEmail.isPresent()) {
            Employees employees = byEmail.get();
            employees.setEnabled(true);
            employees.setEmailCode(null);
            employees.setPassword(passwordEncoder.encode(password));
            employeesRepository.save(employees);
            return new ApiResponse("Email successfully verified", true);

        }
        return new ApiResponse("User not found", false);
    }

    public ApiResponse loginToSystem(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()));
            Employees employee = (Employees) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getEmail(),employee.getRoles());
            return new ApiResponse(token, true);
        } catch (BadCredentialsException badCredentialsException) {
            return new ApiResponse("Username or password is incorrect", false);
        }
    }



}
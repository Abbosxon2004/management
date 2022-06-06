package uz.pdp.online.management.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.online.management.Entity.Enums.RoleName;
import uz.pdp.online.management.Entity.Role;
import uz.pdp.online.management.Entity.User;
import uz.pdp.online.management.Payload.ApiResponse;
import uz.pdp.online.management.Payload.LoginDto;
import uz.pdp.online.management.Payload.RegistreDto;
import uz.pdp.online.management.Repository.RoleRepo;
import uz.pdp.online.management.Repository.UserRepo;
import uz.pdp.online.management.Security.JwtProvider;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + " bunday username topilmadi"));
    }

    public ResponseEntity registerForDirector(RegistreDto registreDto) {
//        User byRoles = userRepo.findByRoles(Collections.singleton(roleRepo.findByRoleName(RoleName.ROLE_DIRECTOR)));

        boolean directorRole = false;// byRoles==null?false:true;
        if (directorRole)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Bu yo`l faqat director faqat 1 marta foydalanishi mumkin", false));
        User user = new User();
        user.setFirstName(registreDto.getFirstName());
        user.setLastName(registreDto.getLastName());
        user.setEmail(registreDto.getEmail());
        user.setPassword(passwordEncoder.encode(registreDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepo.findByRoleName(RoleName.ROLE_DIRECTOR)));

        user.setEmailCode(UUID.randomUUID().toString());
        userRepo.save(user);
        boolean directorSent = sendEmail(user.getEmail(), user.getEmailCode(), registreDto.getPassword());
        if (!directorSent)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("Emailga xabar jo`natilmadi", false));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Muvaffaqiyatli qo`shildi. Email orqali tasdiqlandi.", true));
    }

    public ApiResponse addHRManagerByDirector(RegistreDto registreDto) {
        boolean existsByEmail = userRepo.existsByEmail(registreDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("Bu email bilan allaqavhon ro`yxatdan o`tilgan", false);
        User user = new User();
        user.setFirstName(registreDto.getFirstName());
        user.setLastName(registreDto.getLastName());
        user.setEmail(registreDto.getEmail());
        user.setPassword(passwordEncoder.encode(registreDto.getPassword()));

        user.setRoles(Collections.singleton(roleRepo.findByRoleName(RoleName.ROLE_HR_MANAGER)));

        user.setEmailCode(UUID.randomUUID().toString());
        userRepo.save(user);
        boolean isSent = sendEmail(user.getEmail(), user.getEmailCode(), registreDto.getPassword());
        if (!isSent)
            return new ApiResponse("Emailga xabar jo`natilmadi", false);
        return new ApiResponse("Muvaffaqiyatli qo`shildi.Email orqali tasdiqlansin", true);
    }

    public ApiResponse addManagerByDirector(RegistreDto registreDto) {
        boolean existsByEmail = userRepo.existsByEmail(registreDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("Bu email bilan allaqavhon ro`yxatdan o`tilgan", false);
        User user = new User();
        user.setFirstName(registreDto.getFirstName());
        user.setLastName(registreDto.getLastName());
        user.setEmail(registreDto.getEmail());
        user.setPassword(passwordEncoder.encode(registreDto.getPassword()));

        user.setRoles(Collections.singleton(roleRepo.findByRoleName(RoleName.ROLE_MANAGER)));

        user.setEmailCode(UUID.randomUUID().toString());
        userRepo.save(user);
        boolean isSent = sendEmail(user.getEmail(), user.getEmailCode(), registreDto.getPassword());
        if (!isSent)
            return new ApiResponse("Emailga xabar jo`natilmadi", false);
        return new ApiResponse("Muvaffaqiyatli qo`shildi.Email orqali tasdiqlansin", true);
    }

    public ApiResponse addEmployee(RegistreDto registreDto) {
        boolean existsByEmail = userRepo.existsByEmail(registreDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("Bu email bilan allaqchon ro`yxatdan o`tilgan", false);
        User user = new User();
        user.setFirstName(registreDto.getFirstName());
        user.setLastName(registreDto.getLastName());
        user.setEmail(registreDto.getEmail());
        user.setPassword(passwordEncoder.encode(registreDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepo.findByRoleName(RoleName.ROLE_EMPLOYEE)));

        user.setEmailCode(UUID.randomUUID().toString());
        userRepo.save(user);
        boolean isSent = sendEmail(user.getEmail(), user.getEmailCode(), registreDto.getPassword());
        if (!isSent)
            return new ApiResponse("Emailga xabar jo`natilmadi", false);
        return new ApiResponse("Muvaffaqiyatli qo`shildi. Email orqali tasdiqlandi.", true);
    }

    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()));
            if (authenticate.isAuthenticated()){
                String token = jwtProvider.generateToken(loginDto.getUsername(), authenticate.getAuthorities());
                return new ApiResponse("Token : ", true, token);
            }
            return new ApiResponse("Username and Password not matched", false);
        } catch (BadCredentialsException e) {
            return new ApiResponse("INVALID_CREDENTIALS", false);
        }

    }

    public ApiResponse verifyEmail(String email, String emailCode) {
        Optional<User> optionalUser = userRepo.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepo.save(user);
            return new ApiResponse("Account tasdiqlandi.", true);
        }
        return new ApiResponse("Account allaqchon tasdiqlangan", false);
    }

    public ApiResponse changePassword(LoginDto loginDto) {
        Optional<User> optionalUser = userRepo.findByEmail(loginDto.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(loginDto.getNewPassword());
            userRepo.save(user);
            return new ApiResponse("Parol yangilandi", true);
        }
        return new ApiResponse("Qayta urinib ko`ring.Xatolik yuz berdi", false);
    }

    public boolean sendEmail(String sendingEmail, String emailCode, String password) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Test@pdp.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Accountni tasdiqlash");
            mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>" +
                    "Tasdiqlang" + "</a>" + "\nUshbu parol bilan login yo`liga otib o`z parolingizni o`rnating     Password: " + password);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

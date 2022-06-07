package uz.pdp.online.management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}

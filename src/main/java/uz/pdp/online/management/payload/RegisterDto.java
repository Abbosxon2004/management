package uz.pdp.online.management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @Size(min = 2, max = 20)
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String lastName;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Size(min = 7)
    private String phoneNumber;

}

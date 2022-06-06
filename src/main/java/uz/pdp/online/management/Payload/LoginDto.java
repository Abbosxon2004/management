package uz.pdp.online.management.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotNull
    @Email
    private String username;

    @NotNull
    private String password;

    private String newPassword;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

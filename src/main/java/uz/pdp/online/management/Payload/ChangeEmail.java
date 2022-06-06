package uz.pdp.online.management.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeEmail {
    private String lastEmail;
    private String newEmail;
}

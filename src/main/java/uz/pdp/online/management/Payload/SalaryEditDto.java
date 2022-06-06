package uz.pdp.online.management.Payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryEditDto {

    private String email;
    private Double salary;

}

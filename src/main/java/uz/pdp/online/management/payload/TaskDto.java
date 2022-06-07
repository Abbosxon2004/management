package uz.pdp.online.management.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @NotNull
    private String name;

    @NotNull
    private String taskDefinition;

    @NotNull
    private Date deadline;

    @NotNull
    private UUID employeeId;
}

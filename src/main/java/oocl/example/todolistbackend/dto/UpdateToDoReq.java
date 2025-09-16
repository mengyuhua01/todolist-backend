package oocl.example.todolistbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateToDoReq {
    @NotBlank(message = "text不能为空")
    private String text;

    @NotNull(message = "done不能为空")
    private Boolean done;
}

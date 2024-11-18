package br.com.buscapetapi.buscapetapi.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequestInput {
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    private String name;

    @Size(max = 15, message = "O telefone deve ter no máximo 15 caracteres")
    private String phone;
}

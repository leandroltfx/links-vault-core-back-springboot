package br.com.links_vault_core_back_springboot.module.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegistrationRequestDTO {

    @NotNull(message = "Informe o nome de usuário.")
    @Pattern(regexp = "^[A-Za-z0-9]{3,30}$", message = "O nome de usuário deve conter entre 3 e 30 caracteres alfanuméricos e não deve ter espaços em branco.")
    private String username;

    @NotNull(message = "Informe o e-mail.")
    @Pattern(regexp = "^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+(\\.[a-z]+)?$", message = "E-mail inválido.")
    private String email;

    @NotNull(message = "Informe a senha.")
    @Pattern(regexp = "^.{8,80}$", message = "A senha deve conter entre 8 e 80 caracteres.")
    private String password;

}

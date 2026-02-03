package br.com.links_vault_core_back_springboot.module.user.controller;

import br.com.links_vault_core_back_springboot.dto.ApiResponseDTO;
import br.com.links_vault_core_back_springboot.module.user.dto.UserRegistrationRequestDTO;
import br.com.links_vault_core_back_springboot.module.user.dto.UserRegistrationResponseDTO;
import br.com.links_vault_core_back_springboot.module.user.useCase.UserRegistrationUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserRegistrationUseCase userRegistrationUseCase;

    @PostMapping
    public ResponseEntity<ApiResponseDTO> createUser(
            @Valid @RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO
    ) {
        UserRegistrationResponseDTO userRegistrationResponseDTO = userRegistrationUseCase.execute(userRegistrationRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponseDTO(
                                "Usuário cadastrado com sucesso!",
                                userRegistrationResponseDTO
                        )
                );
    }

}

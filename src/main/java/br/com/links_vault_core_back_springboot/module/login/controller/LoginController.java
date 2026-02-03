package br.com.links_vault_core_back_springboot.module.login.controller;

import br.com.links_vault_core_back_springboot.dto.ApiResponseDTO;
import br.com.links_vault_core_back_springboot.module.login.dto.LoginRequestDTO;
import br.com.links_vault_core_back_springboot.module.login.useCase.LoginUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private final LoginUseCase loginUseCase;

    @PostMapping
    public ResponseEntity<ApiResponseDTO> authenticate(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ) throws AuthenticationException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new ApiResponseDTO(
                                "Login realizado com sucesso!",
                                this.loginUseCase.execute(loginRequestDTO)
                        )
                );
    }

}

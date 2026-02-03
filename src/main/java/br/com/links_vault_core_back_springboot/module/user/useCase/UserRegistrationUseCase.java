package br.com.links_vault_core_back_springboot.module.user.useCase;

import br.com.links_vault_core_back_springboot.exception.DataAlreadyInUseException;
import br.com.links_vault_core_back_springboot.module.user.dto.UserRegistrationRequestDTO;
import br.com.links_vault_core_back_springboot.module.user.dto.UserRegistrationResponseDTO;
import br.com.links_vault_core_back_springboot.module.user.entity.UserEntity;
import br.com.links_vault_core_back_springboot.module.user.repository.UserRepository;
import br.com.links_vault_core_back_springboot.provider.JWTProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserRegistrationUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    public UserRegistrationResponseDTO execute(
            UserRegistrationRequestDTO userCreateRequestDTO
    ) {

        if (
                userRepository.findByUsernameOrEmail(
                        userCreateRequestDTO.getUsername(),
                        userCreateRequestDTO.getEmail()
                ).isPresent()
        ) {
            throw new DataAlreadyInUseException("Este e-mail e/ou nome de usuário já está(ão) em uso.");
        }

        var encodedPassword = passwordEncoder.encode(userCreateRequestDTO.getPassword());

        UserEntity userEntity = UserEntity
                .builder()
                .username(userCreateRequestDTO.getUsername())
                .email(userCreateRequestDTO.getEmail())
                .password(encodedPassword)
                .createdAt(LocalDateTime.now())
                .build();

        var user = userRepository.save(userEntity);

        return UserRegistrationResponseDTO
                .builder()
                .accessToken(jwtProvider.createToken(user.getId()))
                .build();
    }

}

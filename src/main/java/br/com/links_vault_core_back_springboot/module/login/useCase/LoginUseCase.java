package br.com.links_vault_core_back_springboot.module.login.useCase;

import br.com.links_vault_core_back_springboot.module.login.dto.LoginRequestDTO;
import br.com.links_vault_core_back_springboot.module.login.dto.LoginResponseDTO;
import br.com.links_vault_core_back_springboot.module.user.repository.UserRepository;
import br.com.links_vault_core_back_springboot.provider.JWTProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@AllArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO execute(
            LoginRequestDTO loginRequestDTO
    ) throws AuthenticationException {

        if (loginRequestDTO.getUsername() != null && loginRequestDTO.getEmail() != null) {
            throw new IllegalArgumentException();
        }

        var user = userRepository
                .findByUsernameOrEmail(loginRequestDTO.getUsername(), loginRequestDTO.getEmail())
                .orElseThrow(AuthenticationException::new);

        var passwordMatches = this.passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        return LoginResponseDTO
                .builder()
                .accessToken(jwtProvider.createToken(user.getId()))
                .build();
    }

}

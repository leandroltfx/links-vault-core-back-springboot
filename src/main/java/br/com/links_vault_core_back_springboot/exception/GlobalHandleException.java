package br.com.links_vault_core_back_springboot.exception;

import br.com.links_vault_core_back_springboot.dto.ApiResponseDTO;
import br.com.links_vault_core_back_springboot.dto.FieldErrorDTO;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@ControllerAdvice
public class GlobalHandleException {

    private final MessageSource messageSource;

    public GlobalHandleException(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleException() {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(
                "Ocorreu um erro interno, tente novamente."
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        List<FieldErrorDTO> fieldErrorDTOS = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            FieldErrorDTO error = new FieldErrorDTO(message, err.getField());
            fieldErrorDTOS.add(error);
        });

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(
                "Campos inválidos.",
                fieldErrorDTOS
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiResponseDTO);
    }

    @ExceptionHandler(DataAlreadyInUseException.class)
    public ResponseEntity<ApiResponseDTO> handleDataAlreadyInUseException(
            DataAlreadyInUseException dataAlreadyInUseException
    ) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(
                dataAlreadyInUseException.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(apiResponseDTO);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponseDTO> handleAuthenticationException() {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(
                "Credenciais inválidas."
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(apiResponseDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO> handleIllegalArgumentException() {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(
                "Informe apenas o e-mail ou o nome de usuário."
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiResponseDTO);
    }

}

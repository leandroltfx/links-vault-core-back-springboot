package br.com.links_vault_core_back_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDTO {

    private String message;
    private Object data;

    public ApiResponseDTO(String message) {
        this.message = message;
    }

}

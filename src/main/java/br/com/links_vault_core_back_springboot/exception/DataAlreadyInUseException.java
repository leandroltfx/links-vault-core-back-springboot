package br.com.links_vault_core_back_springboot.exception;

public class DataAlreadyInUseException extends RuntimeException {
    public DataAlreadyInUseException(String message) {
        super(message);
    }
}

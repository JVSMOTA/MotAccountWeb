package com.sapatariasmota.MotAccountWeb.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationDto(
        @NotBlank(message = "O atributo email é obrigatório!")
        String email,
        @NotBlank(message = "O atributo senha é obrigatório!")
        String senha)
    {
}

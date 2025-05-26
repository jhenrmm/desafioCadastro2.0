package com.example.desafioCadastro2.dtos;

import com.example.desafioCadastro2.models.Sexo;
import com.example.desafioCadastro2.models.Tipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetsRecordsDto(@NotBlank String name, @NotNull Tipo tipo, @NotNull Sexo sexo, @NotBlank String endereco, @NotNull float idade,@NotNull float peso,@NotBlank String raca) {
}

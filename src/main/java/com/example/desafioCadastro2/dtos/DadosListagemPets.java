package com.example.desafioCadastro2.dtos;

import com.example.desafioCadastro2.models.Pet;
import com.example.desafioCadastro2.models.Sexo;
import com.example.desafioCadastro2.models.Tipo;

public record DadosListagemPets(Long id, String name, Tipo tipo, Sexo sexo) {
    public DadosListagemPets(Pet pet) {
        this(pet.getId(), pet.getName(), pet.getTipo(), pet.getSexo());
    }
}

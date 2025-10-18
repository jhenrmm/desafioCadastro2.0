package com.example.desafioCadastro2.dtos;

import com.example.desafioCadastro2.models.Pet;

public record DadosAtualizacaoPets(Long id, String name, String endereco, Float idade, Float peso, String raca) {
    public DadosAtualizacaoPets(Pet pet) {
        this(pet.getId(), pet.getName(), pet.getEndereco(), pet.getIdade(), pet.getPeso(), pet.getRaca());
    }
}

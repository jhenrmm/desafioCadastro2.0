package com.example.desafioCadastro2.models;


import com.example.desafioCadastro2.dtos.DadosAtualizacaoPets;
import com.example.desafioCadastro2.dtos.DadosListagemPets;
import com.example.desafioCadastro2.dtos.PetDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "PETS_CADASTRADOS")
public class Pet implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Tipo tipo;

    private Sexo sexo;

    private String endereco;

    private Float idade;

    private Float peso;

    private String raca;

    public Pet(){}

    public Pet(DadosAtualizacaoPets attPetDto){
        this.name = attPetDto.name();
        this.endereco = attPetDto.endereco();
        this.idade = attPetDto.idade();
        this.peso = attPetDto.peso();
        this.raca = attPetDto.raca();
    }

    public Pet(PetDto dto){
        this.name = dto.name();
        this.tipo = dto.tipo();
        this.sexo = dto.sexo();
        this.endereco = dto.endereco();
        this.idade = dto.idade();
        this.peso = dto.peso();
        this.raca = dto.raca();
    }

    public Pet(String name, Tipo tipo, Sexo sexo, String endereco, Float idade, Float peso, String raca) {
        this.name = name;
        this.tipo = tipo;
        this.sexo = sexo;
        this.endereco = endereco;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }

    public Pet(DadosListagemPets dto) {
        this.name = dto.name();
        this.tipo = dto.tipo();
        this.sexo = dto.sexo();
    }

    @Override
    public String toString() {
        return "PetsModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tipo=" + tipo +
                ", sexo=" + sexo +
                ", endereco='" + endereco + '\'' +
                ", idade=" + idade +
                ", peso=" + peso +
                ", raca='" + raca + '\'' +
                '}';
    }
}

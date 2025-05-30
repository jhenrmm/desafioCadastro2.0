package com.example.desafioCadastro2.models;


import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "PETS_CADASTRADOS")
public class PetsModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Getter
    private String name;
    @Getter
    private Tipo tipo;
    @Getter
    private Sexo sexo;
    @Getter
    private String endereco;
    private float idade;
    private float peso;
    @Getter
    private String raca;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Float getIdade() {
        return idade;
    }

    public void setIdade(float idade) {
        this.idade = idade;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setRaca(String raca) {
        this.raca = raca;
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

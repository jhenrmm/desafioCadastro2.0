package com.example.desafioCadastro2.service;

import com.example.desafioCadastro2.controllers.PetsControllers;
import com.example.desafioCadastro2.dtos.PetsRecordsDto;
import com.example.desafioCadastro2.models.Constantes;
import com.example.desafioCadastro2.models.Sexo;
import com.example.desafioCadastro2.models.Tipo;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class PetsService {
    private final PetsControllers petsControllers;
    private static final Scanner SCANNER = new Scanner(System.in);

    public PetsService(PetsControllers petsControllers) {
        this.petsControllers = petsControllers;
    }

    public void cadastroPetMenu() {
        System.out.println("1. Qual o nome e Sobrenome do pet?");
        String name = SCANNER.nextLine();
        if (name.isEmpty()) {
            name = Constantes.NAO_INFORMADO;
        }
        else if (!name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)+$")) {
            throw new IllegalArgumentException("Nome Inválido! Deve conter Sobrenome e apenas letras");
        }

        Tipo tipo = null;
        while (tipo == null) {
            System.out.println("2. Qual o tipo do pet (CACHORRO/GATO)?");
            try {
                tipo = Tipo.valueOf(SCANNER.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo inválido. Tente novamente.");
            }
        }

        Sexo sexo = null;
        while (sexo == null) {
            System.out.println("3. Qual o sexo do animal (MACHO/FEMEA)?");
            try {
                sexo = Sexo.valueOf(SCANNER.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Sexo inválido. Tente novamente.");
            }
        }

        System.out.println("4. Qual endereço e bairro que ele foi encontrado?");

        System.out.println("Qual o número da casa?");
        String input = SCANNER.nextLine().trim();

        int numCasa;
        if (input.isEmpty()) {
            numCasa = Constantes.INT_NAO_INFORMADO; // Ex: 0 ou -1
        } else {
            numCasa = Integer.parseInt(input);
        }

        System.out.println("Qual a cidade?");
        String cidade = SCANNER.nextLine();

        System.out.println("Qual a rua?");
        String rua = SCANNER.nextLine();

        String endereco = "Rua " + rua + ", " + numCasa + ", " + cidade;

        System.out.println("5. Qual a idade aproximada do pet?");
        String inputIdade = SCANNER.nextLine().trim();

        float idade;
        if (inputIdade.isEmpty()) {
            idade = Constantes.FLOAT_NAO_INFORMADO; // exemplo: 0.0f
        } else {
            idade = Float.parseFloat(inputIdade.replace(",", "."));

            if (idade > 60 || idade < 0) {
                throw new IllegalArgumentException("Idade Inválida!");
            }
            if (idade < 12) {
                idade = idade / 12f;
            }
        }
        System.out.println("6. Qual o peso aproximado do pet?");
        String inputPeso = SCANNER.nextLine().trim();
        float peso;
        if (inputPeso.isEmpty()) {
            peso = Constantes.FLOAT_NAO_INFORMADO; // ex: 0.0f
        }
        else {
            peso = Float.parseFloat(inputPeso.replace(",", "."));
            if (peso > 60 || peso < 0.5) {
                throw new IllegalArgumentException("Peso Inválido!");
            }
        }

        System.out.println("7. Qual a raça do pet?");
        String raca = SCANNER.nextLine();
        if (raca.isEmpty()) {
            raca = Constantes.NAO_INFORMADO;
        }
        if (!raca.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)+$")) {
            throw new IllegalArgumentException("Raça Inválida! Deve conter apenas letras");
        }

        PetsRecordsDto dto = new PetsRecordsDto(name, tipo, sexo, endereco, idade, peso, raca);
        this.petsControllers.savePets(dto);
        System.out.println("Pet cadastrado com sucesso!");
    }
}

package com.example.desafioCadastro2.service;

import com.example.desafioCadastro2.dtos.PetsRecordsDto;
import com.example.desafioCadastro2.models.Sexo;
import com.example.desafioCadastro2.models.Tipo;

import java.util.Scanner;

public class PetsService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void cadastroPetMenu() {
        System.out.println("1. Qual o nome e Sobrenome do pet?");
        String name = SCANNER.nextLine();
        if (!name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)+$")){
            throw new IllegalArgumentException("Nome Inválido! Deve conter apenas letras");
        }
        if (!hasLastName(name)) {
            throw new IllegalArgumentException("O pet deve ter um sobrenome!");
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
        int numCasa = Integer.parseInt(SCANNER.nextLine());

        System.out.println("Qual a cidade?");
        String cidade = SCANNER.nextLine();

        System.out.println("Qual a rua?");
        String rua = SCANNER.nextLine();

        String endereco = "Rua " + rua + ", " + numCasa + ", " + cidade;

        System.out.println("5. Qual a idade aproximada do pet?");
        float idade = Float.parseFloat(SCANNER.nextLine().replace(",","."));

        System.out.println("6. Qual o peso aproximado do pet?");
        float peso = Float.parseFloat(SCANNER.nextLine().replace(",","."));

        System.out.println("7. Qual a raça do pet?");
        String raca = SCANNER.nextLine();

        PetsRecordsDto dto = new PetsRecordsDto(name, tipo, sexo, endereco, idade, peso, raca);
        System.out.println("Pet cadastrado com sucesso!");
    }

    private static boolean hasLastName(String name) {
        String[] nameAndLastName = name.trim().split(" ");
        if (nameAndLastName.length >= 2) {
            return true;
        }
        return false;
    }
}

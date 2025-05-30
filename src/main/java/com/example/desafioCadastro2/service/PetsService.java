package com.example.desafioCadastro2.service;

import com.example.desafioCadastro2.controllers.PetsControllers;
import com.example.desafioCadastro2.dtos.FiltrarPetsDto;
import com.example.desafioCadastro2.dtos.PetsRecordsDto;
import com.example.desafioCadastro2.models.Constantes;
import com.example.desafioCadastro2.models.PetsModel;
import com.example.desafioCadastro2.models.Sexo;
import com.example.desafioCadastro2.models.Tipo;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
@Log4j2
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

    public void alterarPetMenu() {
        System.out.println("Qual o tipo do animal?");
        System.out.println("1. Cachorro");
        System.out.println("2. Gato");
        int tipoAnimal = Integer.parseInt(SCANNER.nextLine());
        if (tipoAnimal > 2 || tipoAnimal < 1){
            throw new IllegalArgumentException("Tipo Inválido!");
        }
        Tipo tipo = (tipoAnimal == 1) ? Tipo.CACHORRO : Tipo.GATO;

        FiltrarPetsDto filtro = new FiltrarPetsDto();

        mostrarOpcoesDeCriterios();
        int criterio1 = Integer.parseInt(SCANNER.nextLine());
        preencherCriterio(criterio1, filtro);

        System.out.println("Deseja utilizar mais um critério? (S/N)");
        String criterioOpcional = SCANNER.nextLine().trim().toUpperCase();

        if (criterioOpcional.equals("S")) {
            mostrarOpcoesDeCriterios();
            int criterio2 = Integer.parseInt(SCANNER.nextLine());
            preencherCriterio(criterio2, filtro);
        }
        ResponseEntity<List<PetsModel>> listResponseEntity = this.petsControllers.buscarPetsCriterio(tipo, filtro.nome, filtro.sexo, filtro.idade, filtro.peso, filtro.raca, filtro.endereco);
        List<PetsModel> listaDePets = listResponseEntity.getBody();

        if (listaDePets == null || listaDePets.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os critérios informados.");
            return;
        }

        for (int i = 0; i < listaDePets.size(); i++) {
            PetsModel pet = listaDePets.get(i);
            System.out.printf("%d. Nome: %s | Tipo: %s | Sexo: %s | Idade: %.1f | Peso: %.1f | Raça: %s | Endereço: %s%n",
                    i + 1,
                    pet.getName(),
                    pet.getTipo(),
                    pet.getSexo(),
                    pet.getIdade(),
                    pet.getPeso(),
                    pet.getRaca(),
                    pet.getEndereco()
            );
        }

        System.out.println("Digite o número do pet que deseja alterar:");
        int escolha = Integer.parseInt(SCANNER.nextLine());

        if (escolha < 1 || escolha > listaDePets.size()) {
            System.out.println("Escolha inválida.");
            return;
        }

        PetsModel petEscolhido = listaDePets.get(escolha - 1);

        PetsRecordsDto petsRecordsDto =  obterNovosDadosDoPet(petEscolhido);
        this.petsControllers.updatePetById(petEscolhido.getId(), petsRecordsDto);
        System.out.println("Pet alterado com sucesso!");

    }

    public void deletarPet(){
        System.out.println("Qual o tipo do animal?");
        System.out.println("1. Cachorro");
        System.out.println("2. Gato");
        int tipoAnimal = Integer.parseInt(SCANNER.nextLine());
        if (tipoAnimal > 2 || tipoAnimal < 1){
            throw new IllegalArgumentException("Tipo Inválido!");
        }
        Tipo tipo = (tipoAnimal == 1) ? Tipo.CACHORRO : Tipo.GATO;

        FiltrarPetsDto filtro = new FiltrarPetsDto();

        mostrarOpcoesDeCriterios();
        int criterio1 = Integer.parseInt(SCANNER.nextLine());
        preencherCriterio(criterio1, filtro);

        System.out.println("Deseja utilizar mais um critério? (S/N)");
        String criterioOpcional = SCANNER.nextLine().trim().toUpperCase();

        if (criterioOpcional.equals("S")) {
            mostrarOpcoesDeCriterios();
            int criterio2 = Integer.parseInt(SCANNER.nextLine());
            preencherCriterio(criterio2, filtro);
        }
        ResponseEntity<List<PetsModel>> listResponseEntity = this.petsControllers.buscarPetsCriterio(tipo, filtro.nome, filtro.sexo, filtro.idade, filtro.peso, filtro.raca, filtro.endereco);
        List<PetsModel> listaDePets = listResponseEntity.getBody();

        if (listaDePets == null || listaDePets.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os critérios informados.");
            return;
        }

        for (int i = 0; i < listaDePets.size(); i++) {
            PetsModel pet = listaDePets.get(i);
            System.out.printf("%d. Nome: %s | Tipo: %s | Sexo: %s | Idade: %.1f | Peso: %.1f | Raça: %s | Endereço: %s%n",
                    i + 1,
                    pet.getName(),
                    pet.getTipo(),
                    pet.getSexo(),
                    pet.getIdade(),
                    pet.getPeso(),
                    pet.getRaca(),
                    pet.getEndereco()
            );
        }

        System.out.println("Digite o número do pet que deseja deletar:");
        int escolha = Integer.parseInt(SCANNER.nextLine());

        if (escolha < 1 || escolha > listaDePets.size()) {
            System.out.println("Escolha inválida.");
            return;
        }

        PetsModel petEscolhido = listaDePets.get(escolha - 1);
        petsControllers.deletePet(petEscolhido.getId());
        System.out.println("Pet deletado com sucesso!");
    }

    public void mostrarOpcoesDeCriterios(){
        System.out.println("Deseja procurar por qual critério?");
        System.out.println("1. Nome ou Sobrenome");
        System.out.println("2. Sexo");
        System.out.println("3. Idade");
        System.out.println("4. Peso");
        System.out.println("5. Raça");
        System.out.println("6. Endereço");
    }
    public void preencherCriterio(int criterio, FiltrarPetsDto filtro){
        switch (criterio){
            case 1 -> {
                System.out.println("Digite o nome ou sobrenome: ");
                filtro.nome = SCANNER.nextLine();
            }
            case 2 -> {
                System.out.println("Digite o Sexo: ");
                filtro.sexo = SCANNER.nextLine().trim().toUpperCase();
            }
            case 3 -> {
                System.out.println("Digite a Idade: ");
                filtro.idade  = Float.parseFloat(SCANNER.nextLine());
            }
            case 4 -> {
                System.out.println("Digite o Peso: ");
                filtro.peso = Float.parseFloat(SCANNER.nextLine());
            }
            case 5 -> {
                System.out.println("Digite a Raça: ");
                filtro.raca = SCANNER.nextLine();
            }
            case 6 -> {
                System.out.println("Digite o Endereço: ");
                filtro.endereco = SCANNER.nextLine();
            }
            default -> throw new IllegalArgumentException("Critério Inválido!");

        }
    }

    public void listaPetsCadastrados(){
        ResponseEntity<List<PetsModel>> allPets = petsControllers.getAllPets();
        List<PetsModel> pets = allPets.getBody();
        for (PetsModel pet : pets){
            System.out.printf("Nome: %s | Tipo: %s | Sexo: %s | Idade: %.1f | Peso: %.1f | Raça: %s | Endereço: %s%n",
                    pet.getName(),
                    pet.getTipo(),
                    pet.getSexo(),
                    pet.getIdade(),
                    pet.getPeso(),
                    pet.getRaca(),
                    pet.getEndereco()
            );
        }
    }
    public void listarPetsPorCriterio(){
        System.out.println("Qual o tipo do animal?");
        System.out.println("1. Cachorro");
        System.out.println("2. Gato");
        int tipoAnimal = Integer.parseInt(SCANNER.nextLine());
        if (tipoAnimal > 2 || tipoAnimal < 1){
            throw new IllegalArgumentException("Tipo Inválido!");
        }
        Tipo tipo = (tipoAnimal == 1) ? Tipo.CACHORRO : Tipo.GATO;

        FiltrarPetsDto filtro = new FiltrarPetsDto();

        mostrarOpcoesDeCriterios();
        int criterio1 = Integer.parseInt(SCANNER.nextLine());
        preencherCriterio(criterio1, filtro);

        System.out.println("Deseja utilizar mais um critério? (S/N)");
        String criterioOpcional = SCANNER.nextLine().trim().toUpperCase();

        if (criterioOpcional.equals("S")) {
            mostrarOpcoesDeCriterios();
            int criterio2 = Integer.parseInt(SCANNER.nextLine());
            preencherCriterio(criterio2, filtro);
        }
        ResponseEntity<List<PetsModel>> listResponseEntity = this.petsControllers.buscarPetsCriterio(tipo, filtro.nome, filtro.sexo, filtro.idade, filtro.peso, filtro.raca, filtro.endereco);
        List<PetsModel> listaDePets = listResponseEntity.getBody();

        if (listaDePets == null || listaDePets.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os critérios informados.");
            return;
        }

        for (PetsModel pet : listaDePets) {
            System.out.printf("Nome: %s | Tipo: %s | Sexo: %s | Idade: %.1f | Peso: %.1f | Raça: %s | Endereço: %s%n",
                    pet.getName(),
                    pet.getTipo(),
                    pet.getSexo(),
                    pet.getIdade(),
                    pet.getPeso(),
                    pet.getRaca(),
                    pet.getEndereco()
            );
        }
    }
    private PetsRecordsDto obterNovosDadosDoPet(PetsModel pet) {
        System.out.printf("Nome atual: %s. Digite o novo nome (ou pressione Enter para manter):%n", pet.getName());
        String name = SCANNER.nextLine().trim();
        if (name.isEmpty()) {
            name = pet.getName();
        }

        System.out.printf("Endereço atual: %s. Digite o novo endereço (ou pressione Enter para manter):%n", pet.getEndereco());
        String endereco = SCANNER.nextLine().trim();
        if (endereco.isEmpty()) {
            endereco = pet.getEndereco();
        }

        System.out.printf("Idade atual: %.1f. Digite a nova idade (ou pressione Enter para manter):%n", pet.getIdade());
        String idadeInput = SCANNER.nextLine().trim();
        float idade = idadeInput.isEmpty() ? pet.getIdade() : Float.parseFloat(idadeInput.replace(",", "."));

        System.out.printf("Peso atual: %.1f. Digite o novo peso (ou pressione Enter para manter):%n", pet.getPeso());
        String pesoInput = SCANNER.nextLine().trim();
        float peso = pesoInput.isEmpty() ? pet.getPeso() : Float.parseFloat(pesoInput.replace(",", "."));

        System.out.printf("Raça atual: %s. Digite a nova raça (ou pressione Enter para manter):%n", pet.getRaca());
        String raca = SCANNER.nextLine().trim();
        if (raca.isEmpty()) {
            raca = pet.getRaca();
        }

        return new PetsRecordsDto(name, pet.getTipo(), pet.getSexo(), endereco, idade, peso, raca);
    }


}

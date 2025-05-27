package com.example.desafioCadastro2;


import com.example.desafioCadastro2.service.PetsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DesafioCadastro2Application implements CommandLineRunner {
	private static final Scanner SCANNER = new Scanner(System.in);
	PetsService petsService;

	public DesafioCadastro2Application(PetsService  petsService){
		this.petsService= petsService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DesafioCadastro2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		int op = 0;
		Boolean isTrue = true ;

		while(isTrue) {
			menu();
			op = Integer.parseInt(SCANNER.nextLine());
			if (op <= 0 || op > 6) {
				System.out.println("Opção Inválida!");
				continue;
			}
			if (op == 6) break;
			switch (op){
				case 1 -> petsService.cadastroPetMenu();
				case 2 -> petsService.alterarPetMenu();
				default -> isTrue = false;
			}
		}
		System.exit(0);

	}
	private static void menu(){
		System.out.println("1. Cadastrar um novo pet");
		System.out.println("2. Alterar os dados do pet cadastrado");
		System.out.println("3. Deletar um pet cadastrado");
		System.out.println("4. Listar todos os pets cadastrados");
		System.out.println("5. Listar pets por algum critério (idade, nome, raça)");
		System.out.println("6. Sair");
	}
}

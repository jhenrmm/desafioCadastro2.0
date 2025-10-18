package com.example.desafioCadastro2.controllers;

import com.example.desafioCadastro2.dtos.DadosAtualizacaoPets;
import com.example.desafioCadastro2.dtos.DadosListagemPets;
import com.example.desafioCadastro2.dtos.PetDto;
import com.example.desafioCadastro2.models.Pet;
import com.example.desafioCadastro2.models.Tipo;
import com.example.desafioCadastro2.service.PetsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PetsControllers {

    private PetsService petsService;

    @PostMapping("/pets")
    public ResponseEntity<Pet> savePets(@RequestBody @Valid PetDto petDto){
        petsService.save(petDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/pets")
    public ResponseEntity<List<DadosListagemPets>> listAllPets(){
        List<DadosListagemPets> petsList = petsService.listarTodosPets();
        return ResponseEntity.status(HttpStatus.OK).body(petsList);
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<Pet> listPetById(@PathVariable(value = "id") Long id){
        Pet pet = petsService.listarPetPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(pet);
    }

    @GetMapping("/petsCriterio")
    public ResponseEntity<List<DadosListagemPets>> listPetByCriterio(
            @RequestParam Tipo tipo,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) Float idade,
            @RequestParam(required = false) Float peso,
            @RequestParam(required = false) String raca,
            @RequestParam(required = false) String endereco
    ) {
        List<DadosListagemPets> pets = petsService.buscarPets(tipo, nome, sexo, idade, peso, raca, endereco);
        return ResponseEntity.status(HttpStatus.OK).body(pets);
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<Object> updatePetById(@PathVariable(value = "id") Long id, DadosAtualizacaoPets dto){
        Pet pet = petsService.atualizarPetPorId(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(pet);
    }

    @DeleteMapping("/pets/{id}")
    public ResponseEntity<Object> deletePet(@PathVariable(value = "id") Long id){
        petsService.deletarPetPorId(id);
        return ResponseEntity.noContent().build();
    }

}

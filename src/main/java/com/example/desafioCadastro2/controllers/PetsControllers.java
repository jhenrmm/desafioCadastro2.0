package com.example.desafioCadastro2.controllers;

import com.example.desafioCadastro2.dtos.PetsRecordsDto;
import com.example.desafioCadastro2.models.PetsModel;
import com.example.desafioCadastro2.models.Tipo;
import com.example.desafioCadastro2.repositories.PetsRepository;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
public class PetsControllers {

    @Autowired
    PetsRepository petsRepository;

    @PostMapping("/pets")
    public ResponseEntity<PetsModel> savePets(@RequestBody @Valid PetsRecordsDto petsRecordsDto){
        var petsModel = new PetsModel();
        BeanUtils.copyProperties(petsRecordsDto, petsModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(petsRepository.save(petsModel));
    }
    @GetMapping("/pets")
    public ResponseEntity<List<PetsModel>> getAllPets(){
        List<PetsModel> petsList = petsRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(petsList);
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<Object> getOnePet(@PathVariable(value = "id") UUID id){
        Optional<PetsModel> petsO = petsRepository.findById(id);
        if (petsO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(petsO.get());
    }
    @GetMapping("/petsCriterio")
    public ResponseEntity<List<PetsModel>> buscarPetsCriterio(
            @RequestParam Tipo tipo,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) Float idade,
            @RequestParam(required = false) Float peso,
            @RequestParam(required = false) String raca,
            @RequestParam(required = false) String endereco
    ) {
        List<PetsModel> pets = buscarPets(tipo, nome, sexo, idade, peso, raca, endereco);
        return ResponseEntity.status(HttpStatus.OK).body(pets);
    }


    @PutMapping("/pets/{id}")
    public ResponseEntity<Object> updatePetById(@PathVariable(value = "id") UUID id, PetsRecordsDto petsRecordsDto){
        Optional<PetsModel> petsO = petsRepository.findById(id);
        if (petsO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
        }
        var petsModel = petsO.get();
        BeanUtils.copyProperties(petsRecordsDto, petsModel);
        return ResponseEntity.status(HttpStatus.OK).body(petsRepository.save(petsModel));
    }

    @DeleteMapping("/pets/{id}")
    public ResponseEntity<Object> deletePet(@PathVariable(value = "id") UUID id){
        Optional<PetsModel> petsO = petsRepository.findById(id);
        if (petsO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not Found.");
        }
        petsRepository.delete(petsO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pet deleted successfully");
    }
    public List<PetsModel> buscarPets(Tipo tipo, String nome, String sexo, Float idade, Float peso, String raca, String endereco) {
        return petsRepository.findAll().stream()
                .filter(p -> p.getTipo().equals(tipo))
                .filter(p -> nome == null || p.getName().toLowerCase().contains(nome.toLowerCase()))
                .filter(p -> sexo == null || p.getSexo().name().equalsIgnoreCase(sexo))
                .filter(p -> idade == null || p.getIdade().equals(idade))
                .filter(p -> peso == null || p.getPeso().equals(peso))
                .filter(p -> raca == null || p.getRaca().toLowerCase().contains(raca.toLowerCase()))
                .filter(p -> endereco == null || p.getEndereco().toLowerCase().contains(endereco.toLowerCase()))
                .collect(Collectors.toList());
    }

}

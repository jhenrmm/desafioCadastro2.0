package com.example.desafioCadastro2.controllers;

import com.example.desafioCadastro2.dtos.PetsRecordsDto;
import com.example.desafioCadastro2.models.PetsModel;
import com.example.desafioCadastro2.repositories.PetsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
}

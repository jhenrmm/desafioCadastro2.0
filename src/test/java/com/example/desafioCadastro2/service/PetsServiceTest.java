package com.example.desafioCadastro2.service;

import com.example.desafioCadastro2.controllers.PetsControllers;
import com.example.desafioCadastro2.dtos.PetsRecordsDto;
import com.example.desafioCadastro2.models.PetsModel;
import com.example.desafioCadastro2.models.Tipo;
import com.example.desafioCadastro2.models.Sexo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetsServiceTest {

    @Mock
    private PetsControllers petsControllers;

    @InjectMocks
    private PetsService petsService;

    private final InputStream systemIn = System.in;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListaPetsCadastrados() {
        PetsModel pet = new PetsModel();
        pet.setName("Buddy Silva");
        pet.setTipo(Tipo.CACHORRO);
        pet.setSexo(Sexo.MACHO);
        pet.setIdade(3f);
        pet.setPeso(8.5f);
        pet.setRaca("Labrador Retriever");
        pet.setEndereco("Rua das Flores, 123, São Paulo");

        List<PetsModel> petsList = List.of(pet);
        ResponseEntity<List<PetsModel>> response = ResponseEntity.ok(petsList);

        when(petsControllers.getAllPets()).thenReturn(response); // <-- mock correto

        assertDoesNotThrow(() -> petsService.listaPetsCadastrados());

        verify(petsControllers, times(1)).getAllPets();
    }

    @Test
    void testListaPetsCadastrados_Vazio() {
        when(petsControllers.getAllPets()).thenReturn(ResponseEntity.ok(List.of()));

        assertDoesNotThrow(() -> petsService.listaPetsCadastrados());

        verify(petsControllers, times(1)).getAllPets();
    }

}

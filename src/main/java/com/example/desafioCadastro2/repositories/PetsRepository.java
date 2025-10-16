package com.example.desafioCadastro2.repositories;

import com.example.desafioCadastro2.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PetsRepository extends JpaRepository<Pet, Long> {
}

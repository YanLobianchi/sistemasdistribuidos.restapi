package com.sistemasdistribuidos.restapi.repository;

import com.sistemasdistribuidos.restapi.domain.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}

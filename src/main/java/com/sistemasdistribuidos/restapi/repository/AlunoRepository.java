package com.sistemasdistribuidos.restapi.repository;

import com.sistemasdistribuidos.restapi.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}

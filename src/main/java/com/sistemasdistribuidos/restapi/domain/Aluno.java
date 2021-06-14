package com.sistemasdistribuidos.restapi.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Aluno {

	public Aluno(final String nome) {
		this.nome = nome;
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String nome;

	@OneToMany(mappedBy = "matricula")
	private final Set<Matricula> matriculas = new HashSet<>();

}


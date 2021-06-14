package com.sistemasdistribuidos.restapi.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

	@JsonManagedReference
	@OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private final Set<Matricula> matriculas = new HashSet<>();

}


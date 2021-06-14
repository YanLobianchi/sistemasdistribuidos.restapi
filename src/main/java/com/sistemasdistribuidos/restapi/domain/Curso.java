package com.sistemasdistribuidos.restapi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Curso {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(unique = true, nullable = false)
	private String codigo;

}

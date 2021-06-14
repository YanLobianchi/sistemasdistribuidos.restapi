package com.sistemasdistribuidos.restapi.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class Matricula {

	@EmbeddedId
	private MatriculaId id = new MatriculaId();

	@ManyToOne
	@MapsId
	private Aluno aluno;

	@ManyToOne
	@MapsId
	private Curso curso;

	private Float frequencia;

	private Float media;

	@Embeddable
	@NoArgsConstructor
	@EqualsAndHashCode
	@Getter
	@Setter
	static class MatriculaId implements Serializable {
		@Serial
		private static final long serialVersionUID = 1L;

		private Long alunoId;
		private Long cursoId;
	}

}

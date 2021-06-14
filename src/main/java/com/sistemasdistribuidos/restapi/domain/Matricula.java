package com.sistemasdistribuidos.restapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@JsonIgnore
	private MatriculaId id = new MatriculaId();

	@JsonBackReference
	@ManyToOne
	@MapsId("alunoId")
	@JoinColumn(name = "alunoId", referencedColumnName = "id", updatable = false, insertable = false)
	private Aluno aluno;

	@ManyToOne
	@MapsId("cursoId")
	@JoinColumn(name = "cursoId", referencedColumnName = "id", updatable = false, insertable = false)
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

package com.sistemasdistribuidos.restapi.domain.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class CursoForm {

	private String nome;
	private String codigo;

}

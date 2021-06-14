package com.sistemasdistribuidos.restapi.controller;

import com.sistemasdistribuidos.restapi.domain.Curso;
import com.sistemasdistribuidos.restapi.domain.forms.CursoForm;
import com.sistemasdistribuidos.restapi.exception.NotFoundException;
import com.sistemasdistribuidos.restapi.repository.CursoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos")
public class CursoController {

	private final CursoRepository cursoRepository;

	CursoController(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;
	}

	@Operation(summary = "Lista todos os Cursos")
	@GetMapping
	List<Curso> all() {
		return cursoRepository.findAll();
	}

	@Operation(summary = "Cria um novo Curso")
	@PostMapping
	Curso create(@RequestBody CursoForm curso) {
		return cursoRepository.save(new Curso(curso.getNome(), curso.getCodigo()));
	}

	@Operation(summary = "Retorna Curso correspondente ao ID informado")
	@GetMapping("/{id}")
	Curso read(@PathVariable Long id) {
		return cursoRepository.findById(id)
							  .orElseThrow(() -> new NotFoundException(Curso.class, id));
	}

	@Operation(summary = "Atualiza Curso correspondente ao ID informado")
	@PutMapping("/{id}")
	Curso update(@PathVariable Long id, @RequestBody CursoForm newCurso) {
		return cursoRepository.findById(id)
							  .map(curso -> {
								  curso.setNome(newCurso.getNome());
								  curso.setCodigo(newCurso.getCodigo());
								  return cursoRepository.save(curso);
							  })
							  .orElseThrow(() -> new NotFoundException(Curso.class, id));
	}

	@Operation(summary = "Remove um Curso correspondente ao ID informado")
	@DeleteMapping("/{id}")
	void delete(@PathVariable Long id) {
		cursoRepository.deleteById(id);
	}

}

package com.sistemasdistribuidos.restapi.controller;

import com.sistemasdistribuidos.restapi.domain.Curso;
import com.sistemasdistribuidos.restapi.exception.NotFoundException;
import com.sistemasdistribuidos.restapi.repository.CursoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CursoController {

	private final CursoRepository cursoRepository;

	CursoController(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;
	}

	@GetMapping("/cursos")
	List<Curso> all() {
		return cursoRepository.findAll();
	}

	@PostMapping("/cursos")
	Curso create(@RequestBody Curso curso) {
		return cursoRepository.save(curso);
	}

	@GetMapping("/cursos/{id}")
	Curso read(@PathVariable Long id) {
		return cursoRepository.findById(id)
							  .orElseThrow(() -> new NotFoundException(Curso.class, id));
	}

	@PutMapping("/cursos/{id}")
	Curso update(@PathVariable Long id, @RequestBody Curso newCurso) {
		return cursoRepository.findById(id)
							  .map(curso -> {
								  curso.setNome(newCurso.getNome());
								  curso.setCodigo(newCurso.getCodigo());
								  return cursoRepository.save(curso);
							  })
							  .orElseThrow(() -> new NotFoundException(Curso.class, id));
	}

	@DeleteMapping("/cursos/{id}")
	void delete(@PathVariable Long id) {
		cursoRepository.deleteById(id);
	}

}

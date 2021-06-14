package com.sistemasdistribuidos.restapi.controller;

import com.sistemasdistribuidos.restapi.domain.*;
import com.sistemasdistribuidos.restapi.exception.NotFoundException;
import com.sistemasdistribuidos.restapi.repository.AlunoRepository;
import com.sistemasdistribuidos.restapi.repository.CursoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlunoController {

	private final AlunoRepository alunoRepository;
	private final CursoRepository cursoRepository;

	AlunoController(AlunoRepository alunoRepository, CursoRepository cursoRepository) {
		this.alunoRepository = alunoRepository;
		this.cursoRepository = cursoRepository;
	}

	@GetMapping("/alunos")
	List<Aluno> all() {
		return alunoRepository.findAll();
	}

	@PostMapping("/alunos")
	Aluno create(@RequestBody AlunoForm aluno) {
		return alunoRepository.save(new Aluno(aluno.getNome()));
	}

	@GetMapping("/alunos/{id}")
	Aluno read(@PathVariable Long id) {
		return alunoRepository.findById(id)
							  .orElseThrow(() -> new NotFoundException(Aluno.class, id));
	}

	@PutMapping("/alunos/{id}")
	Aluno update(@PathVariable Long id, @RequestBody AlunoForm newAluno) {
		return alunoRepository.findById(id)
							  .map(aluno -> {
								  aluno.setNome(newAluno.getNome());
								  return alunoRepository.save(aluno);
							  })
							  .orElseThrow(() -> new NotFoundException(Aluno.class, id));
	}

	@PutMapping("/alunos/{alunoId}/matriculas/{cursoId}")
	Aluno upsertMatricula(@PathVariable Long alunoId, @PathVariable Long cursoId, @RequestBody MatriculaForm matriculaForm) {
		return alunoRepository.findById(alunoId)
							  .map(aluno -> {
								  final Matricula newMatricula = aluno.getMatriculas().stream()
																	  .filter(matricula -> matriculaPredicate(matricula, alunoId, cursoId))
																	  .findFirst()
																	  .orElseGet(() -> cursoRepository.findById(cursoId)
																									  .map(curso -> {
																										  final Matricula matricula = new Matricula();
																										  matricula.setAluno(aluno);
																										  matricula.setCurso(curso);
																										  return matricula;
																									  })
																									  .orElseThrow(() -> new NotFoundException(Curso.class, cursoId)));

								  newMatricula.setFrequencia(matriculaForm.getFrequencia());
								  newMatricula.setMedia(matriculaForm.getMedia());

								  aluno.getMatriculas().removeIf(matricula -> matriculaPredicate(matricula, alunoId, cursoId));
								  aluno.getMatriculas().add(newMatricula);

								  return alunoRepository.save(aluno);
							  })
							  .orElseThrow(() -> new NotFoundException(Aluno.class, alunoId));
	}

	@DeleteMapping("/alunos/{alunoId}/matriculas/{cursoId}")
	Aluno removeMatricula(@PathVariable Long alunoId, @PathVariable Long cursoId) {
		return alunoRepository.findById(alunoId)
							  .map(aluno -> {
								  aluno.getMatriculas().removeIf(matricula -> matriculaPredicate(matricula, alunoId, cursoId));
								  return alunoRepository.save(aluno);
							  })
							  .orElseThrow(() -> new NotFoundException(Aluno.class, alunoId));
	}

	private boolean matriculaPredicate(Matricula matricula, Long alunoId, Long cursoId) {
		return matricula.getAluno().getId().equals(alunoId) && matricula.getCurso().getId().equals(cursoId);
	}

	@DeleteMapping("/alunos/{id}")
	void delete(@PathVariable Long id) {
		alunoRepository.deleteById(id);
	}

}

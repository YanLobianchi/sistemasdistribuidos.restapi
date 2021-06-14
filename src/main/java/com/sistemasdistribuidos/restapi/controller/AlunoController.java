package com.sistemasdistribuidos.restapi.controller;

import com.sistemasdistribuidos.restapi.domain.Aluno;
import com.sistemasdistribuidos.restapi.domain.Curso;
import com.sistemasdistribuidos.restapi.domain.Matricula;
import com.sistemasdistribuidos.restapi.domain.forms.AlunoForm;
import com.sistemasdistribuidos.restapi.domain.forms.MatriculaForm;
import com.sistemasdistribuidos.restapi.exception.NotFoundException;
import com.sistemasdistribuidos.restapi.repository.AlunoRepository;
import com.sistemasdistribuidos.restapi.repository.CursoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos")
public class AlunoController {

	private final AlunoRepository alunoRepository;
	private final CursoRepository cursoRepository;

	AlunoController(AlunoRepository alunoRepository, CursoRepository cursoRepository) {
		this.alunoRepository = alunoRepository;
		this.cursoRepository = cursoRepository;
	}

	@Operation(summary = "Lista todos os Alunos")
	@GetMapping
	List<Aluno> all() {
		return alunoRepository.findAll();
	}

	@Operation(summary = "Cria um novo Aluno")
	@PostMapping
	Aluno create(@RequestBody AlunoForm aluno) {
		return alunoRepository.save(new Aluno(aluno.getNome()));
	}

	@Operation(summary = "Retorna Aluno correspondente ao ID informado")
	@GetMapping("/{id}")
	Aluno read(@PathVariable Long id) {
		return alunoRepository.findById(id)
							  .orElseThrow(() -> new NotFoundException(Aluno.class, id));
	}

	@Operation(summary = "Atualiza Aluno correspondente ao ID informado")
	@PutMapping("/{id}")
	Aluno update(@PathVariable Long id, @RequestBody AlunoForm newAluno) {
		return alunoRepository.findById(id)
							  .map(aluno -> {
								  aluno.setNome(newAluno.getNome());
								  return alunoRepository.save(aluno);
							  })
							  .orElseThrow(() -> new NotFoundException(Aluno.class, id));
	}

	@Operation(summary = "Insere ou atualiza uma Matrícula correspondente ao ID do Aluno e ao ID do Curso informados")
	@PutMapping("/{alunoId}/matriculas/{cursoId}")
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

	@Operation(summary = "Remove uma Matrícula correspondente ao ID do Aluno e ao ID do Curso informados")
	@DeleteMapping("/{alunoId}/matriculas/{cursoId}")
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

	@Operation(summary = "Remove um Aluno correspondente ao ID informado")
	@DeleteMapping("/{id}")
	void delete(@PathVariable Long id) {
		alunoRepository.deleteById(id);
	}

}

package com.example.edutech.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.edutech.Model.Curso;
import com.example.edutech.Service.CursoService;



@RestController
@RequestMapping("/api/v1/cursos")

public class CursoController {
    @Autowired
    private CursoService cursoService;

    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.getCursos();
    }

    @PostMapping
    public Curso agregarCurso(@RequestBody Curso curso) {
        return cursoService.saveCurso(curso);
    }

    @GetMapping("{id}")
    public Curso buscarCurso(@PathVariable int id){
        return cursoService.getCursoid(id);
    }

    @PutMapping("{id}")
    public Curso actualizarCurso(@PathVariable int id, @RequestBody Curso curso) {
        return cursoService.updateCurso(curso);
    }

    @DeleteMapping("{id}")
    public String eliminarCurso(@PathVariable int id) {
        return cursoService.deleteCurso(id);
    }

    @GetMapping("/total")
    public int totalCursosV2() {
        return cursoService.totalCursosV2();
    }
}

package com.example.edutech.Controller;

import java.util.List;

//importar las librerias de swagger para la documentacion de las API's
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
//agregar anotación tag para agrupar el controlador en swagger
@Tag(name = "Catálogo de cursos", description = "Operaciones sobre el catálogo de cursos")

public class CursoController {
    @Autowired
    private CursoService cursoService;

    //agregar anotacion operation para documentar cada endpoint o método REST 
    @Operation(summary = "Muestra todos los cursos disponibles", description = "Obtiene la lista de todos los cursos disponibles")
    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.getCursos();
    }

    @Operation(summary = "Agregar un curso", description = "Agrega un nuevo curso a la base de datos")
    @PostMapping
    public Curso agregarCurso(@RequestBody Curso curso) {
        return cursoService.saveCurso(curso);
    }

    @Operation(summary = "Buscar un curso por su id", description = "Obtiene un curso por su id")
    @GetMapping("{id}")
    public Curso buscarCurso(@PathVariable int id){
        return cursoService.getCursoid(id);
    }

    @Operation(summary = "Actualizar un curso", description = "Actualiza un curso existente en la base de datos")
    @PutMapping("{id}")
    public Curso actualizarCurso(@PathVariable int id, @RequestBody Curso curso) {
        return cursoService.updateCurso(curso);
    }

    @Operation(summary = "Eliminar un curso", description = "Elimina un curso existente en la base de datos")
    @DeleteMapping("{id}")
    public String eliminarCurso(@PathVariable int id) {
        return cursoService.deleteCurso(id);
    }

    @Operation(summary = "Obtener el total de cursos", description = "Obtiene el número total de cursos en la base de datos")
    @GetMapping("/total")
    public int totalCursosV2() {
        return cursoService.totalCursosV2();
    }
}

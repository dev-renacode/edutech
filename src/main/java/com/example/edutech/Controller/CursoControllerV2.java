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

//importar el assembler para HATEOAS de este controlador
import com.example.edutech.assemblers.cursoModelAssembler;

//Importar las clases necesarias para usar HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//importar las clases de HATEOAS EntityModel, CollectionModel y MediaType para manejar los modelos de respuestas 
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;

//importar las clases responseEntity para mejorar las respuestas http
import org.springframework.http.ResponseEntity;

//importar Stream y colecciones para manejar la lista cursos en java
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/cursos")
//agregar anotación tag para agrupar el controlador en swagger
@Tag(name = "Catálogo de cursos", description = "Operaciones sobre el catálogo de cursos")

public class CursoControllerV2 {
    @Autowired
    private CursoService cursoService;

    //inyectar el assembler de Curso
    @Autowired
    private cursoModelAssembler assembler;

    //agregar anotacion operation para documentar cada endpoint o método REST 
    @Operation(summary = "Muestra todos los cursos disponibles", description = "Obtiene la lista de todos los cursos disponibles")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Curso>> listarCursos() {
        //Obtener la lista de cursos y la convertimos a EntityModel usando el Assembler
        List<EntityModel<Curso>> cursos = cursoService.getCursos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(cursos,
            linkTo(methodOn(CursoControllerV2.class).listarCursos()).withSelfRel());
    }

    @Operation(summary = "Agregar un curso", description = "Agrega un nuevo curso a la base de datos")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Curso>> agregarCurso(@RequestBody Curso curso) {
        Curso crear = cursoService.saveCurso(curso);
        return ResponseEntity
            .created(linkTo(methodOn(CursoControllerV2.class).buscarCurso(crear.getId())).toUri())
            .body(assembler.toModel(crear));
    }

    @Operation(summary = "Buscar un curso por su id", description = "Obtiene un curso por su id")
    @GetMapping("{id}")
    public Curso buscarCurso(@PathVariable int id){
        return cursoService.getCursoid(id);
    }

    @Operation(summary = "Actualizar un curso", description = "Actualiza un curso existente en la base de datos")
    @PutMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
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

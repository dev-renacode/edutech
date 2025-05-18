package com.example.edutech.Service;

import com.example.edutech.Model.Curso;
import com.example.edutech.Repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service

public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> getCursos() {
        return cursoRepository.obtenerCursos();
    }

    public Curso saveCurso(Curso curso){
        return cursoRepository.guardarCurso(curso);
    }

    public Curso getCursoid(int id) {
        return cursoRepository.buscarPorId(id);
    }

    public Curso updateCurso(Curso curso) {
        return cursoRepository.actualizar(curso);
    }

    public String deleteCurso(int id) {
        cursoRepository.eliminarCurso(id);
        return "Curso eliminado";
    }

    public int totalCursos() {
        return cursoRepository.obtenerCursos().size();
    }

    // LA ACCIÓN LA HACE EL MODELO
    public int totalCursosV2() {
        return cursoRepository.totalCursos();
    }
  
}

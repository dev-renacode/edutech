package com.example.edutech.Repository;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import com.example.edutech.Model.Curso;

@Repository
public class CursoRepository {
    private List<Curso> listaCursos = new ArrayList<>();

    public CursoRepository() {
        listaCursos.add(new Curso(1, "Curso de Java", 20240101, "Juan Perez", "Curso de Java", 10, 100));
        listaCursos.add(new Curso(2, "Curso de CSS", 20240101, "Juan Lopez", "Curso de Java", 10, 100));
    }

    public List<Curso> obtenerCursos() {
        return listaCursos;
    }

    public Curso buscarPorId(int id) {
        for (Curso curso : listaCursos) {
            if (curso.getId() == id) {
                return curso;
            } 
        }
        return null;
    }

    public Curso guardarCurso(Curso cur) {
        long nuevoId = 1;
        for (Curso c : listaCursos) {
            if (c.getId() >= nuevoId) {
                nuevoId = c.getId() + 1;
            }
        }

        Curso curso = new Curso();
        curso.setId((int) nuevoId);
        curso.setNombreCurso(cur.getNombreCurso());
        curso.setDescripcion(cur.getDescripcion());
        curso.setPrecio(cur.getPrecio());
        curso.setFechaCreacion(cur.getFechaCreacion());
        curso.setDocenteCurso(cur.getDocenteCurso());
        curso.setHorasDuracion(cur.getHorasDuracion());

        listaCursos.add(curso);
        return curso;
    }
    
    public Curso actualizar(Curso cur){
        int id = 0;
        int idPosicion = 0;

        for (int i = 0; i < listaCursos.size(); i++) {
            if (listaCursos.get(i).getId() == cur.getId()) {
                id = cur.getId();
                idPosicion = i;
            }
        }

        Curso curso = new Curso();
        curso.setId(id);
        curso.setNombreCurso(cur.getNombreCurso());
        curso.setDescripcion(cur.getDescripcion());
        curso.setPrecio(cur.getPrecio());
        curso.setFechaCreacion(cur.getFechaCreacion());
        curso.setDocenteCurso(cur.getDocenteCurso());
        curso.setHorasDuracion(cur.getHorasDuracion());

        listaCursos.set(idPosicion, curso);
        return curso;
    }
    
    public void eliminarCurso(int id){
        listaCursos.removeIf(c -> c.getId() == id);
    }

    public int totalCursos(){
        return listaCursos.size();
    }
}

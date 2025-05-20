package com.example.edutech.Repository;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import com.example.edutech.Model.Curso;

@Repository
public class CursoRepository {
    private List<Curso> listaCursos = new ArrayList<>();

    public CursoRepository() {
        listaCursos.add(new Curso(1, "Curso de Java", 2024, "Juan Perez", "Curso de Java desde las bases hasta pro", 67, 40000));
        listaCursos.add(new Curso(2, "Curso de Javascript", 2023, "Fernando Juarez", "Curso de javascript para principiantes", 90, 90000));
        listaCursos.add(new Curso(3, "Curso de Finanzas", 2022, "Luis Saldavia", "Curso de finanzas, establece metas y logra lo imposible", 120, 50000));
        listaCursos.add(new Curso(4, "Curso de Fullstack", 2025, "Alan Gajardo", "Fullstack desde las bases por cada detalle", 10, 100));
        listaCursos.add(new Curso(5, "Curso de Marketing Digital", 2023, "Maria Burgos", "Marketing, la importancia de aprenderlo", 16, 100));
        listaCursos.add(new Curso(6, "Curso de Mecánica", 2023, "Camila Perez", "Aprende a armar un Ferrari en tu casa", 5, 100));
        listaCursos.add(new Curso(7, "Curso de Matemática Avanzada", 2025, "Karina de las Mercedes", "Aprende Matemática desde el inicio", 324, 100000));
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

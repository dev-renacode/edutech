package com.example.edutech.Repository;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import com.example.edutech.Model.Curso;

@Repository
public class CursoRepository {
    private List<Curso> listaCursos = new ArrayList<>();

    public CursoRepository() {
        //Aqui se crean cursos de ejemplo
        listaCursos.add(new Curso(1, "Curso de Java", 2024, "Juan Pérez", "Curso completo de Java desde cero hasta nivel avanzado", 120, 299900));
        listaCursos.add(new Curso(2, "Curso de JavaScript", 2024, "María González", "Aprende JavaScript y desarrollo web moderno", 90, 249900));
        listaCursos.add(new Curso(3, "Curso de Python", 2024, "Carlos Rodríguez", "Python para ciencia de datos y desarrollo web", 150, 349900));
        listaCursos.add(new Curso(4, "Curso de Desarrollo Web Full Stack", 2024, "Ana Martínez", "HTML, CSS, JavaScript, Node.js y React", 180, 399900));
        listaCursos.add(new Curso(5, "Curso de Marketing Digital", 2024, "Pedro Silva", "Estrategias de marketing digital y redes sociales", 60, 199900));
        listaCursos.add(new Curso(6, "Curso de Finanzas Personales", 2024, "Laura Sánchez", "Aprende a gestionar tus finanzas personales", 45, 149900));
        listaCursos.add(new Curso(7, "Curso de Matemática Avanzada", 2024, "Karina de las Mercedes", "Matemática avanzada para programación y análisis", 100, 279900));
        listaCursos.add(new Curso(8, "Curso de Diseño UX/UI", 2024, "Diego Ramírez", "Diseño de interfaces y experiencia de usuario", 80, 229900));
        listaCursos.add(new Curso(9, "Curso de Base de Datos", 2024, "Valentina Torres", "SQL, MongoDB y diseño de bases de datos", 70, 199900));
        listaCursos.add(new Curso(10, "Curso de Ciberseguridad", 2024, "Roberto Méndez", "Fundamentos de seguridad informática", 110, 329900));
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

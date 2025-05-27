package com.example.edutech.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.edutech.Model.Reportes;

@Repository

public class ReportesRepository {
    private List<Reportes> listaReportes = new ArrayList<>();
    
    public ReportesRepository(){
        //AQUI VAN LOS REPORTES CREADOS POR UNO MISMO
    }

    public List<Reportes> obtenerReportes(){
        return listaReportes;
    }

    public Reportes buscarPorId(int id){
        for (Reportes rep : listaReportes) {
            if (rep.getId() == id) {
                return rep;
            }
        }
        return null;
    }

    public Reportes guardaReportes(Reportes rep){
        long nuevoId = 1;
        for (Reportes r : listaReportes){
            if (r.getId() >= nuevoId){
                nuevoId = r.getId() + 1;
            }
        }
        Reportes reportes = new Reportes();
        reportes.setId((int) nuevoId);
        reportes.setTitulo(rep.getTitulo());
        reportes.setDescripcion(rep.getDescripcion());
        reportes.setDescripcion(rep.getDescripcion());
        
        listaReportes.add(reportes);
        return reportes;
    }

    public Reportes actualizar(Reportes rep){
        int id = 0;
        int idPosicion = 0;

        for (int i = 0; i < listaReportes.size(); i++) {
            if (listaReportes.get(i).getId() == rep.getId()){
                id = rep.getId();
                idPosicion = i;
            }
        }
        Reportes reportes = new Reportes();
        reportes.setId(id);
        reportes.setTitulo(rep.getTitulo());
        reportes.setEmail(rep.getEmail());
        reportes.setDescripcion(rep.getDescripcion());

        listaReportes.set(idPosicion, reportes);
        return reportes;
    }

    public void eliminarReporte(int id){
        listaReportes.removeIf(c -> c.getId() == id);
    }

    public int totalReportes(){
        return listaReportes.size();
    }
}

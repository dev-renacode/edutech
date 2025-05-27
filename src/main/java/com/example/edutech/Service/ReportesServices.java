package com.example.edutech.Service;

import com.example.edutech.Model.Reportes;
import com.example.edutech.Repository.ReportesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ReportesServices {
    @Autowired
    private ReportesRepository ReportesRepository;

    public List<Reportes> getReportes() {
        return ReportesRepository.obtenerReportes();
    }

    public Reportes saveReportes(Reportes reportes) {
        return ReportesRepository.guardaReportes(reportes);
    }

    public Reportes getReportesid(int id) {
        return ReportesRepository.buscarPorId(id);
    }

    public Reportes updateReportes(Reportes reportes) {
        return ReportesRepository.actualizar(reportes);
    }

    public String deleteReportes(int id) {
        ReportesRepository.eliminarReporte(id);
        return "Reporte eliminado";
    }

    public int totalReportes() {
        return ReportesRepository.obtenerReportes().size();
    }

    public int totalReportesV2(){
        return ReportesRepository.totalReportes();
    }

}

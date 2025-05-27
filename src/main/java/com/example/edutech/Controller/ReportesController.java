package com.example.edutech.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.edutech.Service.ReportesService;
import com.example.edutech.Model.Reportes;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/reportes")
public class ReportesController {

    @Autowired
    private ReportesService reportesService;

    @GetMapping
    public List<Reportes> listarReportes() {
        return reportesService.getReportes();
    }

    @PostMapping
    public Reportes agregarReportes(@RequestBody Reportes reportes){
        return reportesService.saveReportes(reportes);
    }
    
    @GetMapping("path")
    public Reportes buscarReportes(@PathVariable int id){
        return reportesService.getReportesid(id);
    }
    
    @PutMapping("{id}")
    public Reportes actualizarReportes(@PathVariable int id, @RequestBody Reportes reportes) {
        return reportesService.updateReportes(reportes);
    }

    @DeleteMapping("{id}")
    public String eliminarReportes(@PathVariable int id){
        return reportesService.deleteReportes(id);
    }

    @GetMapping("/total")
    public int totalReportesV2() {
        return reportesService.totalReportesV2();
    }
    
}
    

package com.example.edutech.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.edutech.Model.Reportes;
import com.example.edutech.Service.ReportesService;
import java.util.*;

@RestController
@RequestMapping("api/v1/reportes")
@CrossOrigin
public class ReportesController {
    private ReportesService reportesServices;

    ReportesController(ReportesService reportesServices) {
        this.reportesServices = reportesServices;
    }

    @PostMapping("/guardar")
    public Reportes guardar(@RequestBody Reportes r){
        return reportesServices.guardar(r);
    }

    @GetMapping("/obtener/{id}")
    public Optional<Reportes> obtenerReportes(@PathVariable Long id){
        return reportesServices.obtenerReportes(id);
    }
    
}

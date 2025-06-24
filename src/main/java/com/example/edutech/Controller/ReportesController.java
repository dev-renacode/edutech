package com.example.edutech.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.edutech.Model.Reportes;
import com.example.edutech.Service.ReportesService;
import java.util.*;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("api/v1/reportes")
@CrossOrigin
public class ReportesController {
    private ReportesService reportesServices;

    public ReportesController(ReportesService reportesServices) {
        this.reportesServices = reportesServices;
    }

    @Operation(summary = "Guardar reporte", description = "Guardar un reporte")
    @PostMapping("/guardar")
    public Reportes guardar(@RequestBody Reportes r){
        return reportesServices.guardar(r);
    }

    @Operation(summary = "Obtener reporte", description = "Obtener un reporte por su ID")
    @GetMapping("/obtener/{id}")
    public Optional<Reportes> obtenerReportes(@PathVariable Long id){
        return reportesServices.obtenerReportes(id);
    }

    @Operation(summary = "Listar reportes", description = "Listar todos los reportes")
    @GetMapping("/listar")
    public List<Reportes> listarReportes(){
        return reportesServices.listarReportes();
    }
}

package com.example.demo.controller;

import com.example.demo.dto.HttpGlobalResponse;
import com.example.demo.dto.TareaRequestDTO;
import com.example.demo.dto.TareaResponseDTO;
import com.example.demo.entity.EstadoTarea;
import com.example.demo.entity.PrioridadTarea;
import com.example.demo.service.TareaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@AllArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @GetMapping
    public HttpGlobalResponse<List<TareaResponseDTO>> listarTodas() {
        HttpGlobalResponse<List<TareaResponseDTO>> response = new HttpGlobalResponse<>();
        response.setSuccess(true);
        response.setMessage("Tareas listadas exitosamente");
        response.setData(tareaService.listarTodas());
        return response;
    }

    @GetMapping("/{id}")
    public HttpGlobalResponse<TareaResponseDTO> obtenerPorId(@PathVariable Long id) {
        HttpGlobalResponse<TareaResponseDTO> response = new HttpGlobalResponse<>();
        response.setSuccess(true);
        response.setMessage("Tarea encontrada con éxito");
        response.setData(tareaService.obtenerPorId(id));
        return response;
    }

    @PostMapping
    public ResponseEntity<HttpGlobalResponse<TareaResponseDTO>> crearTarea(@RequestBody TareaRequestDTO request) {
        HttpGlobalResponse<TareaResponseDTO> response = new HttpGlobalResponse<>();
        response.setSuccess(true);
        response.setMessage("Tarea creada exitosamente");
        response.setData(tareaService.crearTarea(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public HttpGlobalResponse<TareaResponseDTO> actualizarTarea(@PathVariable Long id, @RequestBody TareaRequestDTO request) {
        HttpGlobalResponse<TareaResponseDTO> response = new HttpGlobalResponse<>();
        response.setSuccess(true);
        response.setMessage("Tarea actualizada de forma completa");
        response.setData(tareaService.actualizarTarea(id, request));
        return response;
    }

    @PatchMapping("/{id}/estado")
    public HttpGlobalResponse<TareaResponseDTO> cambiarEstado(@PathVariable Long id, @RequestParam EstadoTarea estado) {
        HttpGlobalResponse<TareaResponseDTO> response = new HttpGlobalResponse<>();
        response.setSuccess(true);
        response.setMessage("Estado de la tarea actualizado con éxito");
        response.setData(tareaService.cambiarEstado(id, estado));
        return response;
    }

    @DeleteMapping("/{id}")
    public HttpGlobalResponse<Void> eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        HttpGlobalResponse<Void> response = new HttpGlobalResponse<>();
        response.setSuccess(true);
        response.setMessage("Tarea eliminada correctamente");
        return response;
    }

    @GetMapping("/filtrar/estado")
    public HttpGlobalResponse<List<TareaResponseDTO>> filtrarPorEstado(@RequestParam EstadoTarea estado) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = new HttpGlobalResponse<>();
        response.setSuccess(true);
        response.setMessage("Tareas filtradas por estado");
        response.setData(tareaService.filtrarPorEstado(estado));
        return response;
    }

    @GetMapping("/filtrar/prioridad")
    public HttpGlobalResponse<List<TareaResponseDTO>> filtrarPorPrioridad(@RequestParam PrioridadTarea prioridad) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = new HttpGlobalResponse<>();
        response.setSuccess(true);
        response.setMessage("Tareas filtradas por prioridad");
        response.setData(tareaService.filtrarPorPrioridad(prioridad));
        return response;
    }

    @GetMapping("/buscar")
    public HttpGlobalResponse<List<TareaResponseDTO>> buscarPorTitulo(@RequestParam String q) {
        HttpGlobalResponse<List<TareaResponseDTO>> response = new HttpGlobalResponse<>();
        response.setSuccess(true);
        response.setMessage("Tareas encontradas");
        response.setData(tareaService.buscarPorTitulo(q));
        return response;
    }
}
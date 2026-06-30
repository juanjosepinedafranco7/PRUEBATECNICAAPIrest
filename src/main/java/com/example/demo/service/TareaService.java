package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.TareaRequestDTO;
import com.example.demo.dto.TareaResponseDTO;
import com.example.demo.entity.EstadoTarea;
import com.example.demo.entity.PrioridadTarea;
import com.example.demo.entity.Tarea;
import com.example.demo.repository.TareaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaResponseDTO crearTarea(TareaRequestDTO request) {
        if (request.getTitulo() == null || request.getTitulo().trim().length() < 3) {
            throw new IllegalArgumentException("El título no puede estar vacío ni tener menos de 3 caracteres");
        }

        Tarea tarea = new Tarea();
        tarea.setTitulo(request.getTitulo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setPrioridad(request.getPrioridad());

        tareaRepository.save(tarea);

        return mapToDTO(tarea);
    }

    public List<TareaResponseDTO> listarTodas() {
        return tareaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TareaResponseDTO obtenerPorId(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tarea no encontrada"));
        return mapToDTO(tarea);
    }

    public void eliminarTarea(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tarea no encontrada"));
        tareaRepository.delete(tarea);
    }

    public TareaResponseDTO actualizarTarea(Long id, TareaRequestDTO request) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tarea no encontrada"));

        if (tarea.getEstado() == EstadoTarea.CANCELADA) {
            throw new IllegalStateException("Conflict: Una tarea en estado CANCELADA no puede cambiar de estado ni ser modificada");
        }

        if (request.getTitulo() == null || request.getTitulo().trim().length() < 3) {
            throw new IllegalArgumentException("El título no puede tener menos de 3 caracteres");
        }

        tarea.setTitulo(request.getTitulo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setPrioridad(request.getPrioridad());

        tareaRepository.save(tarea);

        return mapToDTO(tarea);
    }

    public TareaResponseDTO cambiarEstado(Long id, EstadoTarea nuevoEstado) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tarea no encontrada"));

        EstadoTarea estadoActual = tarea.getEstado();

        if (estadoActual == EstadoTarea.CANCELADA) {
            throw new IllegalStateException("Conflict: Una tarea en estado CANCELADA no puede cambiar de estado");
        }

        if (estadoActual == EstadoTarea.COMPLETADA &&
                (nuevoEstado == EstadoTarea.PENDIENTE || nuevoEstado == EstadoTarea.EN_PROGRESO)) {
            throw new IllegalStateException("Conflict: Una tarea en estado COMPLETADA no puede volver a PENDIENTE ni a EN_PROGRESO");
        }

        tarea.setEstado(nuevoEstado);
        tareaRepository.save(tarea);

        return mapToDTO(tarea);
    }

    public List<TareaResponseDTO> filtrarPorEstado(EstadoTarea estado) {
        return tareaRepository.findByEstado(estado).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TareaResponseDTO> filtrarPorPrioridad(PrioridadTarea prioridad) {
        return tareaRepository.findByPrioridad(prioridad).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TareaResponseDTO> buscarPorTitulo(String q) {
        return tareaRepository.findByTituloContainingIgnoreCase(q).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private TareaResponseDTO mapToDTO(Tarea tarea) {
        TareaResponseDTO dto = new TareaResponseDTO();
        dto.setId(tarea.getId());
        dto.setTitulo(tarea.getTitulo());
        dto.setDescripcion(tarea.getDescripcion());
        dto.setEstado(tarea.getEstado());
        dto.setPrioridad(tarea.getPrioridad());
        dto.setFechaCreacion(tarea.getFechaCreacion());
        dto.setFechaActualizacion(tarea.getFechaActualizacion());
        return dto;
    }
}
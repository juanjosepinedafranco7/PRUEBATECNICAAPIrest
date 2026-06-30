package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.EstadoTarea;
import com.example.demo.entity.PrioridadTarea;

import lombok.Data;

@Data
public class TareaResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private EstadoTarea estado;
    private PrioridadTarea prioridad;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
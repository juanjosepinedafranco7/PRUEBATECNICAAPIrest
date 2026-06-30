package com.example.demo.dto;

import com.example.demo.entity.PrioridadTarea;

import lombok.Data;

@Data
public class TareaRequestDTO {
    private String titulo;
    private String descripcion;
    private PrioridadTarea prioridad;
}
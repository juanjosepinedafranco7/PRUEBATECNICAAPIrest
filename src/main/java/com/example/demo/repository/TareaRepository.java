package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.EstadoTarea;
import com.example.demo.entity.PrioridadTarea;
import com.example.demo.entity.Tarea;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByEstado(EstadoTarea estado);

    List<Tarea> findByPrioridad(PrioridadTarea prioridad);

    List<Tarea> findByTituloContainingIgnoreCase(String q);
}
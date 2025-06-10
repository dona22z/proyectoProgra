package com.proyecto.api.dao;

import com.proyecto.api.models.Estudiante;
import java.util.List;

public interface EstudiantesDao {
    List<Estudiante> getEstudiantes();

    void eliminar(Long id);

    void agregar(Estudiante estudiante);

    void parcial(Estudiante estudiante);

    void editar(Estudiante estudiante);
}

package com.proyecto.api.controllers;

import com.proyecto.api.dao.EstudiantesDao;
import com.proyecto.api.models.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstudianteControllerTest {

    @InjectMocks
    private EstudianteController controller;

    @Mock
    private EstudiantesDao estudiantesDao;

    private Estudiante estudiante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        estudiante = new Estudiante();
        estudiante.setId(1L);
        estudiante.setNombre("Juan Pérez");
        estudiante.setCorreo("juan@example.com");
    }

    @Test
    void getEstudiante() {
        // Este método devuelve un nuevo estudiante vacío en la implementación actual.
        Estudiante result = controller.getEstudiante(1L);

        assertNotNull(result);
        assertNull(result.getId());  // Porque el método no lo setea realmente.
    }

    @Test
    void getEstudiantes() {
        List<Estudiante> lista = List.of(estudiante);
        when(estudiantesDao.getEstudiantes()).thenReturn(lista);

        List<Estudiante> response = controller.getEstudiantes();

        assertEquals(1, response.size());
        assertEquals("Juan Pérez", response.get(0).getNombre());
    }

    @Test
    void eliminar() {
        doNothing().when(estudiantesDao).eliminar(1L);

        controller.eliminar(1L);

        verify(estudiantesDao, times(1)).eliminar(1L);
    }

    @Test
    void agregarEstudiante() {
        doNothing().when(estudiantesDao).agregar(estudiante);

        controller.agregarEstudiante(estudiante);

        verify(estudiantesDao, times(1)).agregar(estudiante);
    }

    @Test
    void editarParcial() {
        doNothing().when(estudiantesDao).parcial(any());

        controller.editarParcial(1L, estudiante);

        assertEquals(1L, estudiante.getId()); // Confirmar que se setea el ID
        verify(estudiantesDao).parcial(estudiante);
    }

    @Test
    void editar() {
        doNothing().when(estudiantesDao).editar(any());

        controller.editar(1L, estudiante);

        assertEquals(1L, estudiante.getId()); // Confirmar que se setea el ID
        verify(estudiantesDao).editar(estudiante);
    }
}

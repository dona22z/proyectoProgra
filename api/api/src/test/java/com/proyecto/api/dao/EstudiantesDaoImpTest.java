package com.proyecto.api.dao;

import com.proyecto.api.models.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EstudiantesDaoImpTest {

    private EstudiantesDaoImp dao;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        dao = new EstudiantesDaoImp();
        entityManager = mock(EntityManager.class);
        dao.entityManager = entityManager;
    }

    @Test
    void getEstudiantes() {
        Estudiante est = new Estudiante();
        List<Estudiante> estudiantes = Arrays.asList(est);

        TypedQuery<Estudiante> query = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Estudiante")).thenReturn(query);
        when(query.getResultList()).thenReturn(estudiantes);

        List<Estudiante> result = dao.getEstudiantes();
        assertEquals(1, result.size());
        verify(entityManager).createQuery("FROM Estudiante");
    }

    @Test
    void eliminar() {
        Estudiante est = new Estudiante();
        est.setId(1L);

        when(entityManager.find(Estudiante.class, 1L)).thenReturn(est);

        dao.eliminar(1L);
        verify(entityManager).remove(est);
    }

    @Test
    void agregarNuevoEstudiante() {
        Estudiante est = new Estudiante();
        est.setCorreo("nuevo@correo.com");

        TypedQuery<Estudiante> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Estudiante.class))).thenReturn(query);
        when(query.setParameter(eq("correo"), eq("nuevo@correo.com"))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        dao.agregar(est);
        verify(entityManager).merge(est);
    }

    @Test
    void agregarEstudianteExistenteLanzaExcepcion() {
        Estudiante est = new Estudiante();
        est.setCorreo("existe@correo.com");

        Estudiante existente = new Estudiante();
        existente.setCorreo("existe@correo.com");

        TypedQuery<Estudiante> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Estudiante.class))).thenReturn(query);
        when(query.setParameter(eq("correo"), eq("existe@correo.com"))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(existente));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> dao.agregar(est));
        assertEquals("El correo ya está registrado.", thrown.getMessage());
    }

    @Test
    void parcial() {
        Estudiante est = new Estudiante();
        est.setId(1L);
        est.setCorreo("nuevo@correo.com");

        Estudiante existente = new Estudiante();
        existente.setId(1L);
        existente.setCorreo("viejo@correo.com");

        when(entityManager.find(Estudiante.class, 1L)).thenReturn(existente);

        dao.parcial(est);
        assertEquals("nuevo@correo.com", existente.getCorreo());
        verify(entityManager).merge(existente);
    }

    @Test
    void editar() {
        Estudiante est = new Estudiante();
        est.setId(1L);
        est.setNombre("Nuevo Nombre");
        est.setCorreo("nuevo@correo.com");
        est.setNumero(123);
        est.setIdioma("Español");

        Estudiante existente = new Estudiante();
        existente.setId(1L);

        when(entityManager.find(Estudiante.class, 1L)).thenReturn(existente);

        dao.editar(est);
        assertEquals("Nuevo Nombre", existente.getNombre());
        assertEquals("nuevo@correo.com", existente.getCorreo());
        assertEquals("123", existente.getNumero());
        assertEquals("Español", existente.getIdioma());
        verify(entityManager).merge(existente);
    }
}

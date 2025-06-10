package com.proyecto.api.dao;

import com.proyecto.api.models.Estudiante;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Transactional

public class EstudiantesDaoImp implements EstudiantesDao{


    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<Estudiante> getEstudiantes() {
        String query = "FROM Estudiante";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Estudiante estudiante = entityManager.find(Estudiante.class, id);
        entityManager.remove(estudiante);
    }

    @Override
    public void agregar(Estudiante estudiante) {
        String query = "FROM Estudiante WHERE correo = :correo";
        List<Estudiante> existentes = entityManager.createQuery(query, Estudiante.class)
                .setParameter("correo", estudiante.getCorreo())
                .getResultList();

        if (existentes.isEmpty()) {
            entityManager.merge(estudiante);
        } else {
            throw new RuntimeException("El correo ya está registrado.");
        }
    }

    @Override
    public void parcial(Estudiante estudiante) {
        Estudiante existente = entityManager.find(Estudiante.class, estudiante.getId());

        if (existente != null) {
            if (estudiante.getCorreo() != null) {
                existente.setCorreo(estudiante.getCorreo());
            }
            if (estudiante.getNumero() != null) {
                existente.setNumero(estudiante.getNumero());
            }
            if (estudiante.getIdioma() != null) {
                existente.setIdioma(estudiante.getIdioma());
            }

            entityManager.merge(existente);
        }
    }

    @Override
    public void editar(Estudiante estudiante) {
        Estudiante existente = entityManager.find(Estudiante.class, estudiante.getId());

        if (existente != null) {
            if(estudiante.getNombre() != null){
                existente.setNombre(estudiante.getNombre());
            }
            if (estudiante.getCorreo() != null) {
                existente.setCorreo(estudiante.getCorreo());
            }
            if (estudiante.getNumero() != null) {
                existente.setNumero(estudiante.getNumero());
            }
            if (estudiante.getIdioma() != null) {
                existente.setIdioma(estudiante.getIdioma());
            }

            entityManager.merge(existente);
        }
    }

}

package com.proyecto.api.controllers;

import com.proyecto.api.dao.EstudiantesDao;
import com.proyecto.api.models.Estudiante;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EstudianteController {

    @Autowired
    private EstudiantesDao estudiantesDao;

    @RequestMapping(value = "students/{id}", method = RequestMethod.GET)
    public Estudiante getEstudiante(@PathVariable long id) {
        Estudiante estudiante = new Estudiante();
        return estudiante;
    }

    @RequestMapping(value =  "students")
    public List<Estudiante> getEstudiantes() {
        return estudiantesDao.getEstudiantes();
    }

    @RequestMapping(value = "students/{id}", method = RequestMethod.DELETE)
    public void eliminar(@PathVariable Long id){
        estudiantesDao.eliminar(id);
    }

    @RequestMapping(value = "students", method = RequestMethod.POST)
    public void agregarEstudiante(@RequestBody Estudiante estudiante){
        estudiantesDao.agregar(estudiante);
    }

    @Override
    public String toString() {
        return "EstudianteController{" +
                "estudiantesDao=" + estudiantesDao +
                '}';
    }

    @RequestMapping(value = "students/{id}", method = RequestMethod.PATCH)
    public void editarParcial(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        estudiante.setId(id);
        estudiantesDao.parcial(estudiante);
    }
    @RequestMapping(value = "students/{id}", method = RequestMethod.PUT)
    public void editar(@PathVariable Long id, @RequestBody Estudiante estudiante){
        estudiante.setId(id);
        estudiantesDao.editar(estudiante);
    }


}

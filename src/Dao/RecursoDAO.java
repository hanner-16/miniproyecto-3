/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 * @author Hanner Fernando Sinisterra <hanner.sinisterra@correounivalle.edu.co>
 * @author Daniel Esteban Gallego Velasco <gallego.daniel@correounivalle.edu.co>
 */

import Entidades.Recurso;
import java.util.List;

public interface RecursoDAO
{
    void guardarRecurso(Recurso recurso);
    void actualizarRecurso(Recurso recurso);
    void eliminarRecurso(Recurso recurso);
    List<Recurso> listarRecursos();
    Recurso obtenerRecurso(int recursoId);
}
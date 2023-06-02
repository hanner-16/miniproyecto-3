/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 * @author Hanner Fernando Sinisterra <hanner.sinisterra@correounivalle.edu.co>
 * @author Daniel Esteban Gallego Velasco <gallego.daniel@correounivalle.edu.co>
 */

import java.util.List;
import Entidades.Préstamo;

public interface PréstamoDAO 
{
    void guardarPréstamo(Préstamo préstamo);
    void actualizarPréstamo(Préstamo préstamo);
    void eliminarPréstamo(Préstamo préstamo);
    List<Préstamo> listarPréstamos();
    Préstamo obtenerPréstamo(int préstamoId);
}

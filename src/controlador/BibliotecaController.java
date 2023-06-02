/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 * @author Hanner Fernando Sinisterra <hanner.sinisterra@correounivalle.edu.co>
 * @author Daniel Esteban Gallego Velasco <gallego.daniel@correounivalle.edu.co>
 */

import Dao.PréstamoDAO;
import Dao.RecursoDAO;
import Dao.UsuarioDAO;

import Entidades.Préstamo;
import Entidades.Recurso;
import Entidades.Usuario;

import Impl.PréstamoDAOImpl;
import Impl.RecursoDAOImpl;
import Impl.UsuarioDAOImpl;

import java.util.List;

public class BibliotecaController
{
    private UsuarioDAO usuarioDAO;
    private RecursoDAO recursoDAO;
    private PréstamoDAO préstamoDAO;

    public BibliotecaController()
    {
        usuarioDAO = new UsuarioDAOImpl();
        recursoDAO = new RecursoDAOImpl();
        préstamoDAO = new PréstamoDAOImpl();
    }

    public void agregarUsuario(int id, String nombre, String tipo) 
    {
        Usuario usuario = new Usuario(usuarioId, nombre, tipo);
        usuarioDAO.guardarUsuario(usuario);
    }

    public void actualizarUsuario(int usuarioId, String nombre, String tipo) 
    {
        Usuario usuario = usuarioDAO.obtenerUsuario(usuarioId);
        if (usuario != null) 
        {
            usuario.setNombre(nombre);
            usuario.setTipo(tipo);
            usuarioDAO.actualizarUsuario(usuario);
        }
        else 
        {
            System.out.println("El usuario no existe.");
        }
    }

    public void eliminarUsuario(int usuarioId) 
    {
        Usuario usuario = usuarioDAO.obtenerUsuario(usuarioId);
        if (usuario != null)
        {
            usuarioDAO.eliminarUsuario(usuario);
        } 
        else 
        {
            System.out.println("El usuario no existe.");
        }
    }

    public void agregarRecurso(int id, String nombre, String tipo) 
    {
        Recurso recurso = new Recurso(id, nombre, tipo);
        recursoDAO.guardarRecurso(recurso);
    }

    public void actualizarRecurso(int recursoId, String nombre, String tipo) 
    {
        Recurso recurso = recursoDAO.obtenerRecurso(recursoId);
        if (recurso != null) 
        {
            recurso.setNombre(nombre);
            recurso.setTipo(tipo);
            recursoDAO.actualizarRecurso(recurso);
        } 
        else 
        {
            System.out.println("El recurso no existe.");
        }
    }

    public void eliminarRecurso(int recursoId)
    {
        Recurso recurso = recursoDAO.obtenerRecurso(recursoId);
        if (recurso != null) 
        {
            recursoDAO.eliminarRecurso(recurso);
        }
        else 
        {
            System.out.println("El recurso no existe.");
        }
    }

    public void solicitarPréstamo(int usuarioId, List<Integer> recursosIds)
    {
        Usuario usuario = usuarioDAO.obtenerUsuario(usuarioId);
        if (usuario != null) 
        {
            List<Recurso> recursos = recursoDAO.obtenerRecursos(recursosIds);
            if (!recursos.isEmpty()) 
            {
                Préstamo préstamo = new Préstamo(usuario, recursos);
                préstamoDAO.guardarPréstamo(préstamo);
            } 
            else 
            {
                System.out.println("Los recursos no existen.");
            }
        }
        else 
        {
            System.out.println("El usuario no existe.");
        }
    }

    public void devolverRecursos(int préstamoId, List<Integer> recursosIds)
    {
        Préstamo préstamo = préstamoDAO.obtenerPréstamo(préstamoId);
        if (préstamo != null) 
        {
            List<Recurso> recursos = recursoDAO.obtenerRecursos(recursosIds);
            if (!recursos.isEmpty()) 
            {
                préstamo.devolverRecursos(recursos);
                préstamoDAO.actualizarPréstamo(préstamo);
            } 
            else 
            {
                System.out.println("Los recursos no existen.");
            }
        }
        else 
        {
            System.out.println("El préstamo no existe.");
        }
    }

    public List<Préstamo> obtenerPréstamosActivos() 
    {
        return préstamoDAO.obtenerPréstamosActivos();
    }
}

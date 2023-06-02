/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Impl;

/**
 * @author Hanner Fernando Sinisterra <hanner.sinisterra@correounivalle.edu.co>
 * @author Daniel Esteban Gallego Velasco <gallego.daniel@correounivalle.edu.co>
 */

import Dao.PréstamoDAO;
import Entidades.Préstamo;
import Entidades.Recurso;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PréstamoDAOImpl implements PréstamoDAO
{
    private static final String PRÉSTAMOS_FILE = "préstamos.txt";

    @Override
    public void guardarPréstamo(Préstamo préstamo)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRÉSTAMOS_FILE, true))) 
        {
            StringBuilder préstamoData = new StringBuilder();
            préstamoData.append(préstamo.getId()).append(",");
            préstamoData.append(préstamo.getUsuario().getId()).append(",");
            for (Recurso recurso : préstamo.getRecursos())
            {
                préstamoData.append(recurso.getId()).append(";");
            }
            préstamoData.deleteCharAt(préstamoData.length() - 1); // Eliminar último punto y coma (;)
            préstamoData.append(",");
            préstamoData.append(préstamo.getFechaRegistro()).append(",");
            préstamoData.append(préstamo.getFechaLimiteDevolucion()).append(",");
            préstamoData.append(préstamo.getFechaDevolucion()).append(",");
            préstamoData.append(préstamo.getEstado().name());

            writer.write(préstamoData.toString());
            writer.newLine();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarPréstamo(Préstamo préstamo)
    {
        List<Préstamo> préstamos = listarPréstamos();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRÉSTAMOS_FILE)))
        {
            for (Préstamo p : préstamos)
            {
                if (p.getId() == préstamo.getId())
                {
                    p.setUsuario(préstamo.getUsuario());
                    p.setRecursos(préstamo.getRecursos());
                    p.setFechaRegistro(préstamo.getFechaRegistro());
                    p.setFechaLimiteDevolucion(préstamo.getFechaLimiteDevolucion());
                    p.setFechaDevolucion(préstamo.getFechaDevolucion());
                    p.setEstado(préstamo.getEstado());
                }
                StringBuilder préstamoData = new StringBuilder();
                préstamoData.append(p.getId()).append(",");
                préstamoData.append(p.getUsuario().getId()).append(",");
                for (Recurso recurso : p.getRecursos()) 
                {
                    préstamoData.append(recurso.getId()).append(";");
                }
                préstamoData.deleteCharAt(préstamoData.length() - 1); // Eliminar último punto y coma (;)
                préstamoData.append(",");
                préstamoData.append(p.getFechaRegistro()).append(",");
                préstamoData.append(p.getFechaLimiteDevolucion()).append(",");
                préstamoData.append(p.getFechaDevolucion()).append(",");
                préstamoData.append(p.getEstado().name());

                writer.write(préstamoData.toString());
                writer.newLine();
            }
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPréstamo(Préstamo préstamo)
    {
        List<Préstamo> préstamos = listarPréstamos();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRÉSTAMOS_FILE)))
        {
            for (Préstamo p : préstamos)
            {
                if (p.getId() != préstamo.getId())
                {
                    StringBuilder préstamoData = new StringBuilder();
                    préstamoData.append(p.getId()).append(",");
                    préstamoData.append(p.getUsuario().getId()).append(",");
                    for (Recurso recurso : p.getRecursos()) {
                        préstamoData.append(recurso.getId()).append(";");
                    }
                    préstamoData.deleteCharAt(préstamoData.length() - 1); // Eliminar último punto y coma (;)
                    préstamoData.append(",");
                    préstamoData.append(p.getFechaRegistro()).append(",");
                    préstamoData.append(p.getFechaLimiteDevolucion()).append(",");
                    préstamoData.append(p.getFechaDevolucion()).append(",");
                    préstamoData.append(p.getEstado().name());

                    writer.write(préstamoData.toString());
                    writer.newLine();
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<Préstamo> listarPréstamos()
    {
        List<Préstamo> préstamos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(PRÉSTAMOS_FILE)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] data = line.split(",");
                int préstamoId = Integer.parseInt(data[0]);
                int usuarioId = Integer.parseInt(data[1]);
                String[] recursosIds = data[2].split(";");
                List<Recurso> recursos = new ArrayList<>();
                for (String recursoId : recursosIds) {
                    recursos.add(new RecursoDAOImpl().obtenerRecurso(Integer.parseInt(recursoId)));
                }
                String fechaRegistro = data[3];
                String fechaLimiteDevolucion = data[4];
                String fechaDevolucion = data[5];
                EstadoPréstamo estado = EstadoPréstamo.valueOf(data[6]);

                Usuario usuario = new UsuarioDAOImpl().obtenerUsuario(usuarioId);
                Préstamo préstamo = new Préstamo(préstamoId, usuario, recursos, fechaRegistro, fechaLimiteDevolucion, fechaDevolucion, estado);
                préstamos.add(préstamo);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        return préstamos;
    }

    @Override
    public Préstamo obtenerPréstamo(int préstamoId)
    {
        List<Préstamo> préstamos = listarPréstamos();

        for (Préstamo préstamo : préstamos) 
        {
            if (préstamo.getId() == préstamoId) 
            {
                return préstamo;
            }
        }

        return null;
    }
}





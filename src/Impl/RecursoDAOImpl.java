/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Impl;

/**
 * @author Hanner Fernando Sinisterra <hanner.sinisterra@correounivalle.edu.co>
 * @author Daniel Esteban Gallego Velasco <gallego.daniel@correounivalle.edu.co>
 */

import Dao.RecursoDAO;
import Entidades.Recurso;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecursoDAOImpl implements RecursoDAO
{
    private static final String RECURSOS_FILE = "recursos.txt";

    @Override
    public void guardarRecurso(Recurso recurso)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECURSOS_FILE, true))) 
        {
            String recursoData = recurso.getId() + "," + recurso.getNombre() + "," + recurso.getTipo() + "," + recurso.isDevuelto();
            writer.write(recursoData);
            writer.newLine();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarRecurso(Recurso recurso) 
    {
        List<Recurso> recursos = listarRecursos();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECURSOS_FILE)))
        {
            for (Recurso r : recursos) 
            {
                if (r.getId() == recurso.getId()) 
                {
                    r.setNombre(recurso.getNombre());
                    r.setTipo(recurso.getTipo());
                    r.setDevuelto(recurso.isDevuelto());
                }
                String recursoData = r.getId() + "," + r.getNombre() + "," + r.getTipo() + "," + r.isDevuelto();
                writer.write(recursoData);
                writer.newLine();
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarRecurso(Recurso recurso)
    {
        List<Recurso> recursos = listarRecursos();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECURSOS_FILE)))
        {
            for (Recurso r : recursos) 
            {
                if (r.getId() != recurso.getId()) 
                {
                    String recursoData = r.getId() + "," + r.getNombre() + "," + r.getTipo() + "," + r.isDevuelto();
                    writer.write(recursoData);
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
    public List<Recurso> listarRecursos()
    {
        List<Recurso> recursos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(RECURSOS_FILE)))
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] data = line.split(",");
                int recursoId = Integer.parseInt(data[0]);
                String nombre = data[1];
                String tipo = data[2];
                boolean devuelto = Boolean.parseBoolean(data[3]);

                Recurso recurso = new Recurso(recursoId, nombre, tipo, devuelto);
                recursos.add(recurso);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        return recursos;
    }

    @Override
    public Recurso obtenerRecurso(int recursoId)
    {
        List<Recurso> recursos = listarRecursos();

        for (Recurso recurso : recursos)
        {
            if (recurso.getId() == recursoId)
            {
                return recurso;
            }
        }

        return null;
    }
}
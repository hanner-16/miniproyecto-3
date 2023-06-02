/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 * @author Hanner Fernando Sinisterra <hanner.sinisterra@correounivalle.edu.co>
 * @author Daniel Esteban Gallego Velasco <gallego.daniel@correounivalle.edu.co>
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Recurso 
{
    private int id;
    private String nombre;
    private String tipo;
    private boolean devuelto;

    private static final String RECURSOS_FILE = "recursos.txt";

    public Recurso(int id, String nombre, String tipo) 
    {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.devuelto = false;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getTipo() 
    {
        return tipo;
    }
    
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
    
    public String getNombre() 
    {
        return nombre;
    }
    
    public void setNombre(String titulo) 
    {
        this.nombre = titulo;
    }
    
     // Operaciones CRUD
    public void guardarRecurso()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECURSOS_FILE, true)))
        {
            String recursoData = id + "," + nombre + "," + tipo + "," + devuelto;
            writer.write(recursoData);
            writer.newLine();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void actualizarRecurso() 
    {
        List<Recurso> recursos = listarRecursos();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECURSOS_FILE)))
        {
            for (Recurso recurso : recursos) 
            {
                if (recurso.getId() == id) 
                {
                    recurso.setNombre(nombre);
                    recurso.setTipo(tipo);
                    recurso.setDevuelto(devuelto);
                }
                String recursoData = recurso.getId() + "," + recurso.getNombre() +
                        "," + recurso.getTipo() + "," + recurso.isDevuelto();
                writer.write(recursoData);
                writer.newLine();
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void eliminarRecurso() 
    {
        List<Recurso> recursos = listarRecursos();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECURSOS_FILE)))
        {
            for (Recurso recurso : recursos) 
            {
                if (recurso.getId() != id) 
                {
                    String recursoData = recurso.getId() + "," + recurso.getNombre() +
                            "," + recurso.getTipo() + "," + recurso.isDevuelto();
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

    public static List<Recurso> listarRecursos() 
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

                Recurso recurso = new Recurso(recursoId, nombre, tipo);
                recurso.setDevuelto(devuelto);
                recursos.add(recurso);
            }
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return recursos;
    }

    public static Recurso obtenerRecurso(int recursoId) 
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

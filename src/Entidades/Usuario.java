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

public class Usuario {
    private int id;
    private String nombre;
    private String tipo; // estudiante, empleado, profesor

    private static final String USUARIOS_FILE = "usuarios.txt";

    public Usuario(int id, String nombre, String tipo) 
    {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public String getTipo()
    {
        return tipo;
    }
    
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    // Operaciones CRUD
    public static List<Usuario> listarUsuarios()
    {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(USUARIOS_FILE)))
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String nombre = data[1];
                String tipo = data[2];
                Usuario usuario = new Usuario(id, nombre, tipo);
                usuarios.add(usuario);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        return usuarios;
    }

    public static Usuario obtenerUsuario(int id) 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(USUARIOS_FILE)))
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] data = line.split(",");
                int userId = Integer.parseInt(data[0]);
                if (userId == id)
                {
                    String nombre = data[1];
                    String tipo = data[2];
                    return new Usuario(id, nombre, tipo);
                }
            }
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void guardarUsuario()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USUARIOS_FILE, true)))
        {
            String usuarioData = id + "," + nombre + "," + tipo;
            writer.write(usuarioData);
            writer.newLine();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void actualizarUsuario() 
    {
        List<Usuario> usuarios = listarUsuarios();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USUARIOS_FILE)))
        {
            for (Usuario usuario : usuarios)
            {
                if (usuario.getId() == id)
                {
                    usuario.setNombre(nombre);
                    usuario.setTipo(tipo);
                }
                String usuarioData = usuario.getId() + "," + usuario.getNombre() + "," + usuario.getTipo();
                writer.write(usuarioData);
                writer.newLine();
            }
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void eliminarUsuario()
    {
        List<Usuario> usuarios = listarUsuarios();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USUARIOS_FILE)))
        {
            for (Usuario usuario : usuarios) 
            {
                if (usuario.getId() != id) 
                {
                    String usuarioData = usuario.getId() + "," + usuario.getNombre() + "," + usuario.getTipo();
                    writer.write(usuarioData);
                    writer.newLine();
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void solicitarPréstamo(List<Recurso> recursos) 
    {
        // Crear un nuevo préstamo para el usuario
        int nuevoIdPréstamo = generarNuevoIdPréstamo();
        Préstamo préstamo = new Préstamo(nuevoIdPréstamo, this, new Date(), calcularFechaLímiteDevolución());

        // Agregar los recursos al préstamo
        préstamo.agregarRecursos(recursos);

        // Guardar el préstamo en el archivo de préstamos
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRÉSTAMOS_FILE, true)))
        {
            String préstamoData = préstamo.getId() + "," + préstamo.getUsuario().getId() + "," +
                    préstamo.getFechaRegistro().getTime() + "," + préstamo.getFechaLímiteDevolución().getTime() +
                    "," + préstamo.getEstado();
            writer.write(préstamoData);
            writer.newLine();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void devolverRecursos(List<Recurso> recursos)
    {
        // Obtener los préstamos activos del usuario
        List<Préstamo> préstamosActivos = obtenerPréstamosActivos();

        // Recorrer los préstamos activos y buscar aquellos que contengan los recursos dados
        for (Préstamo préstamo : préstamosActivos) 
        {
            List<Recurso> recursosPréstamo = préstamo.getRecursos();

            for (Recurso recurso : recursos)
            {
                if (recursosPréstamo.contains(recurso))
                {
                    // Establecer la fecha de devolución del recurso en el préstamo
                    préstamo.setFechaDevoluciónRecurso(recurso, new Date());
                }
            }

            // Verificar si todos los recursos del préstamo han sido devueltos
            if (préstamo.estaCompletamenteCerrado()) 
            {
                // Actualizar el estado del préstamo
                préstamo.setEstado("completamente cerrado");
            } 
            else if (préstamo.estaParcialmenteCerrado()) 
            {
                // Actualizar el estado del préstamo
                préstamo.setEstado("parcialmente cerrado");
            }
        }

        // Actualizar el archivo de préstamos con los cambios
        actualizarArchivoPréstamos(préstamosActivos);
    }

    public List<Préstamo> obtenerPréstamosActivos() 
    {
        List<Préstamo> préstamosActivos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(PRÉSTAMOS_FILE))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] data = line.split(",");
                int préstamoId = Integer.parseInt(data[0]);
                int usuarioId = Integer.parseInt(data[1]);
                long fechaRegistro = Long.parseLong(data[2]);
                long fechaLímiteDevolución = Long.parseLong(data[3]);
                String estado = data[4];

                // Verificar si el préstamo pertenece al usuario actual y si está activo
                if (usuarioId == this.id && !estado.equals("completamente cerrado")) 
                {
                    Usuario usuario = obtenerUsuario(usuarioId);
                    Date fechaRegistroObj = new Date(fechaRegistro);
                    Date fechaLímiteDevoluciónObj = new Date(fechaLímiteDevolución);
                    Préstamo préstamo = new Préstamo(préstamoId, usuario, fechaRegistroObj, fechaLímiteDevoluciónObj);
                    préstamosActivos.add(préstamo);
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        return préstamosActivos;
    }

}
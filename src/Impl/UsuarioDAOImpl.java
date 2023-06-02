/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Impl;

/**
 * @author Hanner Fernando Sinisterra <hanner.sinisterra@correounivalle.edu.co>
 * @author Daniel Esteban Gallego Velasco <gallego.daniel@correounivalle.edu.co>
 */

import Dao.UsuarioDAO;
import Entidades.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO 
{
    private static final String USUARIOS_FILE = "usuarios.txt";

    @Override
    public void guardarUsuario(Usuario usuario)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USUARIOS_FILE, true)))
        {
            String usuarioData = usuario.getId() + "," + usuario.getNombre() + "," + usuario.getTipo();
            writer.write(usuarioData);
            writer.newLine();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario)
    {
        List<Usuario> usuarios = listarUsuarios();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USUARIOS_FILE))) 
        {
            for (Usuario u : usuarios) 
            {
                if (u.getId() == usuario.getId()) 
                {
                    u.setNombre(usuario.getNombre());
                    u.setTipo(usuario.getTipo());
                }
                String usuarioData = u.getId() + "," + u.getNombre() + "," + u.getTipo();
                writer.write(usuarioData);
                writer.newLine();
            }
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarUsuario(Usuario usuario) 
    {
        List<Usuario> usuarios = listarUsuarios();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USUARIOS_FILE))) 
        {
            for (Usuario u : usuarios)
            {
                if (u.getId() != usuario.getId())
                {
                    String usuarioData = u.getId() + "," + u.getNombre() + "," + u.getTipo();
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

    @Override
    public List<Usuario> listarUsuarios() 
    {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(USUARIOS_FILE))) 
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] data = line.split(",");
                int usuarioId = Integer.parseInt(data[0]);
                String nombre = data[1];
                String tipo = data[2];

                Usuario usuario = new Usuario(usuarioId, nombre, tipo);
                usuarios.add(usuario);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return usuarios;
    }

    @Override
    public Usuario obtenerUsuario(int usuarioId)
    {
        List<Usuario> usuarios = listarUsuarios();

        for (Usuario usuario : usuarios) 
        {
            if (usuario.getId() == usuarioId) 
            {
                return usuario;
            }
        }

        return null;
    }
}
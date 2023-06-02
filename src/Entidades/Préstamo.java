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
import java.util.Date;
import java.util.List;

public class Préstamo {
    private int id;
    private Usuario usuario;
    private Date fechaRegistro;
    private Date fechaLímiteDevolución;
    private String estado;
    private List<Recurso> recursos;

    private static final String PRÉSTAMOS_FILE = "préstamos.txt";

    public Préstamo(int id, Usuario usuario, Date fechaRegistro, Date fechaLímiteDevolución) {
        this.id = id;
        this.usuario = usuario;
        this.fechaRegistro = fechaRegistro;
        this.fechaLímiteDevolución = fechaLímiteDevolución;
        this.estado = "abierto";
        this.recursos = new ArrayList<>();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Recurso> getRecursos() {
        return recursos;
    }
    
    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public Date getFechaLímiteDevolución() {
        return fechaLímiteDevolución;
    }
    
    public void setFechaLímiteDevolución(Date fechaLímiteDevolución) {
        this.fechaLímiteDevolución = fechaLímiteDevolución;
    }
    
    public Date getFechaDevolución() {
        return fechaDevolución;
    }
    
    public void setFechaDevolución(Date fechaDevolución) {
        this.fechaDevolución = fechaDevolución;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    // Métodos para agregar y devolver recursos
    public void agregarRecurso(Recurso recurso) {
        recursos.add(recurso);
    }
    
    public void devolverRecurso(Recurso recurso) {
        recursos.remove(recurso);
    }
    
    public void actualizarPréstamo() {
        List<Préstamo> préstamos = listarPréstamos();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRÉSTAMOS_FILE))) {
            for (Préstamo préstamo : préstamos) {
                if (préstamo.getId() == id) {
                    préstamo.setFechaRegistro(fechaRegistro);
                    préstamo.setFechaLímiteDevolución(fechaLímiteDevolución);
                    préstamo.setEstado(estado);
                }
                String préstamoData = préstamo.getId() + "," + préstamo.getUsuario().getId() +
                        "," + préstamo.getFechaRegistro().getTime() + "," +
                        préstamo.getFechaLímiteDevolución().getTime() + "," + préstamo.getEstado();
                writer.write(préstamoData);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPréstamo() {
        List<Préstamo> préstamos = listarPréstamos();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRÉSTAMOS_FILE))) {
            for (Préstamo préstamo : préstamos) {
                if (préstamo.getId() != id) {
                    String préstamoData = préstamo.getId() + "," + préstamo.getUsuario().getId() +
                            "," + préstamo.getFechaRegistro().getTime() + "," +
                            préstamo.getFechaLímiteDevolución().getTime() + "," + préstamo.getEstado();
                    writer.write(préstamoData);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Préstamo> listarPréstamos() {
        List<Préstamo> préstamos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(PRÉSTAMOS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int préstamoId = Integer.parseInt(data[0]);
                int usuarioId = Integer.parseInt(data[1]);
                long fechaRegistro = Long.parseLong(data[2]);
                long fechaLímiteDevolución = Long.parseLong(data[3]);
                String estado = data[4];

                Usuario usuario = obtenerUsuario(usuarioId);
                Date fechaRegistroObj = new Date(fechaRegistro);
                Date fechaLímiteDevoluciónObj = new Date(fechaLímiteDevolución);
                Préstamo préstamo = new Préstamo(préstamoId, usuario, fechaRegistroObj, fechaLímiteDevoluciónObj);
                préstamo.setEstado(estado);
                préstamos.add(préstamo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return préstamos;
    }

    public static Préstamo obtenerPréstamo(int préstamoId) {
        List<Préstamo> préstamos = listarPréstamos();

        for (Préstamo préstamo : préstamos) {
            if (préstamo.getId() == préstamoId) {
                return préstamo;
            }
        }

        return null;
    }

    // Otros métodos útiles
    public boolean estaCompletamenteCerrado() {
        for (Recurso recurso : recursos) {
            if (!recurso.estaDevuelto()) {
                return false;
            }
        }
        return true;
    }

    public boolean estaParcialmenteCerrado() {
        for (Recurso recurso : recursos) {
            if (recurso.estaDevuelto()) {
                return true;
            }
        }
        return false;
    }
}
                                
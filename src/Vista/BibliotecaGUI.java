/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 * @author Hanner Fernando Sinisterra <hanner.sinisterra@correounivalle.edu.co>
 * @author Daniel Esteban Gallego Velasco <gallego.daniel@correounivalle.edu.co>
 */

import controlador.BibliotecaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BibliotecaGUI extends JFrame {
    private BibliotecaController controller;
    private JTextField txtNombreUsuario;
    private JTextField txtTipoUsuario;
    private JTextArea txtAreaUsuarios;

    public BibliotecaGUI() {
        controller = new BibliotecaController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Biblioteca App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(3, 2, 5, 5));

        JLabel lblNombreUsuario = new JLabel("Nombre:");
        panelForm.add(lblNombreUsuario);

        txtNombreUsuario = new JTextField();
        panelForm.add(txtNombreUsuario);

        JLabel lblTipoUsuario = new JLabel("Tipo:");
        panelForm.add(lblTipoUsuario);

        txtTipoUsuario = new JTextField();
        panelForm.add(txtTipoUsuario);

        JButton btnAgregarUsuario = new JButton("Agregar Usuario");
        btnAgregarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Obtener los valores de los campos de texto
            int usuarioId = Integer.parseInt(txtUsuarioId.getText());
            String nombre = txtNombreUsuario.getText();
            String tipo = txtTipoUsuario.getText();

           // Llamar al método agregarUsuario del controlador
           bibliotecaController.agregarUsuario(usuarioId, nombre, tipo);
           }
        });
        panelForm.add(btnAgregarUsuario);

        panel.add(panelForm, BorderLayout.NORTH);

        JButton btnListarUsuarios = new JButton("Listar Usuarios");
        btnListarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarUsuarios();
            }
        });
        panel.add(btnListarUsuarios, BorderLayout.CENTER);

        txtAreaUsuarios = new JTextArea();
        txtAreaUsuarios.setEditable(false);
        panel.add(new JScrollPane(txtAreaUsuarios), BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = controller.listarUsuarios();
        StringBuilder sb = new StringBuilder();
        for (Usuario usuario : usuarios) {
            sb.append("ID: ").append(usuario.getId()).append("\n");
            sb.append("Nombre: ").append(usuario.getNombre()).append("\n");
            sb.append("Tipo: ").append(usuario.getTipo()).append("\n\n");
        }
        txtAreaUsuarios.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BibliotecaGUI();
            }
        });
    }
}
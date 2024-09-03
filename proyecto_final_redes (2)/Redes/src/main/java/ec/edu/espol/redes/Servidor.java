/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.redes;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
/**
 *
 * @author Allan
 */
public class Servidor {
    
    public void iniciarServidor() {
        int puerto = 65433; // Puerto de conexión del servidor
        try (ServerSocket servidor = new ServerSocket(puerto);
             Socket canal = servidor.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(canal.getInputStream()));
             PrintWriter writer = new PrintWriter(new FileWriter("archivo_recibido.txt", true))) { // Abre el archivo en modo append

            System.out.println("Servidor conectado al canal");

            String segmento;
            while ((segmento = reader.readLine()) != null) {
                String[] partes = segmento.split(";");
                if (partes.length == 2) {
                    String contenido = partes[0];
                    String hashRecibido = partes[1];
                    String hashCalculado = calcularHash(contenido);

                    if (!hashRecibido.equals(hashCalculado)) {
                        String errorMsg = "Error de integridad: " + contenido;
                        System.out.println(errorMsg);
                        writer.println(errorMsg); // Escribir el error en el archivo
                    } else {
                        String successMsg = "Recibido correctamente: " + contenido;
                        System.out.println(successMsg);
                        writer.println(successMsg); // Escribir el segmento recibido correctamente en el archivo
                    }
                } else {
                    String formatError = "Segmento con formato incorrecto: " + segmento;
                    System.out.println(formatError);
                    writer.println(formatError); // Escribir errores de formato en el archivo
                }
            }

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Método para calcular el hash SHA-256 de un segmento
    private static String calcularHash(String segmento) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(segmento.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    
}

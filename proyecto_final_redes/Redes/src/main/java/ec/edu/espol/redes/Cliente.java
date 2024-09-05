/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.redes;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Allan
 */
public class Cliente {
    
    public String calcularHash(String segmento) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(segmento.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public  void enviarArchivo() {
        String host = "127.0.0.1";
        int puerto = 65432;

        try (Socket socket = new Socket(host, puerto);
             BufferedReader reader = new BufferedReader(new FileReader("Redes\\archivo.txt"));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                String hash = calcularHash(linea);
                String segmento = linea + "-" + hash;
                writer.print(segmento);
                writer.println(";");
                System.out.println("Enviado: " + segmento);
            }

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void iniciarCliente() {
        enviarArchivo();
    }


}

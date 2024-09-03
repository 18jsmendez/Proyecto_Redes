/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.redes;

import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Allan
 */
public class Canal {
    
    private static List<String> bufferPaquetes = new ArrayList<>();

    public String introducirErrores(String segmento) throws TransmissionException {
        Random random = new Random();

        // Simular pérdida de paquete
        if (random.nextDouble() < 0.1) {
            System.out.println("Paquete perdido");
            throw new TransmissionException("Paquete perdido");
        }

        // Simular alteración de bits
        if (random.nextDouble() < 0.1) {
            char[] chars = segmento.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (random.nextDouble() < 0.01) {
                    chars[i] = chars[i] == '0' ? '1' : '0';
                }
            }
            segmento = new String(chars);
            System.out.println("Paquete alterado: " + segmento);
        }

        // Simular desorden en los paquetes
        if (random.nextDouble() < 0.1) {
            bufferPaquetes.add(segmento);
            if (bufferPaquetes.size() > 1) {
                Collections.shuffle(bufferPaquetes);
                return bufferPaquetes.remove(0);
            }
            return null;
        }

        return segmento;
    }

    public  void enviarPaquetesPendientes(Socket servidor) throws IOException {
        while (!bufferPaquetes.isEmpty()) {
            String segmento = bufferPaquetes.remove(0);
            servidor.getOutputStream().write(segmento.getBytes());
            System.out.println("Enviando al servidor: " + segmento);
        }
    }

    public void iniciarCanal() {
        String host = "127.0.0.1";
        int puertoCliente = 65432;
        int puertoServidor = 65433;

        try (ServerSocket servidorCliente = new ServerSocket(puertoCliente);
             Socket cliente = servidorCliente.accept();
             Socket servidor = new Socket(host, puertoServidor)) {

            System.out.println("Canal conectado al cliente y al servidor");

            BufferedReader reader = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            String segmento;
            while ((segmento = reader.readLine()) != null) {
                try {
                    String segmentoModificado = introducirErrores(segmento);
                    if (segmentoModificado != null) {
                        servidor.getOutputStream().write(segmentoModificado.getBytes());
                    }
                } catch (TransmissionException e) {
                    System.out.println(e.getMessage());
                }
            }
            enviarPaquetesPendientes(servidor);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

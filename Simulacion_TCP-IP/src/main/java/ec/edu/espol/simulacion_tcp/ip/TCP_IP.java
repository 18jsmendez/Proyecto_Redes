/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.simulacion_tcp.ip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Allan
 */
public class TCP_IP {

    private Verificacion_numeros numero;
    private Verificacion_numeros texto;
    private String mensaje;
    private ArrayList<PC> pcs;
    private PC pcOrigen;
    private PC pcDestino;
    private ArrayList<String> segmentos;
    private ArrayList<String> segmentos_mul;
    private ArrayList<String> indices;
    private ArrayList<String> segmentos_modicados;

    private String paquete;
    private String trama;


    Scanner sc = new Scanner(System.in);

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ArrayList<String> getSegmentos() {
        return segmentos;
    }
    
    
    
    public TCP_IP() {
        this.numero = new Verificacion_numeros();
        this.texto = new Verificacion_numeros();
        this.pcs = new ArrayList<>();
        this.segmentos = new ArrayList<>();
        ArrayPcs();
               
    }

    public void ArrayPcs() {
        PC pc1 = new PC("192.168.0.102", "C8:69:CD:92:8B:8B");
        PC pc2 = new PC("192.168.0.103", "30:07:4D:61:2B:AF");
        PC pc3 = new PC("192.168.0.107", "8C:F5:A3:CF:FE:D4");

        this.pcs.add(pc1);
        this.pcs.add(pc2);
        this.pcs.add(pc3);
        
        this.pcOrigen = pc1;
        this.pcDestino = pc2;
        
    }

    public String getMensaje() {
        return mensaje;
    }
    
    
    public void receptarMensaje()
    {
        System.out.println("Ingrese el mensaje que desea enviar");
        this.mensaje = sc.nextLine();
        
    }
    
    public void creacionArchivo()
    {
        try( FileWriter f = new FileWriter("cliente.txt"))
        {
            f.write(this.getMensaje());
        }
        catch(IOException a )
        {
            System.out.println("");
        }
    }
    
    public void leerYsegementarArchivo()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("cliente.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("Procesando línea: " + linea);
                segmentacion(linea);  // Segmentar cada línea del archivo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void segmentacion(String mensaje) {
    System.out.println("Segmentando mensaje.........\n");
    int partes = mensaje.length() / 5;
    int indiceMensaje = 0;
    for (int i = 0; i <= partes; i++) {
        String parte = String.valueOf(i);
        int j = 0;
        while (j < 5 && indiceMensaje < mensaje.length()) {
            parte = parte + mensaje.charAt(indiceMensaje);
            j++;
            indiceMensaje++;
        }
        segmentos.add(parte);
    }

    for (String mensajeString : segmentos) {
        System.out.println(mensajeString);
    }
            
    System.out.println("\nMensaje segmentado correctamente\n");
}
    
    public void mulipLex() {
    Collections.shuffle(segmentos);
    for (String seg : segmentos) {
        System.out.println(seg);
        this.pcDestino.aggMensaje(seg);
        
    }
    System.out.println("Mensaje Multiplexado correctamente.");
    System.out.println("");
}
    
    
    public void CapaDeRed() {
    System.out.println("\nMensaje a enviar: " + mensaje);
    System.out.println("\nHost origen: " + pcOrigen.getAddressIP());
    System.out.println("\nHost destino: " + pcDestino.getAddressIP());
    paquete = pcOrigen.getAddressIP() + "/" + pcDestino.getAddressIP();
}
    
    public void capaDeEnlace() {
    System.out.println("\nMAC origen: " + pcOrigen.getAddressMAC());
    System.out.println("\nMAC destino: " + pcDestino.getAddressMAC());
    trama = pcOrigen.getAddressMAC() + "/" + pcDestino.getAddressMAC();
}

    
    public void Dispositivoswitch() {
    String ipDestino = "";
    String ipOrigen = "";
    String macOrigen = "";
    String macDestino = "";

    int bandera = 0;
    int bandera2 = 0;

    System.out.println("\nBuscando ip de origen y destino: \n");
    System.out.println("\nPaquete: " + paquete);

    for (int i = 0; i < paquete.length(); i++) {
        if (paquete.charAt(i) == '/') {
            bandera = 1;
            i++;
        }
        if (bandera == 0) {
            ipOrigen = ipOrigen + paquete.charAt(i);
        } else {
            ipDestino = ipDestino + paquete.charAt(i);
        }
    }
    
        System.out.println("IP Origen: " + ipOrigen);
        System.out.println("IP Destino: " + ipDestino);
        System.out.println("\nBuscando mac de origen y destino: \n");
        System.out.println("Trama: " + trama);
        
        for (int i = 0; i < trama.length(); i++) {
            if (trama.charAt(i) == '/') {
                bandera2 = 1;
                i++;
            }
            if (bandera2 == 0) {
                macOrigen = macOrigen + trama.charAt(i);
            } else {
                macDestino = macDestino + trama.charAt(i);
            }
        }
        
        System.out.println("\nEnlace realizado correctamente.\n");
        System.out.println("\nEnviando Mensaje al destino: ");

        ipDestino = ipDestino.substring(1);
        macDestino = macDestino.substring(1);

        System.out.println("\nIP Destino: " + ipDestino);
        System.out.println("\nMac Destino: " + macDestino);
    
        for (PC pc : pcs) {
            if (pc.getAddressIP().equals(ipDestino) && pc.getAddressMAC().equals(macDestino)) {
                for (String men : segmentos) {
                    pc.add(men);
                }
            }
        }
        System.out.println("Mensaje enviado correctamente =D ");

    }
    
    public void desencriptarMensaje() {
        System.out.println("");
        pcDestino.verMensajes();
        System.out.println("");
        System.out.println("Comenzando a desencriptar el mensaje......");
        pcDestino.mensajeRecibido();
        System.out.println("");
    }
    
    public void simularPerdidaDePaquetes(ArrayList<String> segmentos) {
        Random rand = new Random();
        boolean segmentoPerdido = false;

        for (int i = 0; i < segmentos.size(); i++) {
            String sg_N = segmentos.get(i);
            String indice = Character.toString(sg_N.charAt(0));
            this.pcDestino.setIndices(indice);

            if (rand.nextDouble() < 0.2) { // 20% de probabilidad de pérdida
                System.out.println("Simulando pérdida del segmento: " + sg_N);
                segmentos.set(i, null); // Marcar como perdido
                this.pcDestino.setSegmentos_modicados(null);
                segmentoPerdido = true;
            } else {
                // Si no se pierde, aún se debe agregar el segmento original
                this.pcDestino.setSegmentos_modicados(segmentos.get(i));
            }
        }

        // Si ningún segmento se perdió, forzamos la pérdida de al menos uno
        if (!segmentoPerdido) {
            int i = rand.nextInt(segmentos.size());
            String sg_N = segmentos.get(i);
            String indice = Character.toString(sg_N.charAt(0));
            this.pcDestino.setIndices(indice);
            System.out.println("Forzando la pérdida del segmento: " + sg_N);

            segmentos.set(i, null);
            this.pcDestino.setSegmentos_modicados(null);
        }
    }

    public void simularCambiosDeBits(ArrayList<String> segmentos) {
    Random rand = new Random();

    for (int i = 0; i < segmentos.size(); i++) {
        if (segmentos.get(i) != null) {
            String sg_N = segmentos.get(i);
            String indice = Character.toString(sg_N.charAt(0));
            this.pcDestino.setIndices(indice);

            if (rand.nextDouble() < 0.3) { // 30% de probabilidad de error
                System.out.println("Simulando error en el segmento: " + sg_N);
                String segmentoConError = modificarBits(segmentos.get(i));
                segmentos.set(i, segmentoConError);
                this.pcDestino.setSegmentos_modicados(segmentoConError);
                System.out.println("Segmento modificado: " + segmentoConError);
            } else {
                // Solo agregar el segmento original si no se modifica
                this.pcDestino.setSegmentos_modicados(segmentos.get(i));
            }
        }
    }
}

    
    
    private String modificarBits(String segmento) {
    char[] chars = segmento.toCharArray();
    Random rand = new Random();
    
    // Cambiar varios caracteres para asegurarnos de que el cambio sea visible
    for (int i = 0; i < chars.length; i++) {
        if (rand.nextDouble() < 0.5) {  // 50% de probabilidad de cambiar cada carácter
            chars[i] = (char)(rand.nextInt(26) + 'A');  // Reemplazar con una letra aleatoria
        }
    }
    
    return new String(chars);
}


    public void verificarYOrdenarSegmentos(ArrayList<String> segmentos, String mensajeOriginal) {
        Collections.sort(segmentos, (a, b) -> {
            if (a == null) return 1;  // Los segmentos perdidos van al final
            if (b == null) return -1;
            return a.compareTo(b);
        });

        try (FileWriter writer = new FileWriter("verificacion_segmentos.txt")) {
            writer.write("Resultado de la verificación de segmentos:\n");
            for (int i = 0; i < segmentos.size(); i++) {
                if (segmentos.get(i) == null) {
                    writer.write("Segmento " + i + " perdido.\n");
                    System.out.println("Segmento " + i + " perdido.");
                } else if (!segmentos.get(i).equals(mensajeOriginal.substring(i * 5, Math.min((i + 1) * 5, mensajeOriginal.length())))) {
                    writer.write("Segmento " + i + " modificado: " + segmentos.get(i) + "\n");
                    System.out.println("Segmento " + i + " modificado: " + segmentos.get(i));
                } else {
                    writer.write("Segmento " + i + " correcto.\n");
                    System.out.println("Segmento " + i + " correcto.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarM_aleatoriamente()
    {
        System.out.println("");
        System.out.println("Multiplexando mensajes.........");
        mulipLex();
    }
    public void enviarMensaje() {
        
        System.out.println("");
        CapaDeRed();
        System.out.println("");
        capaDeEnlace();
        System.out.println("Enviado datos al switch..........");
        Dispositivoswitch();
        System.out.println("");
        System.out.println("Desencriptando mensajes recividos");
        desencriptarMensaje();
        System.out.println("Mensaje desencriptado correctamente");
        System.out.println("Host Origen: " + pcOrigen.getAddressIP());
        System.out.println("");
        this.setMensaje("");
        this.pcDestino.vaciarList();
    }

}

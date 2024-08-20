/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.simulacion_tcp.ip;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 *
 * @author Allan
 */
public class PC {
    
    private String AddressIP;
    private String AddressMAC;
    private ArrayList<String> mensaje;
    private Verificacion_numeros n ;
    private ArrayList<String> mensajeOrdenado;
    
    private ArrayList<String> segmentos_mul;
    private ArrayList<String> indices;
    private ArrayList<String> segmentos_modicados;

    public ArrayList<String> getIndices() {
        return indices;
    }

    public ArrayList<String> getSegmentos_modicados() {
        return segmentos_modicados;
    }

    public void setMensaje(ArrayList<String> mensaje) {
        this.mensaje = mensaje;
    }

    public void setMensajeOrdenado(ArrayList<String> mensajeOrdenado) {
        this.mensajeOrdenado = mensajeOrdenado;
    }

    public void setSegmentos_mul(String segmentos_mul) {
        this.segmentos_mul.add(segmentos_mul);
    }

    public void setIndices(String indices) {
        this.indices.add(indices);
    }

    public void setSegmentos_modicados(String s ) {
        this.segmentos_modicados.add(s);
    }

    

    public PC(String ip, String mac) {
        this.AddressIP = ip;
        this.AddressMAC = mac;
        this.mensaje = new ArrayList<>();
        this.mensajeOrdenado = new ArrayList<>();
        this.indices = new ArrayList<>();
        this.segmentos_modicados = new ArrayList<>();
        this.n = new Verificacion_numeros();
        
    }

    
    
    public void vaciarList()
    {
        this.mensaje.clear();
        this.mensajeOrdenado.clear();
    }

    public ArrayList<String> getMensaje() {
        return mensaje;
    }

    public ArrayList<String> getMensajeOrdenado() {
        return mensajeOrdenado;
    }
    
    
    
    public void aggMensaje(String s)
    {
        this.mensaje.add(s);
    }
    public String getAddressIP() {
        return AddressIP;
    }

    public String getAddressMAC() {
        return AddressMAC;
    }
    
    public int size() {
    return mensaje.size();
}

    public String get(int index) {
        return mensaje.get(index);
    }

    public boolean add(String e) {
        return mensaje.add(e);
    }

    public String remove(int index) {
        return mensaje.remove(index);
    }

    public void verMensajes() {
        System.out.println("\n");
        System.out.println("Mensajes disponibles: \n");

        for (String string : mensaje) {
            System.out.println(string);
        }

        System.out.println("\n");
        
       
        
}
   

    public void mensajeRecibido() 
    {
        
        for (String string : mensaje) {
            mensajeOrdenado.add(string.substring(1));
        }

            Stream.iterate(0, n -> n + 1)
      .limit(Math.min(this.indices.size(), this.segmentos_modicados.size()))
      .forEach(i -> {
          int elemento1 = Integer.parseInt(this.indices.get(i));
          String elemento2 = this.segmentos_modicados.get(i);
          
        if ( (elemento2 != null) && (n.esEntero(elemento2))) {
            
            String subCadena = elemento2.substring(1);
            mensajeOrdenado.set(elemento1, subCadena);
        }        
        else {
           mensajeOrdenado.set(elemento1, elemento2);
           
        }

      });
            
        String mensajeFinal = "";
        for (String mordenado : mensajeOrdenado) {
            mensajeFinal = mensajeFinal + mordenado;
        }

        System.out.println("Mensaje recibido: " + mensajeFinal);
    }
    

    public String set(int index, String element) {
        return mensaje.set(index, element);
    }
    
}

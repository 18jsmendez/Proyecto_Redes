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
public class Canal {
    
    private List<String> bufferPaquetes = new ArrayList<>();

    public List<String> introducirErrores(List<String> bufferPaquetes) throws TransmissionException {
        Random random = new Random();
        double porcentajeError = random.nextDouble();
        System.out.println("\nvalor de error: " + porcentajeError);

        List<String> bufferPaquetesDañados = new ArrayList<>(bufferPaquetes);//lista que se va a retornar


        int indiceAleatorio = random.nextInt(bufferPaquetes.size());
        String elementoDañado = bufferPaquetes.get(indiceAleatorio);//elemento a perder o alterar
        
        
        // Simular pérdida de paquete
        if (porcentajeError >= 0.0 && porcentajeError < 0.3) {
            System.out.println("Se perderá un paquete!!\n");
            //mostrar elemento a perder
            System.out.println("paquete a perder: "+elementoDañado);

            List<String> listaSinPaquetePerdido = perderPaquete(bufferPaquetesDañados, elementoDañado);
            bufferPaquetesDañados = listaSinPaquetePerdido;
            return bufferPaquetesDañados;
            //throw new TransmissionException("Paquete perdido");
        }

        // Simular alteración de bits
        if (porcentajeError >= 0.3 && porcentajeError < 0.6) {

            System.out.println("Se alterará la integridad de un paquete!!\n");
            String[] partes = elementoDañado.strip().split("-");
            
            try{
            String contenidoAalterar = partes[0];
            byte[] bytesContenidoAalterar = contenidoAalterar.getBytes();
            String hashContenidoAalterar = calcularHash(contenidoAalterar);

            /*System.out.println("bytes!!!!");
                for (byte b : bytesContenidoAalterar) {
                                System.out.println(b + " ");
                            }
            */
            String segmentoConHashBueno = contenidoAalterar+"-"+hashContenidoAalterar;
            System.out.println("paquete a Alterar: "+segmentoConHashBueno);

            
            //indice aleatorio a dañar
            int randomIndex = random.nextInt(bytesContenidoAalterar.length);
            //alterando el contenido aleatoriamente
            bytesContenidoAalterar[randomIndex] = (byte) random.nextInt(256);
            
            //String del segmento con BytesAlterado
            String contenidoAlterado = new String(bytesContenidoAalterar);
            //generar hash del paquete alterado
            String hashAlterado = calcularHash(contenidoAlterado);

            //mostrar y crear paquete alterado con hash
            String segmentoConHashDañado = contenidoAlterado+"-"+hashAlterado;
            System.out.println("Paquete alterado: " + segmentoConHashDañado);


            bufferPaquetesDañados.set(indiceAleatorio,segmentoConHashDañado);

            }catch(NoSuchAlgorithmException e){
                e.printStackTrace();
            }
        }

        // Simular desorden en los paquetes
        if (porcentajeError >= 0.6 && porcentajeError < 0.9) {
            System.out.println("Se cambiará el orden de los paquetes!!\n");
             
            if (bufferPaquetesDañados.size() > 1) {
                bufferPaquetesDañados = desordenarLista(bufferPaquetesDañados);
                /*System.out.println("segmentos ordenados "+ bufferPaquetes);
                System.out.println("segmentos desordenados: "+bufferPaquetesDañados);*/
                return bufferPaquetesDañados;
            }
        }

        return bufferPaquetesDañados;
    }


    public void iniciarCanal() {
        String host = "127.0.0.1";
        int puertoCliente = 65432;
        int puertoServidor = 65433;

        try (ServerSocket servidorCliente = new ServerSocket(puertoCliente);
             Socket cliente = servidorCliente.accept();
             Socket servidor = new Socket(host, puertoServidor)) {

            System.out.println("\n-----------------Canal conectado al cliente y al servidor-----------------");

            BufferedReader reader = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            String segmento;
            while ((segmento = reader.readLine()) != null) {
                //System.out.println("BufferPaquetes antes de añadirle los segmentos: "+ this.bufferPaquetes); OJOOOOOOOOO
                this.bufferPaquetes.add(segmento);
                //System.out.println("BuferPaquetes antes de erronearlo: "+this.bufferPaquetes);   OJOOOOOOO
            }

            try{
            List<String> bufferDañadoParaServidor = introducirErrores(this.bufferPaquetes);
            for(String segmentoParaServidor:bufferDañadoParaServidor){
                if (segmentoParaServidor!= null) {
                        String segmentoParaServidorconComa = segmentoParaServidor+";";
                        servidor.getOutputStream().write(segmentoParaServidorconComa.getBytes());
                    }
            }
            }catch (TransmissionException e) {
                System.out.println(e.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> desordenarLista(List<String> lista) {
        List<String> listaDesordenada = new ArrayList<>(lista);
        Collections.shuffle(listaDesordenada);
        return listaDesordenada;
    }

    public List<String> getBufferPaquete(){
        return this.bufferPaquetes;
    }

    private String calcularHash(String segmento) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(segmento.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static List<String> perderPaquete(List<String> list, String element) {
        List<String> newList = new ArrayList<>(list);
        newList.remove(element);
        return newList;
    }

}

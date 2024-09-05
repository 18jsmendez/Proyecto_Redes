/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.redes;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author Allan
 */
public class Servidor {

    public static List<String> getListaComparar(){
        List<String> listaComparar = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("Redes\\archivo.txt"))){ 
            String linea;
            while ((linea = reader.readLine()) != null) {
                String hash = calcularHash(linea);
                String segmento = linea + "-" + hash + ";";
                listaComparar.add(segmento);
            } 
        }catch(IOException | NoSuchAlgorithmException e) {
            
        }
        return listaComparar;
    }


    public List<String> listaContenidosComparar(List<String> listaComparar){
        List<String> listaContenidos = new ArrayList<>();
        for(String segmentoComparar:listaComparar){
            if (!segmentoComparar.isEmpty()){
                String[] contenidoYhash = segmentoComparar.split("-");
                if (contenidoYhash.length == 2) {
                    listaContenidos.add(contenidoYhash[0]);
                }
            }
        }
        
        return listaContenidos;
    }


    public List<String> listaHashesComparar(List<String> listaComparar){
        List<String> listaContenidos = new ArrayList<>();
        for(String segmentoComparar:listaComparar){
            if (!segmentoComparar.isEmpty()){
                String[] contenidoYhash = segmentoComparar.split("-");
                if (contenidoYhash.length == 2) {
                    listaContenidos.add(contenidoYhash[1]);
                }
            }
        }
        
        return listaContenidos;
    }

    public void iniciarServidor() {
        int puerto = 65433; // Puerto de conexión del servidor
        try (ServerSocket servidor = new ServerSocket(puerto);
             Socket canal = servidor.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(canal.getInputStream()));
             PrintWriter writer = new PrintWriter(new FileWriter("archivo_recibido.txt", true))) { // Abre el archivo en modo append

            System.out.println("-----------------Servidor conectado al canal-----------------\n");

            String segmento;

            String[] partes = new String[0];;
            List<String> contenidos = new ArrayList<>();
            List<String> hashes = new ArrayList<>();


            while ((segmento = reader.readLine()) != null) {
                partes = segmento.strip().split(";");
            
            }
            //System.out.println("paquetes de un segmento: "+Arrays.toString(partes)); OJOOOOOOO
            for (String elemento : partes) {
                if (!elemento.isEmpty()) {
                    String[] contenidoYhash = elemento.split("-");
                    if (contenidoYhash.length == 2) {
                        contenidos.add(contenidoYhash[0]);
                        hashes.add(contenidoYhash[1]);
                    }
                }
            }

            //Muestra los paquetes recibidos
            System.out.println("\nPaquetes recibidos: "+ contenidos);

            List<String> listaComparar = getListaComparar();
            List<String> listaContenidosComparar = listaContenidosComparar(listaComparar);
            List<String> listaHashesComparar = listaHashesComparar(listaComparar);

            //Muestra los paquetes que se esperaban recibir
            System.out.println("Paquetes que se esperaban recibir: "+listaContenidosComparar+"\n");

            int datoComparador = compareLists(listaContenidosComparar, contenidos);
            switch (datoComparador) {
                case 0: //si son iguales, es decir no se alteró nada
                    System.out.println("Paquetes recibidos correctamente: ");
                    writer.println("Paquetes recibidos correctamente:");
                    for (int i = 0; i < contenidos.size(); i++){
                        String successMsg = contenidos.get(i);
                        System.out.println(successMsg);
                        
                        writer.println(successMsg); // Escribir el segmento recibido correctamente en el archivo
                    }
                    break;
                case 1: //si tienen los mismos elementos pero en otro orden, los paquetes llegaron desordenados
                    System.out.println("Segmento recibido con paquetes desordenados: ");
                    writer.println("Segmento recibido con paquetes desordenados:");
                    for (int i = 0; i < contenidos.size(); i++){
                        String desorden =  contenidos.get(i);
                        System.out.println(desorden);
                        writer.println(desorden);
                    }

                    break;

                case -2://son del mismo tamaño pero son diferentes contenidos, hubo un error de integridad
                    System.out.println("Error de integridad en los paquetes recibidos: ");
                    writer.println("Error de integridad en los paquetes recibidos: ");
                    for (int i = 0; i < contenidos.size(); i++){
                        String hashCalculado = calcularHash(listaContenidosComparar.get(i));
                        System.out.println("Hash proveniente: "+hashes.get(i));
                        System.out.println("Hash calculado original: "+hashCalculado);

                        String errorMsg =  contenidos.get(i);
                        System.out.println(errorMsg);
                        writer.println(errorMsg); // Escribir los elementos ya sea con error o no de integridad en el archivo
                    }

                    break;
                case 2: //no son del mismo tamaño, hubo una pérdida de paquetes
                    System.out.println("Pérdida de paquetes: "); 
                    writer.println("Pérdida de paquetes: "); 
                    for (int i = 0; i < contenidos.size(); i++){
                       String elementoNoPerdido = contenidos.get(i);
                       System.out.println(elementoNoPerdido);
                       writer.println(elementoNoPerdido);
                    }
                    List<String> paquetesPerdidos = paquetesPerdidos(listaContenidosComparar, contenidos);

                    //Mostrar los paquetes perdidos
                    System.out.println("\nLos paquetes que se han perdido son: ");
                    for(int i = 0; i < paquetesPerdidos.size(); i++){
                        System.out.println(paquetesPerdidos.get(i));
                    }
                    break;
                default:
                    break;
            }

            writer.println(" ");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }catch(IOException | NoSuchAlgorithmException e){
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

    public static int compareLists(List<String> list1, List<String> list2) {

        // Comparar si las listas no son del mismo tamaño
        if (list1.size() != list2.size()) {
            return 2;
        }

        // Comparar si las listas son iguales en orden
        if (list1.equals(list2)) {
            return 0;
        }

        // Comparar si las listas contienen los mismos elementos sin importar el orden
        List<String> sortedList1 = new ArrayList<>(list1);
        List<String> sortedList2 = new ArrayList<>(list2);
        Collections.sort(sortedList1);
        Collections.sort(sortedList2);

        if (sortedList1.equals(sortedList2)) {
            return 1;
        }

        // Si son del mismo tamaño pero tienen contenido diferente
        return -2;
    }

    public static List<String> paquetesPerdidos(List<String> list1, List<String> list2) {
        List<String> uniqueInList1 = list1.stream()
                                          .filter(element -> !list2.contains(element))
                                          .collect(Collectors.toList());

        List<String> uniqueInList2 = list2.stream()
                                          .filter(element -> !list1.contains(element))
                                          .collect(Collectors.toList());

        uniqueInList1.addAll(uniqueInList2);
        return uniqueInList1;
    }
    
}

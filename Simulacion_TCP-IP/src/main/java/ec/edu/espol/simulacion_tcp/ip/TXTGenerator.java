/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.simulacion_tcp.ip;

/**
 *
 * @author MendezJorge
 */
import java.util.*;
import java.io.*;

class TXTGenerator {
    
    public void recepcionData(){
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre del remitente: ");
        String nombre = sc.nextLine();
        System.out.print("Cédula del remitente: ");
        String cedula = sc.nextLine();
        
        //Datos de red
        System.out.print("Ingrese la cantidad de routers que desea instalar: ");
        int cantidadRouters = sc.nextInt();
        System.out.print("Ingrese la cantidad de interfaces por routers que desea: ");
        int cantidadInterfacesPorRouters = sc.nextInt();
        System.out.print("Ingrese la cantidad de departamentos que tendrá su red: ");
        int cantidadDepar = sc.nextInt();
        
        ArrayList<ArrayList> dicPCs = new ArrayList<>();
        for(int i = 0;i<cantidadDepar; i++){
            ArrayList<Integer> depPC = new ArrayList<>();
            System.out.println("    Ingrese la cantidad de PCs en el departamento "+i+" : ");
            int pcs = sc.nextInt();
            depPC.add(i);
            depPC.add(pcs);
            dicPCs.add(depPC);
        }
        
        System.out.println("ARCHIVO TXT GENERADO");
        
        try{
        FileWriter writer = new FileWriter("datos.txt",true);
        BufferedWriter bwriter = new BufferedWriter(writer);
        bwriter.write("Nombre del remitente: "+nombre);
        bwriter.write("");
        bwriter.write("Cédula del remitente: "+cedula);
        bwriter.write("");
        bwriter.write("Cantidad de Routers: "+cantidadRouters);
        bwriter.write("");
        bwriter.write("Cantidad de interfaces por routers: "+cantidadInterfacesPorRouters);
        bwriter.write("");
        bwriter.write("Cantidad de departamentos: "+ cantidadDepar);
        bwriter.write("");
        bwriter.write("PCs por cada departamento:");
        
        writer.close();
        }catch(IOException e){
        }
    }
    
}
	


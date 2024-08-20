/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.simulacion_tcp.ip;

import java.util.Scanner;
/**
 *
 * @author Allan
 */
public class Verificacion_numeros {

    public Verificacion_numeros() {
    }
    
    Scanner sc = new Scanner(System.in);
    
    public int verificacion_n1(String n)
    {
        int num = 0 ;
        while(true)
        {
            System.out.println(n);
            
            try {
                num = Integer.parseInt(sc.nextLine());
                
                return num;
            }
            catch(Exception a) {
                 System.out.println("Tipo de dato no valido");   
            }
        }
        
    }
    public int verificacion_n2(String mensaje, int minimo, int maximo) {
        
    while (true) {
        try {
            System.out.print(mensaje + " ");
            int numero = Integer.parseInt(sc.nextLine());
            if (minimo <= numero && numero <= maximo) {
                return numero;
            } else {
                System.out.printf("Valor ingresado no corresponde al intervalo [%d, %d]\n", minimo, maximo);
            }
        } catch (Exception e) {
            System.out.println("Error, vuelva a intentarlo.");
        }
    }

    }
    
    
    
    public boolean esEntero(String str) {
    try {
        
        String s = str.substring(1);
        Integer.parseInt(s);
        return true;  // Si la conversión es exitosa, es un entero
    } catch (NumberFormatException e) {
        return false;  // Si ocurre una excepción, no es un entero
    }
}

}
    
    

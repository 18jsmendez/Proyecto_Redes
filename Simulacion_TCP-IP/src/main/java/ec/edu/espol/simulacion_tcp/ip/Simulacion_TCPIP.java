/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.espol.simulacion_tcp.ip;

/**
 *
 * @author Allan
 */
public class Simulacion_TCPIP {

    public static void main(String[] args) {

        TXTGenerator txt = new TXTGenerator();
        txt.recepcionData();
        
        Servidor server = new Servidor();
        
        server.iniciar();
    }
}

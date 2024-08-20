/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.simulacion_tcp.ip;

/**
 *
 * @author Allan
 */
public class Servidor {
    
    private TCP_IP menu;

    public Servidor() {
    }

    public void iniciar() {
        menu = new TCP_IP();
        menu.receptarMensaje();
        menu.creacionArchivo();
        menu.leerYsegementarArchivo();
        menu.enviarM_aleatoriamente();
        
        menu.simularPerdidaDePaquetes(menu.getSegmentos());
        menu.simularCambiosDeBits(menu.getSegmentos());
        menu.verificarYOrdenarSegmentos(menu.getSegmentos(), menu.getMensaje());
        menu.enviarMensaje();
    }
}

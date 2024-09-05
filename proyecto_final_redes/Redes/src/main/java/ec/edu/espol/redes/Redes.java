/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.espol.redes;

/**
 *
 * @author Allan
 */
public class Redes {

    public static void main(String[] args) {
        // Crear y ejecutar el hilo del servidor
        Thread servidorThread = new Thread(() -> {
            Servidor servidor = new Servidor();
            servidor.iniciarServidor();
        });

        // Crear y ejecutar el hilo del canal
        Thread canalThread = new Thread(() -> {
            Canal canal = new Canal();
            canal.iniciarCanal();
        });

        // Crear y ejecutar el hilo del cliente
        Thread clienteThread = new Thread(() -> {
            Cliente cliente = new Cliente();
            cliente.iniciarCliente();
        });

        // Iniciar los hilos en el orden correcto
        servidorThread.start();
        try {
            Thread.sleep(1000);  // Esperar un segundo para asegurar que el servidor esté listo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        canalThread.start();
        try {
            Thread.sleep(1000);  // Esperar un segundo para que el canal esté listo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clienteThread.start();

        // Esperar a que todos los hilos terminen
        try {
            servidorThread.join();
            canalThread.join();
            clienteThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

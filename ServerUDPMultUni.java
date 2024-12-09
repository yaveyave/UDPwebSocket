/*import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ServerUDPMultUni {

    public static void main(String[] args) throws Exception {
        
        //MULTICAST
        //Enviar Mensajes MULTICAST
        final InetAddress mcGroupDir = InetAddress.getByName("224.0.0.1");
        final DatagramSocket udpSocket = new DatagramSocket();
        
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("\n << INICIANDO EL SERVIDOR UDP: MODO MULTICAST >>");
        
        System.out.println("ENVIA MULTICAST DESDE " + udpSocket.getLocalSocketAddress());

        while(true) {
            String enviar = teclado.nextLine();
            
            byte[] buffer = enviar.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, mcGroupDir, 9000);
            udpSocket.send(packet);
            
            if(enviar.equalsIgnoreCase("EXIT")) break;
        }
        teclado.close();
        udpSocket.close();                


        //UNICAST
        
        //RECIBIR Mensajes en UNICAST
        System.out.println("\n << INICIANDO EL SERVIDOR UDP: MODO UNICAST >>");
        
        //Crea el socket servidor
        DatagramSocket socket = new DatagramSocket(5000);
        System.out.println("El servidor se reserva el puerto: " + socket.getLocalPort());

        System.out.println("\n SERVIDOR UDP: RECEPCION DE MENSAJES UNICAST");
        
        while(true) {
            //Crea el buffer para guardar los datos que se reciban
            byte[] buffer = new byte[256];
            
            //Crea un datagrama con el buffer vacío para recibir los datos
            DatagramPacket packet = new DatagramPacket(buffer, 256);
            
            //Espera que llegue un datagrama y lo copia en el datagrama creado
            socket.receive(packet);
            
            //presenta la infomación recibida
            String carga = new String(packet.getData()).trim();
            System.out.println(
                String.format("Recibido: %s; desde: %s", carga, packet.getSocketAddress()));

            //si recibe el mensaje "EXIT" finaliza
            if(carga.equals("SALIR")) break;
        }
        //cierra el socket
        socket.close();

    }


}
*/

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ServerUDPMultUni {

    public static void main(String[] args) throws Exception {
        
        /*MULTICAST*/
        //Enviar Mensajes MULTICAST
        final InetAddress mcGroupDir = InetAddress.getByName("224.0.0.1");
        final DatagramSocket udpSocket = new DatagramSocket();
        
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("\n << INICIANDO EL SERVIDOR UDP: MODO MULTICAST >>");
        System.out.println("ENVIA MULTICAST DESDE " + udpSocket.getLocalSocketAddress());

        while(true) {
            System.out.print("Ingrese mensaje multicast (o 'EXIT' para terminar): ");
            String enviar = teclado.nextLine();

            byte[] buffer = enviar.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, mcGroupDir, 9000);
            udpSocket.send(packet);
            
            if(enviar.equalsIgnoreCase("EXIT")) break;
        }
        teclado.close();
        udpSocket.close();                


        /*UNICAST*/

        //RECIBIR Mensajes en UNICAST
        System.out.println("\n << INICIANDO EL SERVIDOR UDP: MODO UNICAST >>");
        
        //Crea el socket servidor
        DatagramSocket socket = new DatagramSocket(5000);
        System.out.println("El servidor se reserva el puerto: " + socket.getLocalPort()+ "...");

        System.out.println("\n SERVIDOR UDP: RECEPCION DE MENSAJES UNICAST");

        // Bucle principal de escucha para recibir mensajes unicast
        while(true) {
            // Crea el buffer para guardar los datos que se reciban
            byte[] buffer = new byte[256];
            
            // Crea un datagrama con el buffer vacío para recibir los datos
            DatagramPacket packet = new DatagramPacket(buffer, 256);
            
            // Espera que llegue un datagrama y lo copia en el datagrama creado
            socket.receive(packet);
            
            // Cada vez que se recibe un paquete, se crea un nuevo hilo para manejar ese mensaje
            new Thread(new ClienteHandler(socket, packet)).start();
        }
    }

    // Clase que maneja cada cliente en un hilo separado
    static class ClienteHandler implements Runnable {
        private DatagramSocket socket;
        private DatagramPacket packet;

        public ClienteHandler(DatagramSocket socket, DatagramPacket packet) {
            this.socket = socket;
            this.packet = packet;
        }

        @Override
        public void run() {
            try {
                // Procesa el mensaje recibido
                String carga = new String(packet.getData()).trim();
                System.out.println(String.format("Recibido: %s ---> desde: %s", carga, packet.getSocketAddress()));

                // Si recibe el mensaje "SALIR", termina la conexión con el cliente
                if(carga.equals("SALIR")) {
                    System.out.println("Cliente desconectado: " + packet.getSocketAddress());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

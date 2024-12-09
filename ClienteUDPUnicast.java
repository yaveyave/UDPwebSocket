/*import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class ClienteUDPUnicast {
    public static void main(String[] args) throws Exception {
        
        ///*MULTICAST CLIENTE 
        //Recibir mensajes MULTICAST
        final InetAddress mcGroupDir = InetAddress.getByName("224.0.0.1");
        final MulticastSocket mcSocket = new MulticastSocket(9000);
        
        mcSocket.joinGroup(mcGroupDir);

        System.out.println("\n << INICIANDO COMUNICACION MULTICAST EN EL SERVIDOR >>");
        System.out.println("RECIBE MULTICAST EN " + mcSocket.getLocalSocketAddress());
        
        while(true) {
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            mcSocket.receive(packet);
            
            String received = new String(packet.getData()).trim();
            System.out.println(
                String.format("Mensaje=%s; desde=%s", received, packet.getSocketAddress())
            );
            if(received.equalsIgnoreCase("EXIT")) break;
        }
        mcSocket.leaveGroup(mcGroupDir);
        mcSocket.close();



        ///*UNICAST CLIENTE 
        //Recibir  mensajes UNICAST
        System.out.println("\n << INICIANDO EL CLIENTE UDP: MODO UNICAST >>");

        //Crea el socket cliente
        DatagramSocket socket = new DatagramSocket();
        System.out.println("El cliente se reserva el puerto: " + socket.getLocalPort());
        
        System.out.println("\n CLIENTE UDP: ENVIO DE MENSAJES UNICAST");
        
        //Define la dirección(IP o nombre) del servidor
        InetAddress direccion = InetAddress.getByName("localhost");
        
        //Mensajes a enviar
        String[] mensajes = {"HOLA", "Primer mensaje", "Segundo mensaje", "CHAO", "SALIR"};

        for(String msj : mensajes) {
            //Convierte el valor a byte[]
            byte[] buffer = msj.getBytes();
            
            //Crea el datagrama a enviar con el valor, y la dirección y puerto del servidor
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, direccion, 5000);
            
            //envía el datagrama
            socket.send(packet);
            
            //presenta la información enviada
            System.out.println(
                String.format("Enviado: %s; hacia: %s", new String(packet.getData()), packet.getSocketAddress()));
            
            //Pausa el trabajo del hilo
            Thread.sleep(3000);
        }
        socket.close();
    }
}
    */

    import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class ClienteUDPUnicast {
    public static void main(String[] args) throws Exception {
        
        /*MULTICAST CLIENTE */
        //Recibir mensajes MULTICAST
        final InetAddress mcGroupDir = InetAddress.getByName("224.0.0.1");
        final MulticastSocket mcSocket = new MulticastSocket(9000);
        
        mcSocket.joinGroup(mcGroupDir);

        System.out.println("\n << INICIANDO COMUNICACION MULTICAST EN EL SERVIDOR >>");
        System.out.println("RECIBE MULTICAST EN " + mcSocket.getLocalSocketAddress());
        
        while(true) {
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            mcSocket.receive(packet);
            
            String received = new String(packet.getData()).trim();
            System.out.println(
                String.format("Mensaje=%s; desde=%s", received, packet.getSocketAddress())
            );
            if(received.equalsIgnoreCase("EXIT")) break;
        }
        mcSocket.leaveGroup(mcGroupDir);
        mcSocket.close();



        /*UNICAST CLIENTE */
        // Crea el socket cliente
        DatagramSocket socket = new DatagramSocket();
        System.out.println("El cliente se reserva el puerto: " + socket.getLocalPort());
        
        System.out.println("\n CLIENTE UDP: ENVIO DE MENSAJES UNICAST");
        
        // Define la dirección(IP o nombre) del servidor
        InetAddress direccion = InetAddress.getByName("localhost");
        
        // Crear un scanner para leer los mensajes del usuario
        Scanner scanner = new Scanner(System.in);

        String mensaje;
        while (true) {
            // Leer el mensaje del usuario
            System.out.print("Ingrese mensaje (o 'SALIR' para terminar): ");
            mensaje = scanner.nextLine();

            // Si el mensaje es "SALIR", se termina el ciclo
            if (mensaje.equalsIgnoreCase("SALIR")) {
                break;
            }

            // Convierte el valor a byte[]
            byte[] buffer = mensaje.getBytes();
            
            // Crea el datagrama a enviar con el valor, y la dirección y puerto del servidor
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, direccion, 5000);
            
            // Envía el datagrama
            socket.send(packet);
            
            // Presenta la información enviada
            System.out.println(
                String.format("Enviado: %s; hacia: %s", new String(packet.getData()).trim(), packet.getSocketAddress()));
            
            // Pausa el trabajo del hilo (opcional para hacer más legible)
            Thread.sleep(2000);
        }
        
        // Cierra el socket
        socket.close();
        scanner.close();
    }
}


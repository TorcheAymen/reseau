import java.io.*; 
import java.net.*;

// De la chaîne aux octets : byte[] String.getBytes(String charset)
// Des octets à la chaîne : new String(byte[] tableauOctets, int depart, int longueur, String charset)
public class Client {

public static void main(String[] args) throws Exception {
    int port = 3000;
    int lenght = 6522;
    String msg = "Hello World";
    byte buffer[] = new byte[lenght];
    buffer = msg.getBytes();
    InetAddress adress = InetAddress.getByName("192.168.1.45");

    //DatagramSocket permet de créer des sockets UDP qui permettent d'envoyer et de recevoir des datagrammes UDP.
    // On va donc créer un socket pour pouvoir envoyer les packet 
    DatagramSocket socket = new DatagramSocket(port);


    //DatagramPacket permet de créer objets qui contiendront données envoyées/reçues et l'adresse de destination ou de provenance du datagramme.
    // Maintenant on va créer notre paquet 
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adress, port);


    socket.send(packet);

}
    
}

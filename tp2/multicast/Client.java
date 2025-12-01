import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client {

    private static final int PORT = 7654;
    private static final String GROUP_ADDRESS = "224.0.0.1";
    
    public static void main(String[] args) {
        
        // On récupère le message à envoyer depuis les arguments du programme (simplicité)
        String messageContent = "Hello, Groupe Multicast !";
        
        MulticastSocket socket = null;
        
        try {
            // c'est évident ici ya qu'a lire 
            InetAddress group = InetAddress.getByName(GROUP_ADDRESS);

            // on crée un socket sans préciser le port 
            socket = new MulticastSocket(); 
            

            byte[] buffer = messageContent.getBytes();
            
            DatagramPacket packet = new DatagramPacket(
                buffer, 
                buffer.length, 
                group, 
                PORT
            );


            socket.send(packet);
            
            System.out.println("Message envoyé au groupe " + GROUP_ADDRESS + ":" + PORT);
            System.out.println("Contenu : " + messageContent);

        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du Multicast : " + e.getMessage());
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}

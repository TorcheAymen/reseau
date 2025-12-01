import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class Server {

    private static final int PORT = 7654;
    private static final String GROUP_ADDRESS = "224.0.0.1";
    private static final int MAX_LENGTH = 65535; 
    public static void main(String[] args) {
        MulticastSocket socket = null;
        
        try {
            InetAddress group = InetAddress.getByName(GROUP_ADDRESS);

            // 2. Créer le MulticastSocket sur le port de groupe même chose
            // que l'unicast au final ( presque mdr ) 
            socket = new MulticastSocket(PORT);
            
            // abonnement grp multicast => Partie importante
            socket.joinGroup(group);

            System.out.println("Récepteur Multicast démarré sur " + GROUP_ADDRESS + ":" + PORT + ". Prêt à recevoir...");

            byte[] buffer = new byte[MAX_LENGTH];
            
            // Boucle de réception
            while (true) {
                // paquet de reception 
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                
                // reception 
                socket.receive(packet); 

                // conversion des informations 
                String message = new String(
                    packet.getData(), 
                    packet.getOffset(), 
                    packet.getLength()
                );
                
 // ici on affiche les infos quoi 
                System.out.println("-------------------------------------");
                System.out.println("Message reçu de [" + packet.getAddress().getHostAddress() + "]: " + message);
            }

        } catch (SocketException e) {
            System.err.println("Erreur Socket : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erreur I/O ou adhésion au groupe : " + e.getMessage());
        } finally {

            if (socket != null) {
                try {
                    InetAddress group = InetAddress.getByName(GROUP_ADDRESS);
                    socket.leaveGroup(group);
                    socket.close();
                } catch (IOException e) {

                }
            }
        }
    }
}

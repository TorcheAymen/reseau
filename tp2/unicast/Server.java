import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {

    public static void main(String[] args) {

        int port = 3000; // port du client 
        int maxLenght = 6522; // bufferzisz choisie aléatoirement 
        DatagramSocket socket = null;

        try {
	    // création du socketudp connecté au port 3000
            socket = new DatagramSocket(port);
            System.out.println("Serveur UDP démarré. En attente de messages sur le port " + port + "...");

            // Boucle infinie pour recevoir continuellement des messages
            while (true) {
                // 2. Préparer le DatagramPacket vide pour la réception
                byte[] buffer = new byte[maxLenght];
                DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);


                socket.receive(receivedPacket); 

                // 4. Extraire et convertir les données reçues en chaîne de caractères
                String message = new String(
                    receivedPacket.getData(), // Le tableau d'octets
                    receivedPacket.getOffset(), 
                    receivedPacket.getLength() // Longueur réelle du message
                );

                // 5. Afficher le message et l'origine (adresse IP et port de l'émetteur)
                System.out.println("-------------------------------------");
                System.out.println("Message reçu de : " + receivedPacket.getAddress().getHostAddress() + 
                                   ":" + receivedPacket.getPort());
                System.out.println("Contenu du message : " + message);
                

            }

        } catch (SocketException e) {
            // port est déjà utilisé 
            System.err.println("Erreur Socket (port déjà utilisé, etc.) : " + e.getMessage());
        } catch (IOException e) {
            // (échec de la réception)
            System.err.println("Erreur d'entrée/sortie lors de la réception : " + e.getMessage());
        } finally {
            // S'assurer que le socket est fermé même en cas d'erreur
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}

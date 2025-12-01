import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread extends Thread { 
    Socket s; 
    ArrayList<Socket> listeClients = new ArrayList<>();
    public ClientThread(Socket s, ArrayList<Socket> liste_clients){
        this.s = s; 
        this.listeClients = liste_clients; 
    }

    public void run() {
        try{
            BufferedReader entree = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            while(true){
                String msg = entree.readLine(); 
            
                if (msg == null) break;
                for(Socket client : this.listeClients){
                    if(client != this.s){
                        PrintStream p = new PrintStream(client.getOutputStream()); 
                        p.println(msg);
                    }

                }
            }
            this.listeClients.remove(this.s);
            this.s.close();
            System.out.println("Client déconnecté et retiré de la liste.");
        } catch (IOException e){
            System.out.println("Erreur : " + e );
        } 

        }
}
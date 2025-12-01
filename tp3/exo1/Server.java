import java.io.*;
import java.net.*;

public class Server{


    public static void main(String[] args){
        try{
            System.out.println("Starting server...");

            // on crée un server socket sur le port 2004
            ServerSocket ss = new ServerSocket(2004); 

            System.out.println("server started !"); 

            // on accepte la connexion d'un client 
            while(true){
                Socket s  = ss.accept(); 
                System.out.println("Un nouveau client a été accepté !");
                // on peut fermer la socket maintenant

                PrintStream p = new PrintStream(s.getOutputStream()); 
                p.println("Bienvenu sur mon serveur et au revoir ");
                s.close(); 

            } 
           
        }catch(Exception e){
            System.out.println("Server Error :" + e); 
        }
    }
}
import java.io.*;
import java.net.*;
import java.util.*;

public class Server{


    public static void main(String[] args){
        try{
            System.out.println("Starting server...");

            ArrayList<Socket> listeClients = new ArrayList<>(); 

            // on crée un server socket sur le port 2004
            ServerSocket ss = new ServerSocket(2004); 

            System.out.println("server started !"); 

            // on accepte la connexion d'un client 
            while(true){
                Socket s  = ss.accept(); 
                listeClients.add(s);
                System.out.println("Un nouveau client a été accepté !");
                // on peut fermer la socket maintenant
                ClientThread t = new ClientThread(s, listeClients); 
                
                t.start(); 
            } 
           
        }catch(Exception e){
            System.out.println("Server Error :" + e); 
        }
    }



}

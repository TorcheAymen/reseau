import java.io.*;
import java.net.*;

public class Client{
    public static void main(String[] args){
        try{

            //créer un socket sur le même port que le Serveur écoute 
            Socket s = new Socket("localhost", 2004); 
            // mon moyen d'écouter le serveur 
            BufferedReader entree = new BufferedReader(new InputStreamReader(s.getInputStream()));
            // écouter le server : 
            String messageRecu = entree.readLine(); 
            System.out.println("Le serveur a dit : " + messageRecu);
        }catch (Exception e){
            System.out.println(e); 
        }
    }

}
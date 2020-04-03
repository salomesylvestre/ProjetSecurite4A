package securite;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
//import java.net.UnknownHostException;

public class PC {

    static ServerSocket server;
    static int clientID = 0;
    static SecretKey cle;

    public static void main(String ard[]) {
        //creation de la clé de chiffrement 
        creationCle();
        //appel de la methode de lancement du serveur
        go();
    }

    public static void creationCle() {
        try {
            File file = new File("key.txt");
            //generation d'une clé aleatoire stocké dans un fichier texte
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            //cle de 128 bits par sequence
            generator.init(128);
            SecretKey cle = generator.generateKey();
            String key;
            byte[] encoded = cle.getEncoded();
            key = Base64.encode(encoded);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(key);
            bw.close();
            fw.close();
            //On affiche dans la console la clé
            System.out.println("Notre clé stockée est :  " + key);
            server = new ServerSocket(4444, 5);//5 connexions clientes au plus sur le serveur 4444
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void go() {

        try {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true)//
                    {
                        try {
                            Socket client = server.accept();
                            // Faire tourner le socket qui s'occupe de ce client dans son propre thread et revenir en attente de la prochaine connexion
                            // Le chat avec l'entité connectée est encapsulé par une instance de ChatServer
                            Thread tAccueil = new Thread(new ChatServer(client, clientID));
                            tAccueil.start();
                            clientID++;
                        } catch (Exception e) {
                        }
                    }
                }
            });
            t.start();

        } catch (Exception i) {
            System.out.println("Impossible d'écouter sur le port 4444: serait-il occupé?");
            i.printStackTrace();
        }
    }
}

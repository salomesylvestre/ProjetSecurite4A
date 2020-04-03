package securite;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author salomesylvestre & florentbonnafous
 */
public class ChatServer implements Runnable {

    static TP3 cryptage;
    static SecretKey cle;
    PrintWriter out;
    BufferedReader in;
    Socket s;
    Scanner keyboard;
    int index;
    String input;
    boolean doRun = true;

    public ChatServer(Socket a, int u) {
        s = a;
        keyboard = new Scanner(System.in);
        index = u;
    }

    public void run() {

        File f = new File("key.txt");
        FileReader fr;
        try {
            com.sun.org.apache.xml.internal.security.Init.init();
            Cipher cipher = Cipher.getInstance("AES");
            fr = new FileReader(f.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String key = br.readLine();
            byte[] decodedKey = Base64.decode(key);
            cle = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            //initialisation de la classe de cryptage/decryptage
            cryptage = new TP3(cle);
            br.close();
            fr.close();

            try {
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out = new PrintWriter(s.getOutputStream());

                System.out.println("connexion de " + s.getInetAddress().toString() + " sur le port " + s.getPort());
                String talk = in.readLine();
                while (doRun) {
                    while (talk == null) {
                        talk = in.readLine();
                    }
                    //Message encrypte
                    System.out.println("Le message encrypté est : " +talk);
                    //on decrypte le message
                    talk = cryptage.decryptage(talk);
                    System.out.println("Le message decrypté est : " +talk);
                    if (talk.compareToIgnoreCase("bye") == 0) {
                        System.out.println("shutting down following remote request");
                        doRun = false;
                    } else {
                        System.out.print("Envoyer message au client n°" + index + "> ");
                        input = keyboard.nextLine();
                        //encryptage du message 
                        out.println(cryptage.encryptage(input));
                        out.flush();
                        if (input.compareToIgnoreCase("bye") == 0) {
                            System.out.println("server shutting down");
                            doRun = false;
                        } else {
                            talk = in.readLine();
                        }
                    }
                }
                s.close();
            } catch (Exception e) {
                System.out.println("raaah! what did u forget this time?");
                e.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Base64DecodingException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

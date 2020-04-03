/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securite;
  
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 *
 * @author salomesylvestre & florentbonnafous
 */
public class TP3 {
    //initialisation des varibales
    Cipher cipher;
    SecretKey cle;
    
    //Constructeur
    TP3(SecretKey cleDepart) {
         try {
            cle = cleDepart;
            //methode de cryptage AES
            cipher = Cipher.getInstance("AES");
            com.sun.org.apache.xml.internal.security.Init.init();
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    //methode encryptage
    public String encryptage(String message) {
        try {
            //passage en mode encryptage
            cipher.init(Cipher.ENCRYPT_MODE, cle);
            //retourne le message encrypte
            return Base64.encode(cipher.doFinal(message.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    //methode de decryptage
    public String decryptage(String message) {
        try {
            //methode de cryptage AES
            cipher = Cipher.getInstance("AES");
            //passage en mode decryptage
            cipher.init(Cipher.DECRYPT_MODE, cle);
            //retourne le message decrypte
            return new String(cipher.doFinal(Base64.decode(message)));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

package securite;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.util.Base64.*;


public class Cryp {

	/**
	 * @param args
	 */
	
	final String encryptedValue = "I saw the real you" ;
	String secKey = "ubutru";

	
	public static void main(String[] args) {
		
		String encryptedVal = null;
                //Nous avons ici la valeur qui sera à encoder
		String valueEnc = "aazzaa";

	    try {
                //On créé un générateur de clé pour AES
	    	 KeyGenerator generator = KeyGenerator.getInstance("AES");
                    //On initialise ici la clé AES avec une taille de clé de 128 caractéristique de l'AES
	            generator.init(128);
                    //On créé la clé
	            SecretKey key = generator.generateKey();
                    //On défini ici la méthode de chiffrement que nous allons utiliser
	            Cipher cipher = Cipher.getInstance("AES");
                    //On défini ici le mode de chiffrement de la clé
	            cipher.init(Cipher.ENCRYPT_MODE, key);
                    //On transforme la valeur de départ par un tableau d'octets 
	            byte[] res = cipher.doFinal(valueEnc.getBytes());
                    //On stocke dans une variable le tableau d'octets affiché en une chaîne de caractères
	            String res_str =  Base64.encode(res);//new String(res);
	           
                    //On change le mode : On passe en mode déchiffrement
	            cipher.init(Cipher.DECRYPT_MODE, key);
	               
                    //On transforme la chaîne à décrypter en un tableau d'octets
	            byte[] res2 = cipher.doFinal(Base64.decode(res_str));
	                //byte[] res2 = cipher.doFinal(res_str.getBytes("utf-8"));
	            String res_str2 =  new String(res2);
	           //Notre valeur de départ
	            System.out.println("source:"+valueEnc);
                    //La valeur encodée
	            System.out.println("enc:"+res_str);
                    //La valeur de départ qui a été encodé et qui a été déchiffrée
	            System.out.println("dec:"+res_str2);
                    
                    
	            byte[] enck = key.getEncoded();
                    //Affichage de cla clé
	            System.out.println(Base64.encode(enck));
	           
                    //Résultat du chiffrement 
	            String encoded = "r1peJOWYRRod8IibmrYoPA==";
                    //Chaine de caractère de la clé
	            String key_str = "+WHQtDsr9LJQ05/2MHZkQQ==";
	           
                    //Stockage dans un tableau
	            byte[] kb = Base64.decode(key_str.getBytes());
                    //Création d'une nouvelle clé de type AES
	            SecretKeySpec ksp = new SecretKeySpec(kb, "AES");
	           
	            Cipher cipher2 = Cipher.getInstance("AES");
                    //Nous repassons ici en mode décryptage
	            cipher2.init(Cipher.DECRYPT_MODE, ksp);
	            byte[] res3 = cipher2.doFinal(Base64.decode(encoded));
	            String res_str3 =  new String(res3);
	            System.out.println("obtained: "+res_str3);
	        
	       
	    } catch(Exception ex) {
	        System.out.println("The Exception is=" + ex);
	    }
	}

}
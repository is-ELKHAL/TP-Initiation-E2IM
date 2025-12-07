
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class CryptoUtils {

    private static final String AES_ALGORITHM = "AES";
    private static final String RSA_ALGORITHM = "RSA";

    public static SecretKey generateAESKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(AES_ALGORITHM);
            keyGen.init(128);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur génération clé AES", e);
        }
    }

    public static String encryptAES(String message, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(message.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erreur chiffrement AES", e);
        }
    }

    public static String decryptAES(String encryptedMessage, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decoded = Base64.getDecoder().decode(encryptedMessage);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Erreur déchiffrement AES", e);
        }
    }

    public static KeyPair generateRSAKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyGen.initialize(2048);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur génération clé RSA", e);
        }
    }

    public static String encryptRSA(SecretKey aesKey, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(aesKey.getEncoded());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erreur chiffrement RSA", e);
        }
    }

    public static SecretKey decryptRSA(String encryptedKey, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decoded = Base64.getDecoder().decode(encryptedKey);
            byte[] decrypted = cipher.doFinal(decoded);
            return new SecretKeySpec(decrypted, AES_ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException("Erreur déchiffrement RSA", e);
        }
    }

    public static String publicKeyToString(PublicKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static PublicKey stringToPublicKey(String keyStr) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(keyStr);
            java.security.spec.X509EncodedKeySpec spec =
                    new java.security.spec.X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Erreur conversion clé publique", e);
        }
    }
}
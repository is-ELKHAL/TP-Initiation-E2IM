import java.io.*;
import java.net.*;
import java.security.PublicKey;
import java.util.Scanner;
import javax.crypto.SecretKey;

public class SecureChatClient {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private SecretKey aesKey;
    private String pseudo;
    private boolean connected;

    public void connect(String host, int port, String pseudo) {
        this.pseudo = pseudo;

        try {
            socket = new Socket(host, port);
            System.out.println("✓ Connecté au serveur " + host + ":" + port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            String publicKeyStr = (String) in.readObject();
            PublicKey serverPublicKey = CryptoUtils.stringToPublicKey(publicKeyStr);

            aesKey = CryptoUtils.generateAESKey();
            String encryptedAESKey = CryptoUtils.encryptRSA(aesKey, serverPublicKey);
            out.writeObject(encryptedAESKey);
            out.flush();

            System.out.println("✓ Échange de clés réussi");

            String encryptedPseudo = CryptoUtils.encryptAES(pseudo, aesKey);
            Message pseudoMsg = new Message("", encryptedPseudo);
            out.writeObject(pseudoMsg);
            out.flush();

            connected = true;
            System.out.println("✓ Vous êtes connecté en tant que : " + pseudo);
            System.out.println("Tapez vos messages (ou 'quit' pour quitter)\n");

            new Thread(new MessageReceiver()).start();

        } catch (Exception e) {
            System.err.println("✗ Erreur de connexion : " + e.getMessage());
        }
    }

    public void sendMessage(String text) {
        if (!connected) return;

        try {
            String encryptedText = CryptoUtils.encryptAES(text, aesKey);
            Message message = new Message(pseudo, encryptedText);
            out.writeObject(message);
            out.flush();

        } catch (IOException e) {
            System.err.println("✗ Erreur d'envoi : " + e.getMessage());
        }
    }

    public void disconnect() {
        connected = false;

        try {
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();

            System.out.println("\n✓ Déconnecté du serveur");

        } catch (IOException e) {
            System.err.println("✗ Erreur de déconnexion : " + e.getMessage());
        }
    }

    private class MessageReceiver implements Runnable {

        @Override
        public void run() {
            try {
                while (connected) {
                    Message message = (Message) in.readObject();

                    if (message.getType() == Message.MessageType.TEXT) {
                        String decryptedContent = CryptoUtils.decryptAES(message.getContent(), aesKey);
                        message.setContent(decryptedContent);
                    }

                    System.out.println(message.getFormattedMessage());
                }

            } catch (EOFException e) {
                // Connexion fermée
            } catch (Exception e) {
                if (connected) {
                    System.err.println("✗ Erreur de réception : " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== CLIENT CHAT SÉCURISÉ ===\n");

        System.out.print("Adresse du serveur (ex: localhost) : ");
        String host = scanner.nextLine();

        System.out.print("Port du serveur (ex: 12345) : ");
        int port = Integer.parseInt(scanner.nextLine());

        System.out.print("Votre pseudonyme : ");
        String pseudo = scanner.nextLine();

        SecureChatClient client = new SecureChatClient();
        client.connect(host, port, pseudo);

        while (client.connected) {
            String message = scanner.nextLine();

            if (message.equalsIgnoreCase("quit")) {
                break;
            }

            if (!message.trim().isEmpty()) {
                client.sendMessage(message);
            }
        }

        client.disconnect();
        scanner.close();
    }
}
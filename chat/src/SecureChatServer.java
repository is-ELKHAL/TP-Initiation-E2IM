import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;
import javax.crypto.SecretKey;

public class SecureChatServer {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private KeyPair rsaKeyPair;

    public SecureChatServer() {
        clients = new ArrayList<>();
        rsaKeyPair = CryptoUtils.generateRSAKeyPair();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("✓ Serveur démarré sur le port " + PORT);
            System.out.println("En attente de connexions...\n");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("→ Nouvelle connexion : " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket);
                clients.add(handler);
                new Thread(handler).start();
            }

        } catch (IOException e) {
            System.err.println("✗ Erreur serveur : " + e.getMessage());
        }
    }

    private synchronized void broadcast(Message message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender && client.isConnected()) {
                client.sendMessage(message);
            }
        }
    }

    private synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    private class ClientHandler implements Runnable {

        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private String pseudo;
        private SecretKey aesKey;
        private boolean connected;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            this.connected = true;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                String publicKeyStr = CryptoUtils.publicKeyToString(rsaKeyPair.getPublic());
                out.writeObject(publicKeyStr);
                out.flush();

                String encryptedAESKey = (String) in.readObject();
                aesKey = CryptoUtils.decryptRSA(encryptedAESKey, rsaKeyPair.getPrivate());

                System.out.println("✓ Échange de clés réussi avec le client");

                Message pseudoMsg = (Message) in.readObject();
                String encryptedPseudo = pseudoMsg.getContent();
                pseudo = CryptoUtils.decryptAES(encryptedPseudo, aesKey);

                System.out.println("✓ " + pseudo + " s'est connecté");

                Message connectionMsg = new Message(pseudo, "", Message.MessageType.CONNECTION);
                broadcast(connectionMsg, this);

                while (connected) {
                    Message message = (Message) in.readObject();
                    String decryptedContent = CryptoUtils.decryptAES(message.getContent(), aesKey);
                    message.setContent(decryptedContent);
                    message.setSender(pseudo);

                    System.out.println(message.getFormattedMessage());
                    broadcast(message, this);
                }

            } catch (EOFException e) {
                // Connexion fermée
            } catch (Exception e) {
                System.err.println("✗ Erreur avec " + pseudo + " : " + e.getMessage());
            } finally {
                disconnect();
            }
        }

        public void sendMessage(Message message) {
            try {
                String encryptedContent = CryptoUtils.encryptAES(message.getContent(), aesKey);
                message.setContent(encryptedContent);
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.err.println("✗ Erreur envoi à " + pseudo);
            }
        }

        private void disconnect() {
            connected = false;

            try {
                if (pseudo != null) {
                    System.out.println("✗ " + pseudo + " s'est déconnecté");
                    Message disconnectionMsg = new Message(pseudo, "", Message.MessageType.DISCONNECTION);
                    broadcast(disconnectionMsg, this);
                }

                if (socket != null) socket.close();
                if (in != null) in.close();
                if (out != null) out.close();

            } catch (IOException e) {
                System.err.println("✗ Erreur fermeture : " + e.getMessage());
            } finally {
                removeClient(this);
            }
        }

        public boolean isConnected() {
            return connected;
        }
    }

    public static void main(String[] args) {
        SecureChatServer server = new SecureChatServer();
        server.start();
    }
}

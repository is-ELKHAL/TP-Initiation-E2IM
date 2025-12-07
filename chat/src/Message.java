import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum MessageType {
        TEXT,
        CONNECTION,
        DISCONNECTION,
        KEY_EXCHANGE
    }

    private String sender;
    private String content;
    private MessageType type;
    private LocalDateTime timestamp;

    public Message(String sender, String content, MessageType type) {
        this.sender = sender;
        this.content = content;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public Message(String sender, String content) {
        this(sender, content, MessageType.TEXT);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedMessage() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = timestamp.format(formatter);

        switch (type) {
            case CONNECTION:
                return "[" + time + "] *** " + sender + " a rejoint le chat ***";

            case DISCONNECTION:
                return "[" + time + "] *** " + sender + " a quitté le chat ***";

            case TEXT:
            default:
                return "[" + time + "] " + sender + ": " + content;
        }
    }

    @Override
    public String toString() {
        return getFormattedMessage();
    }
}
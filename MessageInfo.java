package comparativestudies;

public class MessageInfo {
    private String sender;
    private String receiver;
    private MessageType type;

    public MessageInfo(String sender, String receiver, MessageType type) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public MessageType getType() {
        return type;
    }
}

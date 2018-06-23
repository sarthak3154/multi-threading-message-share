package comparativestudies;

class MessageInfo {
    private String sender;
    private String receiver;
    private MessageType type;
    private long timestamp;

    MessageInfo(String sender, String receiver, MessageType type, long timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.timestamp = timestamp;
    }

    String getSender() {
        return sender;
    }

    String getReceiver() {
        return receiver;
    }

    MessageType getType() {
        return type;
    }

    long getTimestamp() {
        return timestamp;
    }
}

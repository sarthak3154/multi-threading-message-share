package comparativestudies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Master extends Thread {
    private volatile List<MessageInfo> messages = new ArrayList<>();
    private static final long MASTER_EXECUTION_TIME = 1500;
    private Main main = Main.getInstance();

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        initCommunication();

        while (true) {
            while (!messages.isEmpty()) {
                Iterator<MessageInfo> iterator = messages.iterator();
                while (iterator.hasNext()) {
                    MessageInfo messageInfo = iterator.next();
                    System.out.println(messageInfo.getReceiver() + " received " +
                            messageInfo.getType().toString().toLowerCase() + " message from " + messageInfo.getSender());
                    iterator.remove();
                }
            }

            if (System.currentTimeMillis() - startTime > MASTER_EXECUTION_TIME) {
                System.out.println(Thread.currentThread().getName() + " has not received any messages " +
                        "since 1.5 sec, exiting...");
                break;
            }
        }
    }

    public List<MessageInfo> getMessages() {
        return messages;
    }

    public void addMessage(MessageInfo message) {
        messages.add(message);
    }

    private void initCommunication() {
        for (Friend friend : main.getFriendsMap().values()) {
            friend.start();
        }
    }

}

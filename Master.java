package comparativestudies;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Master extends Thread {
    private BlockingQueue<MessageInfo> messages;
    private static final long MASTER_EXECUTION_TIME = 1500;
    private Main main = Main.getInstance();

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        messages = new ArrayBlockingQueue<>(main.findMessagesCount());
        initCommunication();

        while (true) {
            Iterator<MessageInfo> iterator = messages.iterator();
            if (iterator.hasNext()) {
                MessageInfo messageInfo = iterator.next();
                System.out.println(messageInfo.getReceiver() + " received " +
                        messageInfo.getType().toString().toLowerCase() + " message from " + messageInfo.getSender() +
                        " [" + messageInfo.getTimestamp() + "]");
                iterator.remove();
            }

            if (System.currentTimeMillis() - startTime > MASTER_EXECUTION_TIME) {
                System.out.println("\n" + Thread.currentThread().getName() + " has received no calls " +
                        "for 1.5 seconds, ending...");
                break;
            }
        }
    }

    void addMessage(MessageInfo message) {
        messages.add(message);
    }

    private void initCommunication() {
        for (Person person : main.getPersonMap().values()) {
            person.start();
        }
    }

}

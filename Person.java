package comparativestudies;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Person extends Thread {
    private Vector<String> initialContacts;
    private BlockingQueue<Receiver> introFriends;
    private BlockingQueue<Receiver> replyFriends;
    private Main main = Main.getInstance();
    private static final long EXECUTION_TIME = 1000;

    Person() {
        initialContacts = new Vector<>();
        introFriends = new ArrayBlockingQueue<>(initialContacts.capacity());
        replyFriends = new ArrayBlockingQueue<>(initialContacts.capacity());
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (true) {
            try {
                initMessages();
                receiveIntroMessage();
                receiveReplyMessage();

                if (System.currentTimeMillis() - startTime > EXECUTION_TIME) {
                    System.out.println("\nProcess " + Thread.currentThread().getName() + " has received no calls " +
                            "for 1 second, ending...");
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveReplyMessage() {
        Iterator<Receiver> replyIterator = replyFriends.iterator();
        if (replyIterator.hasNext()) {
            Receiver friend = replyIterator.next();
            main.getMasterThread().addMessage(new MessageInfo(friend.getFriendName(),
                    Thread.currentThread().getName(), MessageType.REPLY, friend.getTimestamp()));
            replyIterator.remove();
        }

    }

    private void receiveIntroMessage() throws InterruptedException {
        Iterator<Receiver> iterator = introFriends.iterator();
        if (iterator.hasNext()) {
            Receiver friend = iterator.next();
            main.getMasterThread().addMessage(new MessageInfo(friend.getFriendName(),
                    Thread.currentThread().getName(), MessageType.INTRO, friend.getTimestamp()));
            int randomNo = new Random().nextInt(99) + 1;
            Thread.sleep(randomNo);
            main.getPersonMap().get(friend.getFriendName()).addReplyContact(new Receiver(Thread.currentThread().getName(),
                    friend.getTimestamp()));
            iterator.remove();
        }
    }

    private void initMessages() throws InterruptedException {
        while (!initialContacts.isEmpty()) {
            Iterator<String> iterator = initialContacts.iterator();
            while (iterator.hasNext()) {
                String friend = iterator.next();
                int randomNo = new Random().nextInt(99) + 1;
                Thread.sleep(randomNo);
                main.getPersonMap().get(friend).addIntroContact(new Receiver(Thread.currentThread().getName(),
                        System.currentTimeMillis()));
                iterator.remove();
            }
        }
    }

    void addContacts(List<String> friend) {
        initialContacts.addAll(friend);
    }

    private void addIntroContact(Receiver friend) {
        introFriends.add(friend);
    }

    private void addReplyContact(Receiver friend) {
        replyFriends.add(friend);
    }

    private class Receiver {
        private String friendName;
        private long timestamp;

        Receiver(String friendName, long timestamp) {
            this.friendName = friendName;
            this.timestamp = timestamp;
        }

        String getFriendName() {
            return friendName;
        }

        long getTimestamp() {
            return timestamp;
        }
    }
}

package comparativestudies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Friend extends Thread {
    private Vector<String> initialContacts;
    private List<String> introFriends;
    private List<String> replyFriends;
    private Main main = Main.getInstance();
    private static final long EXECUTION_TIME = 1000;


    public Friend() {
        initialContacts = new Vector<>();
        introFriends = new ArrayList<>();
        replyFriends = new ArrayList<>();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (true) {
            initMessages();
            while (!introFriends.isEmpty()) {
                Iterator<String> iterator = introFriends.iterator();
                while (iterator.hasNext()) {
                    String friend = iterator.next();
                    main.getMasterThread().addMessage(new MessageInfo(friend,
                            Thread.currentThread().getName(), MessageType.INTRO));
                    main.getFriendsMap().get(friend).addReplyContact(Thread.currentThread().getName());
                    iterator.remove();
                }
            }

            while (!replyFriends.isEmpty()) {
                Iterator<String> iterator = replyFriends.iterator();
                while (iterator.hasNext()) {
                    main.getMasterThread().addMessage(new MessageInfo(iterator.next(),
                            Thread.currentThread().getName(), MessageType.REPLY));
                    iterator.remove();
                }
            }

            if (System.currentTimeMillis() - startTime > EXECUTION_TIME) {
                System.out.println("Process " + Thread.currentThread().getName() + " has not received any messages " +
                        "since 1 sec, exiting...");
                break;
            }
        }
    }

    private void initMessages() {
        while (!initialContacts.isEmpty()) {
            Iterator<String> iterator = initialContacts.iterator();
            while (iterator.hasNext()) {
                String friend = iterator.next();
                main.getFriendsMap().get(friend).addIntroContact(Thread.currentThread().getName());
                iterator.remove();
            }
        }
    }

    public void addContacts(List<String> friend) {
        initialContacts.addAll(friend);
    }

    public void addIntroContact(String friendName) {
        introFriends.add(friendName);
    }

    public void addReplyContact(String friendName) {
        replyFriends.add(friendName);
    }
}

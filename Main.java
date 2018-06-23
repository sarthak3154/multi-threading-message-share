package comparativestudies;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Main mainInstance;
    private Map<String, Friend> friendsMap;
    private Master masterThread;

    protected static Main getInstance() {
        if (mainInstance == null) {
            return new Main();
        }
        return mainInstance;
    }

    public Master getMasterThread() {
        return masterThread;
    }

    public static void main(String[] args) {
        mainInstance = getInstance();
        mainInstance.initMasterThread();
        mainInstance.initializeFriends();
    }

    private void initMasterThread() {
        masterThread = new Master();
        masterThread.setName("Master");
        masterThread.start();
    }

    public Map<String, Friend> getFriendsMap() {
        return friendsMap;
    }

    private void initializeFriends() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\Users\\Lenovo\\IdeaProjects\\DS Algorithms\\src\\comparativestudies\\calls.txt")));
            String line = null;
            friendsMap = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                String namesInfo = line.substring(1, line.length() - 2);
                Friend friend = new Friend();
                String contactsInfo = namesInfo.split(" ")[1];
                String[] contacts = contactsInfo.substring(1, contactsInfo.length() - 1).split(",");
                friend.addContacts(Arrays.asList(contacts));
                friend.setName(namesInfo.split(",")[0]);
                friendsMap.put(friend.getName(), friend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

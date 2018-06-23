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
    private Map<String, Person> personMap;
    private Master masterThread;
    private int messagesCount = 0;

    static Main getInstance() {
        if (mainInstance == null) {
            return new Main();
        }
        return mainInstance;
    }

    Master getMasterThread() {
        return masterThread;
    }

    public static void main(String[] args) {
        mainInstance = getInstance();
        mainInstance.initializeFriends();
        mainInstance.initMasterThread();
    }

    private void initMasterThread() {
        masterThread = new Master();
        masterThread.setName("Master");
        masterThread.start();
    }

    Map<String, Person> getPersonMap() {
        return personMap;
    }

    private void initializeFriends() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\Users\\Lenovo\\IdeaProjects\\DS Algorithms\\src\\comparativestudies\\calls.txt")));
            String line;
            personMap = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                String namesInfo = line.substring(1, line.length() - 2);
                Person person = new Person();
                String contactsInfo = namesInfo.split(" ")[1];
                String[] contacts = contactsInfo.substring(1, contactsInfo.length() - 1).split(",");
                person.addContacts(Arrays.asList(contacts));
                person.setName(namesInfo.split(",")[0]);
                personMap.put(person.getName(), person);
                messagesCount += contacts.length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int findMessagesCount() {
        return messagesCount * 2;
    }
}

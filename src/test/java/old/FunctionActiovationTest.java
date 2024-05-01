package old;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FunctionActiovationTest {

    @Test
    public void test() throws IOException {
        Child child = new Child();
        System.out.println(child.getSome());

        Parent childAsParent = (Parent) child;

//        FileOutputStream fis = new FileOutputStream("d");
//        ObjectOutputStream oos = new ObjectOutputStream(fis);
//
//        oos.writeObject(child);


        LocalDateTime stamp = LocalDateTime.now();
        stamp = stamp.minusDays(10);

        System.out.println(stamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        File file = new File("");

        System.out.println(childAsParent.getSome());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        String inputFile = args[0];
//        String[] ids = inputFile.split("\n");
        File file = new File("");

        HttpClient httpClient = HttpClient.newHttpClient();
        httpClient.send(null, null);

        String input = Files.readString(Path.of("/your/directory/path/file.txt"));
        String[] ids = input.split("\n");

        String currentUser = ids[0];
        List<String> otherUsers = new ArrayList<>(Arrays.asList(ids));
        otherUsers.remove(0);

        String apiKey = getServiceApiKey();

        for (int i = 0; i < otherUsers.size(); i++) {
            int relationDeep = getDeepOfRelation(currentUser, otherUsers.get(i));

            String answer = new StringBuilder(currentUser)
                    .append(",")
                    .append(otherUsers.get(i))
                    .append(",")
                    .append(relationDeep)
                    .toString();

        }


    }

    public static int getDeepOfRelation(String currUser, String otherUser) {
        return getDeepOfRelation(currUser, otherUser, 0);
    }

    public static int getDeepOfRelation(String currUser, String otherUser, int currentDeep) {
        if (currentDeep == 5) {
            return -1;
        }

        currentDeep++;

        String[] friendsId = getFriendsIds(otherUser);
        HashSet<String> friends = new HashSet(List.of(friendsId));
        if (friends.contains(currUser)) {
            return currentDeep;
        }

        for (String userId: friendsId) {
            int some = getDeepOfRelation(currUser, userId, currentDeep);
        }

        return currentDeep;
    }


    public static String getServiceApiKey() {
        return "";
    }

    public static String[] getFriendsIds(String userId) {
        return null;
    }

}

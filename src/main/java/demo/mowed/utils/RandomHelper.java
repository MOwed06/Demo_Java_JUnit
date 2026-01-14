package demo.mowed.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RandomHelper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static Random random = new Random();
    private static List<String> firstNames;
    private static List<String> lastNames;
    private static List<String> sentences;

    static {
        try {
            firstNames = readSetupDataFile("firstNames.json");
            lastNames = readSetupDataFile("familyNames.json");
            sentences = readSetupDataFile("poetryRFrost.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> readSetupDataFile(String fileName) throws IOException {
        var fullName = String.format("data/%s", fileName);
        return OBJECT_MAPPER.readValue(new File(fullName),
                new TypeReference<List<String>>() {});
    }

    public static int getInt(int min, int max) {
        return random.nextInt(min, max);
    }

    public static float getFloat(float min, float max) {
        return random.nextFloat(min, max);
    }

    public static <T> T selectItem(List<T> source) {
        int selectIndex = getInt(0, source.size());
        return source.get(selectIndex);
    }

    public static String generatePerson() {
        var firstName = selectItem(firstNames);
        var lastName = selectItem(lastNames);
        return String.format("%s %s", firstName, lastName);
    }

    public static String generateEmail() {
        var firstName = selectItem(firstNames);
        var lastName = selectItem(lastNames);
        return String.format("%s.%s@demo.com", firstName, lastName);
    }

    public static String generateGUID() {
        var guid = UUID.randomUUID();
        return guid.toString().toUpperCase();
    }

    public static String generatePhrase() {
        return selectItem(sentences);
    }

    // demo purposes only
    public static void main(String[] args) {
        try {
            var person = RandomHelper.generatePerson();
            System.out.println(person);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

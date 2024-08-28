import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
public class DestinationHashGenerator {
    public static void main(String[] args) {
        String prnNumber = "240343120061"; 
        String jsonString = "{ \"key1\": 10, \"key2\": { \"key3\": \"value3\", \"destination\": \"finalValue\" }, \"key4\": [1, 2, { \"destination\": \"anotherValue\" }] }";
        try {
            String destinationValue = findDestinationValueFromString(jsonString);
            String randomString = generateRandomString(8);
            String inputString = prnNumber.toLowerCase() + destinationValue + randomString;
            String md5Hash = generateMD5Hash(inputString);
            System.out.println(md5Hash + ";" + randomString);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    private static String findDestinationValueFromString(String jsonString) {
        String searchKey = "\"destination\"";
        int index = jsonString.indexOf(searchKey);
        if (index == -1) {
            return ""; 
        }
        int colonIndex = jsonString.indexOf(":", index);
        int startQuoteIndex = jsonString.indexOf("\"", colonIndex);
        int endQuoteIndex = jsonString.indexOf("\"", startQuoteIndex + 1);

        return jsonString.substring(startQuoteIndex + 1, endQuoteIndex);
    }
    private static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }
        return randomString.toString();
    }
    private static String generateMD5Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

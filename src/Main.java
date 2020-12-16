import java.util.Scanner;
import java.security.SecureRandom;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class Main {

    public static int parseInput(String str, int def) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static byte[] generateRandomKey(int len) {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[len];
        random.nextBytes(bytes);
        return bytes;
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String hmac256(String msg, String keyString) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return digest;
    }


    public static void main(String[] args) {

        if (args.length > 2 || args.length % 2 != 0) {
            for (int i = 0; i < args.length; i++) {
                for (int j = 0; j < args.length; j++) {
                    if (args[i].equals(args[j]) && i != j) {
                        System.out.println("Enter unique arguments! Example : rock paper scissors");
                        System.exit(0);
                    }
                }
            }
        }
        if (args.length < 3 || args.length % 2 == 0) {
            System.out.println("Enter 3 or more arguments, but not even quantity (3, 5, 7, ...)! Example : rock paper scissors");
            System.exit(0);
        } else {
            byte[] randomKey = generateRandomKey(16);
            String randomKeyHex = bytesToHex(randomKey);


            int computerMoveIndex = getRandomNumber(1, args.length + 1);
            String computerMove = args[computerMoveIndex - 1];
            System.out.println("HMAC-256: " + hmac256(computerMove, randomKeyHex));


            Boolean isCorrect = false;
            String move;
            int moveIndex = -1;
            while (!isCorrect) {
                System.out.println("Choose your move:");
                for (int i = 0; i < args.length; i++) {
                    System.out.println((i + 1) + " - " + args[i]);
                }
                System.out.println(0 + " - " + "Exit");
                Scanner inputMove = new Scanner(System.in);
                System.out.println("Your move:");

                String yourMove = inputMove.nextLine();
                moveIndex = parseInput(yourMove, -1);
                if (moveIndex < 0 || moveIndex > args.length) {
                    isCorrect = false;
                    System.out.println("Incorrect input!");
                } else {
                    isCorrect = true;
                }
            }
            if (moveIndex == 0) {
                System.out.println("Game closed!");
                System.exit(0);
            } else {
                move = args[moveIndex - 1];
                System.out.println("Your move is: " + move);
            }
            System.out.println("Computer's move is: " + computerMove);

            Boolean isResultFound = false;
            int search = 0;
            int currentIndex = moveIndex - 1;
            if (moveIndex == computerMoveIndex) {
                System.out.println("Draw!");
            } else {
                while (!isResultFound) {
                    if (currentIndex == args.length) {
                        currentIndex = 0;
                    }
                    if (currentIndex == computerMoveIndex - 1) {
                        isResultFound = true;
                        if (search > args.length/2) {
                            System.out.println("You win!");
                        } else {
                            System.out.println("You lost!");
                        }
                    }
                    search++;
                    currentIndex++;
                }
            }
            System.out.println("HMAC-256 key: " + randomKeyHex);
        }
    }
}

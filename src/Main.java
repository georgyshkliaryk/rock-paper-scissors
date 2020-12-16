import java.util.Scanner;

public class Main {

    public static int parseInput(String str, int def) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static void main(String[] args) {

        // TODO computer's move

        if (args.length < 3 || args.length % 2 == 0) {
            System.out.println("Enter 3 or more arguments, but not even quantity (3, 5, 7, ...)! Example : rock paper scissors");
        } else {
            Boolean isCorrect = false;
            String move = "";
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
        }

        //TODO check results
    }
}

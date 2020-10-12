import org.apache.commons.codec.binary.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class RockPaperScissors {

    public static void main(String[] args) throws  NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException{
        Scanner scanner = new Scanner(System.in);

        if(args.length < 3 || args.length > 5 || args.length == 4) {
            System.out.println("Error, set proper values");
            System.exit(1);
        }

        int gameType = (int) Math.floor(args.length / 2);

        int compChoice = (int) (Math.random()* (args.length - 1) + 1);

        String secKey = secure(key(), args[compChoice - 1]);
        System.out.println("HMAC");
        System.out.println(secKey);

        if(gameType == 1) {
            availableMovesStandart();
        } else if(gameType == 2) {
            availableMovesExtended();
        } else {
            System.out.println("Error, enter correct values");
        }

        int myChoice = scanner.nextInt();

        while(myChoice > args.length || myChoice < 0) {

            System.out.println("choose the right option");

            if(gameType == 2) {
                availableMovesExtended();
                myChoice = scanner.nextInt();

            }   else if(gameType == 1) {
                availableMovesStandart();
                myChoice = scanner.nextInt();
            }
        }

        if(myChoice != 0) {
            System.out.println("Your move " + args[myChoice - 1]);
            System.out.println("Computer move " + args[compChoice-1]);
            myChoice -= 1;
            compChoice -= 1;

        } else {
            System.exit(0);
        }

        int winNum = myChoice - compChoice;

        if(gameType == 2) {
            switch (winNum) {
                case 2:
                case 1:
                case -3:
                case -4:
                    System.out.println("you win");
                    break;
                case 0:
                    System.out.println("It's a tie");
                    break;
                default:
                    System.out.println("you lose");
            }
        }

        if(gameType != 2) {

            switch (winNum) {
                case 1:
                case -2:
                    System.out.println("you win");
                    break;
                case 0:
                    System.out.println("It's a tie");
                    break;
                default:
                    System.out.println("you lose");
            }
        }
        System.out.println("HMAC key: " + secKey);
    }


    public static String key() throws  NoSuchAlgorithmException{
            SecureRandom random = SecureRandom.getInstanceStrong();
            byte[] values = new byte[32];
            random.nextBytes(values);
            StringBuilder sb = new StringBuilder();
            for (byte b : values) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
    }


    public static String secure(String key, String message) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Mac sha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec s_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256.init(s_key);
        return Base64.encodeBase64String(sha256.doFinal(message.getBytes("UTF-8")));
    }

    public static void availableMovesExtended() {
        System.out.println("Available moves");
        System.out.println("1: Rock");
        System.out.println("2: Scissors");
        System.out.println("3: Paper");
        System.out.println("4: Lizard");
        System.out.println("5: Spock");
        System.out.println("0: Exit");
        System.out.println("Enter your move");
    }
    public static void availableMovesStandart() {
        System.out.println("Available moves");
        System.out.println("1: Rock");
        System.out.println("2: Scissors");
        System.out.println("3: Paper");
        System.out.println("0: exit");
        System.out.println("Enter your move");
    }


}

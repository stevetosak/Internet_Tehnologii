import gen.HtmlGen;
import gen.WordGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static gen.HttpGen.*;
public class Hangman {
    private static final String grad = "RESEN";
    private static final Map<Character, List<Integer>> chToPosMap = new HashMap<>();

    private static boolean checkIfFinished(String word) {
        int j = 0;
        for (int i = 0; i < word.length(); i += 2) {
            if (word.charAt(i) != grad.charAt(j++)) {
                return false;
            }
        }
        return true;
    }

    private static void init(){
        for (int i = 0; i < grad.length(); i++) {
            if (!chToPosMap.containsKey(grad.charAt(i))) {
                chToPosMap.put(grad.charAt(i), new ArrayList<>());
                chToPosMap.get(grad.charAt(i)).add(i);
            } else {
                chToPosMap.get(grad.charAt(i)).add(i);
            }
        }
    }

    public static void printErrorMsg(String title) {
        printHTTPHeader();
        String errorMsg = "INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 " +
                "with a single alphabetical character as a value and will output i" +
                "f you guessed the char correctly (if an imagined string contains the guessed " +
                "character and at which positions)";
        System.out.println(HtmlGen.generateSimpleHtml(title, new String[]{errorMsg}));
    }

    private static boolean inputValid(String[] inputToken) {
        if (inputToken.length < 2) {
            printErrorMsg("HELP");
            return false;
        }
        if (!inputToken[0].equals("operand1")) {
            printErrorMsg("ERROR");
            return false;
        }
        if (inputToken[1].length() > 1) {
            printErrorMsg("ERROR");
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        init();
        String method = System.getenv("REQUEST_METHOD");
        String[] inputToken = new String[]{};

        if (method.equals("GET")) {
            inputToken = System.getenv("QUERY_STRING").split("&")[0].split("=");
            if (!inputValid(inputToken)) {
                return;
            }
        } else if (method.equals("POST")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String postData = br.readLine();
            inputToken = postData.split("&")[0].split("=");
            if (!inputValid(inputToken)) {
                return;
            }
        }

        char ch = Character.toUpperCase(inputToken[1].charAt(0));

        //clearCookie("City");

        String cookie = System.getenv("HTTP_COOKIE");
        String currentWordState;


        if (cookie == null) {
            cookie = "char=" + "_ ".repeat(grad.length());
        }
        currentWordState = cookie.split("=")[1];

        if (chToPosMap.containsKey(ch)) {
            char[] chrs = currentWordState.toCharArray();
            for (Integer pos : chToPosMap.get(ch)) {
                chrs[pos * 2] = ch;
                // se mnozi po dva bidejki bukvite sto se prikazuvaat se odvoeni so prazno mesto,
                // primer: R E S E N, bukvata 'S' e na pozicija 2 vo originalniot string
                // a tuka e na pozicija 2 * 2
            }

            String word = new String(chrs);

            if (checkIfFinished(word)) {
                clearCookie("char");
                System.out.println("You guessed correctly - " + grad);
            } else {
                setCookie("char",word);
                System.out.println(HtmlGen.generateSimpleHtml("Hangman", new String[]{"HIT", word}));
            }

        } else {
            printHTTPHeader();
            System.out.println(HtmlGen.generateSimpleHtml("Hangman", new String[]{"MISS", currentWordState}));
        }
    }

}

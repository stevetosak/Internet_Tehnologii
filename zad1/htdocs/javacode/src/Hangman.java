import gen.HtmlGen;
import gen.HttpGen;
import gen.WordGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static gen.HttpGen.*;
public class Hangman {
    private static final String grad = "СТРУМИЦА";
    private static final Map<Character, List<Integer>> chToPosMap = new HashMap<>();

    private static boolean checkIfFinished(String word) {
        int j = 0;
        for (int i = 0; i < word.length(); i++) {
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

    public static String printErrorMsg(String title) {
        printHTTPHeader();
        String errorMsg = "INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 " +
                "with a single alphabetical character as a value and will output i" +
                "f you guessed the char correctly (if an imagined string contains the guessed " +
                "character and at which positions)";
        return HtmlGen.generateSimpleHtml(title, new String[]{errorMsg});
    }

    private static String inputValid(String[] inputToken) {
        if (inputToken.length < 2) {
            return printErrorMsg("HELP");
        }
        if (!inputToken[0].equals("operand1")) {
            return printErrorMsg("ERROR");
        }
        if (inputToken[1].length() != 1) {
            return printErrorMsg("ERROR");
        }
        return "VALID";
    }

    public static void main(String[] args) throws IOException {
        init();
        String method = System.getenv("REQUEST_METHOD");
        String[] inputToken = new String[]{};
        PrintWriter pw = new PrintWriter(System.out,false, StandardCharsets.UTF_8);

        if (method.equals("GET")) {
            String query = System.getenv("QUERY_STRING");
            if(query != null){
                query = URLDecoder.decode(query, StandardCharsets.UTF_8);
                inputToken = query.split("&")[0].split("=");
                String res = inputValid(inputToken);
                if (!res.equals("VALID")) {
                    pw.println(res);
                    pw.flush();
                    return;
                }
            }

        } else if (method.equals("POST")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String postData = URLDecoder.decode(br.readLine(),StandardCharsets.UTF_8);
            inputToken = postData.split("&")[0].split("=");
            String res = inputValid(inputToken);
            if (!res.equals("VALID")) {
                pw.println(res);
                pw.flush();
                return;
            }
        }

        char ch = Character.toUpperCase(inputToken[1].charAt(0));
        String cookies = System.getenv("HTTP_COOKIE");
        String currentWordState;
        String cookie;
        if (cookies == null) {
            cookie = "word=" + "_".repeat(grad.length());
        } else {
            String[] cookiePair = HttpGen.getCookie("word",cookies.split(";"));
            if(cookiePair == null || cookiePair.length < 2){
                cookie = "word=" + "_".repeat(grad.length());
            } else {
                cookie = cookiePair[0] + "=" + cookiePair[1];
            }
        }
        currentWordState = URLDecoder.decode(cookie.split("=")[1],StandardCharsets.UTF_8);
        if (chToPosMap.containsKey(ch)) {
            char[] chrs = currentWordState.toCharArray();
            for (Integer pos : chToPosMap.get(ch)) {
                chrs[pos] = ch;
            }

            String word = new String(chrs);

            if (checkIfFinished(word)) {
                clearCookie("word");
                printHTTPHeader();
                pw.println(HtmlGen.generateSimpleHtml("Hangman",new String[]{"You guessed correctly - " + grad}));
                pw.flush();

            } else {
                String encoded = URLEncoder.encode(word,StandardCharsets.UTF_8);
                setCookie("word",encoded);
                pw.println(HtmlGen.generateSimpleHtml("Hangman", new String[]{word}));
                pw.flush();
            }

        } else {
            printHTTPHeader();
            pw.println(HtmlGen.generateSimpleHtml("Hangman", new String[]{currentWordState}));
            pw.flush();
        }
        pw.flush();
    }

}

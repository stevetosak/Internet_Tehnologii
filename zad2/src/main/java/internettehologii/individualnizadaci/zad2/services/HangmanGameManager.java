package internettehologii.individualnizadaci.zad2.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.ui.Model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HangmanGameManager {
    private String word;
    private String defaultString;
    private Map<Character, List<Integer>> chToPosMap;

    public HangmanGameManager(){
        reset();
    }

    public void updatePage(String wordStateCookie, Character ch, HttpServletResponse response, Model model){
        char[] currentWordChArray;

        if (wordStateCookie.equals("EMPTY")) currentWordChArray = defaultString.toCharArray();
        else currentWordChArray = wordStateCookie.toCharArray();

        if (chToPosMap.containsKey(ch)) {
            for (Integer pos : chToPosMap.get(ch)) {
                currentWordChArray[pos] = ch;
            }
        }

        String currentWordString = new String(currentWordChArray);
        //String htmlencode = StringEscapeUtils.escapeHtml4(currentWordString);
        if(gameFinished(currentWordString)){
            Cookie deleteCookie = new Cookie("word",null);
            updateCookie(deleteCookie,true,response,model,currentWordString);
            reset();
            model.addAttribute("letters","You guessed correctly - " + currentWordString);
        } else {
            Cookie cookie = new Cookie("word",URLEncoder.encode(currentWordString,StandardCharsets.UTF_8));
            updateCookie(cookie,false,response,model,currentWordString);
            model.addAttribute("letters",currentWordString);
        }
    }

    private void updateCookie(Cookie cookie,boolean reset,HttpServletResponse response,Model model,String currentWordString){
        cookie.setPath("/hangman");
        if(reset){
            cookie.setMaxAge(0);
            response.addCookie(cookie);

        } else {
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
        }

    }

    private void reset() {
        chToPosMap = new HashMap<>();
        word = "СТРУМИЦА";
        defaultString = "_".repeat(word.length());
        for (int i = 0; i < word.length(); i++) {
            if (!chToPosMap.containsKey(word.charAt(i))) {
                chToPosMap.put(word.charAt(i), new ArrayList<>());
            }
            chToPosMap.get(word.charAt(i)).add(i);
        }
    }

    private boolean gameFinished(String currentWordState) {
        int j = 0;
        for (int i = 0; i < currentWordState.length(); i++) {
            if (currentWordState.charAt(i) != word.charAt(j++)) {
                return false;
            }
        }
        return true;
    }
}

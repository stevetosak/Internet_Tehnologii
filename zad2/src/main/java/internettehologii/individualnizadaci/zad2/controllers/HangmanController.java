package internettehologii.individualnizadaci.zad2.controllers;

import internettehologii.individualnizadaci.zad2.services.HangmanGameManager;
import internettehologii.individualnizadaci.zad2.services.InputManager;
import internettehologii.individualnizadaci.zad2.services.RequestManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class HangmanController {
    private final HangmanGameManager hangmanGameManager;
    private final InputManager inputManager;

    public HangmanController() {
        inputManager = new RequestManager("INVALID OPERATION - the hangman service expects a GET or POST parameter " +
                "operand1 with a single alphabetical character as a value and will output " +
                "if you guessed the char correctly (if an imagined string contains the guessed character and at which positions");
        hangmanGameManager = new HangmanGameManager();
    }

    @GetMapping(value = "/hangman", produces={"text/html; charset=UTF-8"})
    public String HangmanGet(Model model,
                             HttpServletRequest request, HttpServletResponse response,
                             @CookieValue(value = "word", defaultValue = "EMPTY") String wordStateCookie) {


        String[] paramNvalue;
        if(request.getQueryString() != null){
             paramNvalue = inputManager.parseQueryString(URLDecoder.decode(request.getQueryString(),StandardCharsets.UTF_8));
        } else {
            paramNvalue = null;
        }

        return handleRequest(model, response, wordStateCookie, paramNvalue);
    }


    @PostMapping(value = "/hangman", produces={"text/html; charset=UTF-8"})
    public String HangmanPost(HttpServletResponse response, Model model, @RequestBody String request,
                              @CookieValue(value = "word", defaultValue = "EMPTY") String wordStateCookie) {
        String[] paramNvalue = inputManager.parsePostReq(URLDecoder.decode(request,StandardCharsets.UTF_8));
        wordStateCookie = URLDecoder.decode(wordStateCookie,StandardCharsets.UTF_8);
        return handleRequest(model, response, wordStateCookie, paramNvalue);
    }

    private String handleRequest(Model model, HttpServletResponse response, @CookieValue(value = "word", defaultValue = "EMPTY") String wordStateCookie, String[] paramNvalue) {
        String msg = inputManager.validateInput(paramNvalue);
        if (msg != null) {
            model.addAttribute("title", msg);
            model.addAttribute("err", inputManager.getErrorMessage());
            return "error";
        }
        wordStateCookie = URLDecoder.decode(wordStateCookie,StandardCharsets.UTF_8);
        hangmanGameManager.updatePage(wordStateCookie, Character.toUpperCase(paramNvalue[1].charAt(0)), response, model);
        return "hangman";
    }
}

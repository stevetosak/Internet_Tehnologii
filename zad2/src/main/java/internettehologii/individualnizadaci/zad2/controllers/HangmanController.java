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

    @GetMapping("/hangman")
    public String HangmanGet(Model model,
                             HttpServletRequest request, HttpServletResponse response,
                             @CookieValue(value = "currentWord", defaultValue = "EMPTY") String wordStateCookie) {

        String[] paramNvalue = inputManager.parseQueryString(request.getQueryString());
        return handleRequest(model, response, wordStateCookie, paramNvalue);
    }


    @PostMapping("/hangman")
    public String HangmanPost(HttpServletResponse response, Model model, @RequestBody String request,
                              @CookieValue(value = "currentWord", defaultValue = "EMPTY") String wordStateCookie) {
        String[] paramNvalue = inputManager.parsePostReq(request);
        return handleRequest(model, response, wordStateCookie, paramNvalue);
    }

    private String handleRequest(Model model, HttpServletResponse response, @CookieValue(value = "currentWord", defaultValue = "EMPTY") String wordStateCookie, String[] paramNvalue) {
        String msg = inputManager.validateInput(paramNvalue);
        if (msg != null) {
            model.addAttribute("title", msg);
            model.addAttribute("err", inputManager.getErrorMessage());
            return "error";
        }
        hangmanGameManager.updatePage(wordStateCookie, Character.toUpperCase(paramNvalue[1].charAt(0)), response, model);
        return "hangman";
    }
}

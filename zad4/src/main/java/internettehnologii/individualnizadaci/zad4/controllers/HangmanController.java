package internettehnologii.individualnizadaci.zad4.controllers;

import internettehnologii.individualnizadaci.zad4.services.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/application")
public class HangmanController {
    private final ClientService clientService;
    
    @Autowired
    public HangmanController(ClientService clientService) {
        this.clientService = clientService;
    }

    
    @RequestMapping("/hangman")
    public String hangmanView(Model model, HttpSession session,
                              @RequestParam(value = "option",required = false) String optionRadio,
                              @RequestParam(value = "operand1",required = false) String letter,
                              HttpServletRequest request, HttpServletResponse response){
        if(!isValidSession(session)) return "redirect:/application";

        String responseData= "";

        if(optionRadio != null){
            switch (optionRadio) {
                case "CGI" -> responseData = clientService.sendPostRequest("http://localhost:8081/hangman.cgi",letter,request,response);
                case "Java" -> responseData = clientService.sendPostRequest("http://localhost:8082/hangman",letter,request,response);
                case "DotNet" -> responseData = clientService.sendPostRequest("http://localhost:8083/hangman",letter,request,response);
                default -> responseData = "ERROR";
            }
        }

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        model.addAttribute("username",username);
        model.addAttribute("role", role);
        model.addAttribute("response",responseData);
        
        return "hangmanView";
    }

    
    private boolean isValidSession (HttpSession session){
        return session.getAttribute("username") != null && session.getAttribute("role") != null;
    }
}

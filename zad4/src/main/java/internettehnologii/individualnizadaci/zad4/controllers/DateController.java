package internettehnologii.individualnizadaci.zad4.controllers;

import internettehnologii.individualnizadaci.zad4.services.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/application")
public class DateController {
    private final ClientService clientService;

    public DateController(ClientService clientService) {
        this.clientService = clientService;
    }


    @RequestMapping("/date")
    public String dateView(HttpSession session, Model model, @RequestParam(value = "option",required = false) String option){

      if(isValidSession(session)) return "redirect:/application";
      
      String response = "";

        if(option != null){
            switch (option) {
                case "CGI" -> response = clientService.sendGetRequest("http://localhost:8081/date.cgi");
                case "Java" -> response = clientService.sendGetRequest("http://localhost:8082/date");
                case "DotNet" -> response = clientService.sendGetRequest("http://localhost:8083/date");
                default -> response = "ERROR";
            }
        }

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        model.addAttribute("username",username);
        model.addAttribute("role", role);
        model.addAttribute("response",response);

      return "dateView";
    }

    private boolean isValidSession (HttpSession session){
        return session.getAttribute("username") != null && session.getAttribute("role") != null;
    }
}

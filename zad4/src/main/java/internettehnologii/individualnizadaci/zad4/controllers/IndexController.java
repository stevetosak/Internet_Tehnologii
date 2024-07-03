package internettehnologii.individualnizadaci.zad4.controllers;

import internettehnologii.individualnizadaci.zad4.services.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
public class IndexController{
    private final LoginService loginService;

    @Autowired
    public IndexController(LoginService loginService){
        this.loginService = loginService;
    }

    @GetMapping("/application")
    public String index(HttpSession session){
        if(session.getAttribute("username") != null && session.getAttribute("role") != null){
            if(session.getAttribute("role") == "DATE"){
                return "redirect:/application/date";
            } else {
                return "redirect:/application/hangman";
            }
        }
        return "index";
    }

    @PostMapping("/application")
    public String login(HttpSession session,Model model, @RequestParam("username") String username, @RequestParam("password") String password){
        String role = loginService.validate(username,password);
        if(role != null){
            session.setAttribute("username",username);
            session.setAttribute("role",role);
            if(role.equals("DATE")){
                return "redirect:/application/date";
            } else {
                return "redirect:/application/hangman";
            }
        } else {
            model.addAttribute("msg","LOGIN UNSUCCESSFUL");
            return "index";
        }
    }


}

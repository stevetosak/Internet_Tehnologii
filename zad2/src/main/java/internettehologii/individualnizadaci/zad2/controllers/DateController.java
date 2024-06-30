package internettehologii.individualnizadaci.zad2.controllers;

import internettehologii.individualnizadaci.zad2.services.InputManager;
import internettehologii.individualnizadaci.zad2.services.RequestManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class DateController {
    private final InputManager manager = new RequestManager("INVALID OPERATION - " +
            "the service only functions " +
            "either without any parameters or with the " +
            "parameter \"time\", used with or without a value");

    @GetMapping("/date")
    public String Date(Model model, HttpServletRequest request){
        String[] params = manager.parseQueryString(request.getQueryString());
        if(params != null){
            if(params[0].equals("time")){
                model.addAttribute("title","Curent Date and Time");
                model.addAttribute("date", showCurrentDate(true));
                return "date";
            } else {
                model.addAttribute("err",manager.getErrorMessage());
                return "error";
            }
        }
        model.addAttribute("date", showCurrentDate(false));
        return "date";
    }

    private String showCurrentDate(boolean datetime){
        if(!datetime){
            return LocalDate.now(ZoneId.of("UTC+2")).toString();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:m:ss");
            return LocalDateTime.now(ZoneId.of("UTC+2")).format(formatter);
        }
    }

}

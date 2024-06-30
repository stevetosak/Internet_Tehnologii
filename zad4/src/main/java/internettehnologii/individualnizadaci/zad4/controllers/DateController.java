package internettehnologii.individualnizadaci.zad4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/application")
public class DateController {
    @RequestMapping("/date")
    public String dateView(){
        return "dateView";
    }
}

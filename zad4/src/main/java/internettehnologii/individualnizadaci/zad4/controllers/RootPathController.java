package internettehnologii.individualnizadaci.zad4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootPathController {
    @RequestMapping("/")
    public String Redirect(){
        return "redirect:/application";
    }
}

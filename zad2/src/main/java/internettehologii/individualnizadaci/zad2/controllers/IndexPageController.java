package internettehologii.individualnizadaci.zad2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexPageController {

    @GetMapping("/")
    public String HomePage(){
        return "index";
    }
}

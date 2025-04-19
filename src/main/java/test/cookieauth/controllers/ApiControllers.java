package test.cookieauth.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiControllers {

    @GetMapping("/")
    public String getPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username){
        return "profile";
    }

    @GetMapping("/profile")
    public String profile(){
        return "profile";
    }
}

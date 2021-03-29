package s1vskcsgobet.web_ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupPageController {

    @GetMapping(value = "/signup")
    public String showWelcomePage() {
        return "signup";
    }

}

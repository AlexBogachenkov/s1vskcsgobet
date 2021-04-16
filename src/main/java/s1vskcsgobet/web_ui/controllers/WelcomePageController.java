package s1vskcsgobet.web_ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class WelcomePageController {

    @GetMapping(value = "/")
    public String showWelcomePage(Principal principal) {
        if (principal == null) {
            return "index";
        }
        return "redirect:/bets";
    }

}

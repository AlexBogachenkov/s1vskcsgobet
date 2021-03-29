package s1vskcsgobet.web_ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModeratorPageController {

    @GetMapping(value = "/moderatorPage")
    public String showWelcomePage() {
        return "moderatorPage";
    }

}

package s1vskcsgobet.web_ui.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
public class ModeratorPageController {

    @GetMapping(value = "/moderatorPage")
    public String showWelcomePage() {
        return "moderatorPage";
    }

}

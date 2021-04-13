package s1vskcsgobet.web_ui.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminPageController {

    @GetMapping(value = "/adminPage")
    public String showAdminPage() {
        return "adminPage";
    }

}

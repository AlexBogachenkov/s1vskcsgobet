package s1vskcsgobet.web_ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import s1vskcsgobet.core.requests.user.LoginUserRequest;
import s1vskcsgobet.core.responses.user.LoginUserResponse;
import s1vskcsgobet.core.services.UserService;

@Controller
public class LoginPageController {

    private UserService userService;

    public LoginPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping(value = "/login")
    public String processLoginRequest(@ModelAttribute(value = "request") LoginUserRequest request, ModelMap modelMap) {
        LoginUserResponse response = userService.login(request);
        if (response.hasErrors()) {
            modelMap.addAttribute("errors", response.getErrors());
            return "login";
        }
        return "bets";
    }

}

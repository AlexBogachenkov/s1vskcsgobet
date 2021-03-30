package s1vskcsgobet.web_ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import s1vskcsgobet.core.requests.user.SignupUserRequest;
import s1vskcsgobet.core.responses.user.SignupUserResponse;
import s1vskcsgobet.core.services.UserService;

@Controller
public class SignupPageController {

    private UserService userService;

    public SignupPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/signup")
    public String showSignupPage(ModelMap modelMap) {
        modelMap.addAttribute("request", new SignupUserRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignupRequest(@ModelAttribute(value = "request") SignupUserRequest request, ModelMap modelMap) {
        SignupUserResponse response = userService.signup(request);
        if (response.hasErrors()) {
            modelMap.addAttribute("errors", response.getErrors());
            return "signup";
        }
        return "bets";
    }

}

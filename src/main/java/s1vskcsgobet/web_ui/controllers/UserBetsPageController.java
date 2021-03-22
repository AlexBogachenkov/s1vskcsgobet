package s1vskcsgobet.web_ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import s1vskcsgobet.core.requests.user_bet.FindUserBetsByUserIdRequest;
import s1vskcsgobet.core.responses.user_bet.FindUserBetsByUserIdResponse;
import s1vskcsgobet.core.services.UserBetService;

@Controller
public class UserBetsPageController {

    @Autowired
    private UserBetService userBetService;

    @GetMapping(value = "/user/{id}/bets")
    public String showUserBetsPage(@PathVariable Long id, ModelMap modelMap) {
        FindUserBetsByUserIdRequest request = new FindUserBetsByUserIdRequest(id);
        FindUserBetsByUserIdResponse response = userBetService.findByUserId(request);
        modelMap.addAttribute("userBets", response.getUserBets());
        modelMap.addAttribute("errors", response.getErrors());
        return "userBets";
    }

}

package s1vskcsgobet.web_ui.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import s1vskcsgobet.core.requests.user_bet.FindUserBetsByUserIdRequest;
import s1vskcsgobet.core.responses.user_bet.FindUserBetsByUserIdResponse;
import s1vskcsgobet.core.services.ApplicationContextService;
import s1vskcsgobet.core.services.UserBetService;

@Controller
@RequiredArgsConstructor
public class UserBetsPageController {

    private final UserBetService userBetService;
    private final ApplicationContextService context;

    @PreAuthorize("#id == authentication.principal.id or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping(value = "/user/{id}/bets")
    public String showUserBetsPage(@PathVariable Long id, ModelMap modelMap) {
        FindUserBetsByUserIdRequest request = new FindUserBetsByUserIdRequest(id);
        FindUserBetsByUserIdResponse response = userBetService.findByUserId(request);
        modelMap.addAttribute("userBets", response.getUserBets());
        modelMap.addAttribute("errors", response.getErrors());
        modelMap.addAttribute("userId", context.getUserId());
        return "userBets";
    }

}

package s1vskcsgobet.web_ui.controllers.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1vskcsgobet.core.requests.user_bet.AddUserBetRequest;
import s1vskcsgobet.core.responses.user_bet.AddUserBetResponse;
import s1vskcsgobet.core.services.UserBetService;

@RestController
@RequestMapping("/userBet")
public class UserBetRestController {

    private UserBetService userBetService;

    public UserBetRestController(UserBetService userBetService) {
        this.userBetService = userBetService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public AddUserBetResponse addUserBet(@RequestBody AddUserBetRequest request) {
        return userBetService.add(request);
    }

}

package s1vskcsgobet.web_ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.User;
import s1vskcsgobet.core.dto.UserDto;
import s1vskcsgobet.core.responses.bet.FindAllBetsResponse;
import s1vskcsgobet.core.services.ApplicationContextService;
import s1vskcsgobet.core.services.BetService;

@Controller
public class BetsPageController {

    @Autowired
    private BetService betService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationContextService context;

    @GetMapping(value = "/bets")
    public String showBetsPage(ModelMap modelMap) {
        FindAllBetsResponse response = betService.findActive();
        User user = userRepository.findById(context.getUserId()).get();
        UserDto userDto = new UserDto(user.getId(), user.getNickname(), user.getBalance());
        modelMap.addAttribute("activeBets", response.getAllBets());
        modelMap.addAttribute("user", userDto);
        return "bets";
    }

}

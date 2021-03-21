package s1vskcsgobet.web_ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import s1vskcsgobet.core.responses.bet.FindAllBetsResponse;
import s1vskcsgobet.core.services.BetService;

@Controller
public class BetsPageController {

    @Autowired
    private BetService betService;

    @GetMapping(value = "/bets")
    public String showBetsPage(ModelMap modelMap) {
        FindAllBetsResponse response = betService.findActive();
        modelMap.addAttribute("activeBets", response.getAllBets());
        return "bets";
    }

}
